package pl.sdk.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class MapTile extends StackPane {
    private Rectangle rec;
    public MapTile(){
        rec = new Rectangle(40,40, Color.WHITE);
        rec.setStroke(Color.BLACK);
        getChildren().add(rec);
    }

    public void createLabel(String text) {
        getChildren().add(new Label(text));
    }

    public void setBackgroundColor(Color color) {
        rec.setFill(color);
    }
}
