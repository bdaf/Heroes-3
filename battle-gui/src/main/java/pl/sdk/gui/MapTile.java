package pl.sdk.gui;

import javafx.geometry.Pos;
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
    public MapTile(){
        rec = new Rectangle(60,60, Color.WHITE);
        rec.setStroke(Color.BLACK);
        getChildren().add(rec);
    }

    public void addCreature(String stringOfCurrentHp, String nameOfCreature, Creature.Team aTeam) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/graphics/creatures/"+aTeam.getPath()+"/" + nameOfCreature + ".png")));
        image.setFitHeight(61);
        image.setFitWidth(61);
        vbox.getChildren().add(image);
        getChildren().add(vbox);
        Text text = new Text(stringOfCurrentHp);
        vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(vbox);

    }

    public void setBackgroundColor(Color color) {
        rec.setFill(color);
    }
}
