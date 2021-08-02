package pl.sdk.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.sdk.creatures.Creature;

public class GraphicsOfCreaturesMaker {
    static public ImageView getGraphicsOf(String aName, int aHeight, int aWidth){
        ImageView image = new ImageView(new Image(GraphicsOfCreaturesMaker.class.getResourceAsStream("/graphics/creatures/"+ Creature.Team.RIGHT_TEAM.getPath() +"/" + aName + ".png")));
        image.setFitHeight(aHeight);
        image.setFitWidth(aWidth);
        return image;
    }

    static public ImageView getGraphicsOf(String aName){
        return getGraphicsOf(aName,61,61);
    }
}
