package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.sdk.*;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BattleMapController implements PropertyChangeListener {

    @FXML
    private GridPane gridMap;
    private GameEngine gameEngine;
    @FXML
    private Button passButton;
    @FXML
    private Button escapeButton;

    public BattleMapController(){
        List<Creature> notUpgradedCreatures = new ArrayList<Creature>();
        List<Creature> upgradedCreatures = new ArrayList<Creature>();
        NecropolisFactory factory = new NecropolisFactory();

        Creature c;
        for (int i = 0; i < 7; i++) {
            c = factory.Create(false,i+1);
            notUpgradedCreatures.add(c);
        }
        for (int i = 0; i < 7; i++) {
            c = factory.Create(true,i+1);
            upgradedCreatures.add(c);
        }


        gameEngine = new GameEngine(notUpgradedCreatures,upgradedCreatures);
    }


    @FXML
    void initialize(){
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_CHANGED , this);
        gameEngine.addObserver(gameEngine.CREATURE_MOVED , this);
        gameEngine.addObserver(gameEngine.CURRENT_CREATURE_ATTACKED , this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            gameEngine.pass();
        });


        refreshGui();
    }

    private void refreshGui() {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile,x,y);

                Creature c = gameEngine.get(x,y);
                if(c!=null) {
                    mapTile.addCreature(c.getStringOfCurrentHp(), c.getName());
                    if(c == gameEngine.getActiveCreature())
                        mapTile.setBackgroundColor(Color.GREEN);
                    else if(gameEngine.canAttack(x,y)){
                        final int FX = x;
                        final int FY = y;
                        mapTile.setBackgroundColor((Color.RED));
                        mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.attack(new Point(FX,FY)));
                    }
                }
                else if(gameEngine.canMove(x,y)){
                    final int FX = x;
                    final int FY = y;
                    mapTile.setBackgroundColor((Color.GRAY));
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> gameEngine.move(new Point(FX,FY)));
                }
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
    }
}
