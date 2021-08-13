package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.*;
import pl.sdk.creatures.Creature;

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
    private Pane leftPainForHero;
    @FXML
    private Pane rightPainForHero;

    private GameEngine gameEngine;
    private Stage windowForEndOfTheGame;

    public BattleMapController(List<Creature> TeamLeft, List<Creature> TeamRight) {
        gameEngine = new GameEngine(TeamLeft, TeamRight);
    }

    @FXML
    void initialize() {
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_CHANGED, this);
        gameEngine.addObserver(gameEngine.CREATURE_MOVED, this);
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_ATTACKED, this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.pass());

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
                    if (creatureOnMapTitle == gameEngine.getActiveCreature() && gameEngine.getActiveCreature().isAlive())
                        flagActiveCreature(x, y, mapTile,creatureOnMapTitle);
                    else if (!creatureOnMapTitle.isAlive())
                        flagDeadCreatureAndSetMoveOnClick(x,y,mapTile);
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

    private void flagActiveCreature(int aX, int aY, MapTile aMapTile, Creature aCreatureOnMapTitle) {
        if (isAfterAction(aX, aY,aCreatureOnMapTitle)) aMapTile.setBackgroundColor(Color.GRAY);
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
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.move(new Point(FX, FY)));
    }

    private void setBackgroundRedAndSetAttackOnClick(int aX, int aY, MapTile aMapTile) {
        aMapTile.setBackgroundColor((Color.RED));
        final int FX = aX;
        final int FY = aY;
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.attack(new Point(FX, FY)));
    }

    private boolean isAfterAction(int aX, int aY, Creature aCreature) {
        return gameEngine.getPossibleAttacksOfActiveCreature() <= 0
                || (gameEngine.getLeftMovePointsOfActiveCreature() < 1
                && aCreature.getShots() < 1
                && !gameEngine.canAttack(aX - 1, aY)
                && !gameEngine.canAttack(aX - 1, aY + 1)
                && !gameEngine.canAttack(aX, aY + 1)
                && !gameEngine.canAttack(aX + 1, aY + 1)
                && !gameEngine.canAttack(aX + 1, aY)
                && !gameEngine.canAttack(aX + 1, aY - 1)
                && !gameEngine.canAttack(aX, aY - 1)
                && !gameEngine.canAttack(aX - 1, aY - 1));
    }

    private void makeWindowOfWinningSide() {
        HBox top = new HBox();
        HBox bottom = new HBox();
        preparingWindow(bottom,top);
        prepareSellingAndCloseButtonsAndTop(bottom, top);
        windowForEndOfTheGame.showAndWait();
        exit();

    }

    private void prepareSellingAndCloseButtonsAndTop(HBox aBottom, HBox aTop) {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Congratulations!"));
        vBox.getChildren().add(new Label("If You are the " +gameEngine.getActiveCreature().getTeam()+", you won!"));
        vBox.getChildren().add(new Label("If You are not, sorry, train better."));
        aTop.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(200);
        closeButton.setAlignment(Pos.CENTER);
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForEndOfTheGame.close());
        aBottom.getChildren().add(closeButton);
    }

    private void preparingWindow(HBox aBottom, HBox aTop) {
        windowForEndOfTheGame = new Stage();
        windowForEndOfTheGame.getIcons().add(new Image("jpg/icon.jpg"));
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 450, 250);
        scene.getStylesheets().add("fxml/main.css");
        windowForEndOfTheGame.setScene(scene);
        windowForEndOfTheGame.initOwner(gridMap.getScene().getWindow());
        windowForEndOfTheGame.initModality(Modality.APPLICATION_MODAL);
        windowForEndOfTheGame.setTitle("End Of the game!");
        windowForEndOfTheGame.setResizable(false);
        aBottom.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setTop(aTop);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
        if(aPropertyChangeEvent.getPropertyName().equals( gameEngine.CURRENT_CREATURE_ATTACKED)){
            if(gameEngine.ifSomeTeamWon()){
                makeWindowOfWinningSide();
            }
        }
    }
}
