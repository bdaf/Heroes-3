package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.sdk.Creature;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.GenericArrayType;
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
        List<Creature> listOfCreatures1 = new ArrayList<Creature>();
        listOfCreatures1.add(new Creature.Builder().build());
        listOfCreatures1.add(new Creature.Builder().build());
        listOfCreatures1.add(new Creature.Builder().build());
        List<Creature> listOfCreatures2 = new ArrayList<Creature>();
        listOfCreatures2.add(new Creature.Builder().build());
        listOfCreatures2.add(new Creature.Builder().build());
        listOfCreatures2.add(new Creature.Builder().build());

        gameEngine = new GameEngine(listOfCreatures1,listOfCreatures2);


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
                    mapTile.addNameOfCreature(c.toString());
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
