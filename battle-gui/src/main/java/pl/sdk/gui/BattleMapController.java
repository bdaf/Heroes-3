package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.sdk.*;
import pl.sdk.creatures.CastleFactory;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;

public class BattleMapController implements PropertyChangeListener {

    @FXML
    private GridPane gridMap;
    private GameEngine gameEngine;
    @FXML
    private Button passButton;
    @FXML
    private Button escapeButton;

    public BattleMapController(List<Creature> TeamLeft, List<Creature> TeamRight) {
        gameEngine = new GameEngine(TeamLeft, TeamRight);
    }

    @FXML
    void initialize() {
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> exit());
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_CHANGED, this);
        gameEngine.addObserver(gameEngine.CREATURE_MOVED, this);
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_ATTACKED, this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.pass());
        refreshGui();
    }

    private void refreshGui() {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile, x, y);
                Creature creatureOnMapTitle = gameEngine.get(x, y);
                if (creatureOnMapTitle != null) {
                    mapTile.addCreature(creatureOnMapTitle.getStringOfCurrentHp(), creatureOnMapTitle.getName(), creatureOnMapTitle.getTeam());
                    if (!creatureOnMapTitle.isAlive())
                        flagCreatureNotAlive(mapTile);
                    else if (creatureOnMapTitle == gameEngine.getActiveCreature())
                        flagActiveCreature(x, y, mapTile);
                    else if (gameEngine.canAttack(x, y))
                        setBackgroundRedAndSetClickOnAttack(x, y, mapTile);
                    else if (creatureOnMapTitle.getTeam() == Creature.Team.LEFT_TEAM)
                        mapTile.setBackgroundColor(Color.BLUEVIOLET);
                    else if (creatureOnMapTitle.getTeam() == Creature.Team.RIGHT_TEAM)
                        mapTile.setBackgroundColor(Color.AQUA);
                } else if (gameEngine.canMove(x, y))
                    setBackgroundGrayAndSetClickOnMove(x, y, mapTile);
            }
        }
    }

    private void flagActiveCreature(int aX, int aY, MapTile aMapTile) {
        aMapTile.setBackgroundColor(Color.GREEN);
        if (isAfterAction(aX, aY)) aMapTile.setBackgroundColor(Color.GRAY);
    }

    private void flagCreatureNotAlive(MapTile aMapTile) {
        aMapTile.setRotateToCreatureImage(90);
        aMapTile.setBackgroundColor(Color.BLACK);
    }

    private void setBackgroundGrayAndSetClickOnMove(int aX, int aY, MapTile aMapTile) {
        final int FX = aX;
        final int FY = aY;
        aMapTile.setBackgroundColor((Color.GRAY));
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.move(new Point(FX, FY)));
    }

    private void setBackgroundRedAndSetClickOnAttack(int aX, int aY, MapTile aMapTile) {
        final int FX = aX;
        final int FY = aY;
        aMapTile.setBackgroundColor((Color.RED));
        aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.attack(new Point(FX, FY)));
    }

    private boolean isAfterAction(int aX, int aY) {
        return gameEngine.getPossibleAttacksOfActiveCreature() <= 0
                || (gameEngine.getLeftMovePointsOfActiveCreature() < 1
                && !gameEngine.canAttack(aX - 1, aY)
                && !gameEngine.canAttack(aX - 1, aY + 1)
                && !gameEngine.canAttack(aX, aY + 1)
                && !gameEngine.canAttack(aX + 1, aY + 1)
                && !gameEngine.canAttack(aX + 1, aY)
                && !gameEngine.canAttack(aX + 1, aY - 1)
                && !gameEngine.canAttack(aX, aY - 1)
                && !gameEngine.canAttack(aX - 1, aY - 1));
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
    }
}
