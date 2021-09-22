package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.sdk.*;
import pl.sdk.creatures.Creature;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.KindOfGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static javafx.application.Platform.exit;

public class BattleMapController implements PropertyChangeListener {

    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;
    @FXML
    private Button escapeButton;
    @FXML
    private Pane leftPainForHero;
    @FXML
    private Pane rightPainForHero;

    private GameEngine gameEngine;
    private final Fraction leftTeamFraction, rightTeamFraction;
    private final KindOfGame kindOfGame;

    public BattleMapController(List<Creature> TeamLeft, List<Creature> TeamRight, KindOfGame aKindOfGame) {
        gameEngine = new GameEngine(TeamLeft, TeamRight);
        leftTeamFraction = Fraction.NECROPOLIS;
        rightTeamFraction = Fraction.CASTLE;
        kindOfGame = aKindOfGame;
    }

    @FXML
    void initialize() {
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_CHANGED, this);
        gameEngine.addObserver(gameEngine.CREATURE_MOVED, this);
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_ATTACKED, this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) gameEngine.pass();
        });
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) exit();
        });
        refreshGui();
    }

    private void refreshGui() {
        gridMap.getChildren().clear();
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile, x, y);
                Creature creatureOnMapTitle = gameEngine.get(x, y);
                if (creatureOnMapTitle != null) {
                    mapTile.addCreature(creatureOnMapTitle);
                    addEventHandlerForShowingStatsOnRightClick(mapTile, creatureOnMapTitle);
                    if (creatureOnMapTitle == gameEngine.getActiveCreature() && gameEngine.getActiveCreature().isAlive())
                        flagActiveCreature(mapTile);
                    else if (!creatureOnMapTitle.isAlive())
                        flagDeadCreatureAndSetMoveOnClick(x, y, mapTile);
                    else if (gameEngine.canAttack(x, y))
                        setBackgroundRedAndSetAttackOnClick(x, y, mapTile);
                    else if (creatureOnMapTitle.getTeam() == Creature.Team.LEFT_TEAM)
                        mapTile.setBackgroundColor(Color.BLUEVIOLET);
                    else if (creatureOnMapTitle.getTeam() == Creature.Team.RIGHT_TEAM)
                        mapTile.setBackgroundColor(Color.AQUA);
                } else if (gameEngine.canMove(x, y))
                    setBackgroundGrayAndSetMoveOnClick(x, y, mapTile);
            }
        }
    }

    private void addEventHandlerForShowingStatsOnRightClick(MapTile aMapTile, Creature aCreature) {
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                new UnitWindow(aCreature, (Stage) aMapTile.getScene().getWindow()).showAndWait();
            }
        });
    }

    private void flagActiveCreature(MapTile aMapTile) {
        if (!gameEngine.canActiveCreatureDoAnyAction()) aMapTile.setBackgroundColor(Color.GRAY);
        else aMapTile.setBackgroundColor(Color.GREEN);
    }

    private void flagDeadCreatureAndSetMoveOnClick(int aX, int aY, MapTile aMapTile) {
        aMapTile.setBackgroundColor(Color.BLACK);
        aMapTile.setRotateToCreatureImage(90);
        setMoveOnClick(aX, aY, aMapTile);
    }

    private void setBackgroundGrayAndSetMoveOnClick(int aX, int aY, MapTile aMapTile) {
        aMapTile.setBackgroundColor((Color.GRAY));
        setMoveOnClick(aX, aY, aMapTile);
    }

    private void setMoveOnClick(int aX, int aY, MapTile aMapTile) {
        final int FX = aX;
        final int FY = aY;
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) gameEngine.move(new Point(FX, FY));
        });
    }

    private void setBackgroundRedAndSetAttackOnClick(int aX, int aY, MapTile aMapTile) {
        aMapTile.setBackgroundColor((Color.RED));
        final int FX = aX;
        final int FY = aY;
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) gameEngine.attack(new Point(FX, FY));
        });
    }

    private void makeWindowOfWinningSideInBattle() {
        gameEngine.pass();
        AfterBattleWindow afterBattleWindow = new AfterBattleWindow(this);
        afterBattleWindow.showAndWait();
        if (kindOfGame.ifAnyFractionWon(leftTeamFraction,rightTeamFraction)) {
            String winner = getWinnerOfTheGame();
            AfterGameWindow afterGameWindow = new AfterGameWindow(this);
            afterGameWindow.addCaption("\n" + winner + " Won the Game!");
            afterGameWindow.addCaption("Score: " + leftTeamFraction.getPoints() + " - " + rightTeamFraction.getPoints() + " for " + winner + "!");
            afterGameWindow.showAndWait();
        }
        Stage stage = (Stage) gridMap.getScene().getWindow();
        stage.close();
    }

    private String getWinnerOfTheGame() {
        if (kindOfGame.ifFractionWon(leftTeamFraction)) return leftTeamFraction.name();
        else return rightTeamFraction.name();
    }

    String getWinnerOfTheBattle() {
        if (gameEngine.getWinnerTeam() == Creature.Team.LEFT_TEAM) return leftTeamFraction.name();
        else return rightTeamFraction.name();
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
        if (aPropertyChangeEvent.getPropertyName().equals(gameEngine.CURRENT_CREATURE_ATTACKED)) {
            if (gameEngine.anyTeamWon()) {
                if (gameEngine.getWinnerTeam() == Creature.Team.LEFT_TEAM) leftTeamFraction.increasePoints(1);
                else rightTeamFraction.increasePoints(1);
                makeWindowOfWinningSideInBattle();
            }
        }
    }

    Node getGridMap() {
        return gridMap;
    }

    Fraction getLeftTeamFraction() {
        return leftTeamFraction;
    }

    Fraction getRightTeamFraction() {
        return rightTeamFraction;
    }
}
