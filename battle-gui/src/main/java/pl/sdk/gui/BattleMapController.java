package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.sdk.Creature;
import pl.sdk.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class BattleMapController {

    @FXML
    private GridPane gridMap;
    private GameEngine gameEngine;

    public BattleMapController(){
        List<Creature> listOfCreatures1 = new ArrayList<Creature>();
        listOfCreatures1.add(new Creature());
        listOfCreatures1.add(new Creature());
        listOfCreatures1.add(new Creature());
        List<Creature> listOfCreatures2 = new ArrayList<Creature>();
        listOfCreatures2.add(new Creature());
        listOfCreatures2.add(new Creature());
        listOfCreatures2.add(new Creature());

        gameEngine = new GameEngine(listOfCreatures1,listOfCreatures2);
    }


    @FXML
    void initialize(){
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                MapTile mapTile = new MapTile();
                gridMap.add(mapTile,x,y);

                Creature c = gameEngine.get(x,y);
                if(c!=null)
                    mapTile.createLabel(c.getName());
            }
        }
    }

}
