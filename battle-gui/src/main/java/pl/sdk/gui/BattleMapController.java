package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BattleMapController {

    @FXML
    private GridPane gridMap;

    @FXML
    void initialize(){
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 15; y++) {
                Rectangle rec = new Rectangle(40,40, Color.WHITE);
                rec.setStroke(Color.BLACK);
                gridMap.add(rec,x,y);
            }
        }
    }
}
