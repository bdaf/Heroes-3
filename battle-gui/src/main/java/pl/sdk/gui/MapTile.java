package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import pl.sdk.creatures.Creature;

class MapTile extends StackPane {
    private Rectangle rec;
    private ImageView image;

    public MapTile(){
        rec = new Rectangle(60,60, Color.WHITE);
        rec.setStroke(Color.BLACK);
        getChildren().add(rec);
    }

    void addCreature(String stringOfCurrentHp, String nameOfCreature, Creature.Team aTeam) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        image = GraphicsOfCreaturesMaker.getGraphicsOf(nameOfCreature,aTeam,61,61);
        vbox.getChildren().add(image);
        getChildren().add(vbox);
        Text text = new Text(stringOfCurrentHp);
        vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(vbox);
    }

    void setBackgroundColor(Color color) {
        rec.setFill(color);
    }

    void setRotateToCreatureImage(double aValue){
        if(image != null) image.setRotate(aValue);
    }


}
