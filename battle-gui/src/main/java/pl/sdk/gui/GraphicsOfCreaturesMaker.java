package pl.sdk.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.sdk.creatures.Creature;

public class GraphicsOfCreaturesMaker {
    static public ImageView getGraphicsOf(String aName, Creature.Team aTeam, int aHeight, int aWidth) {
        ImageView image = new ImageView(new Image(GraphicsOfCreaturesMaker.class.getResourceAsStream("/graphics/creatures/" + aTeam.getPath() + "/" + aName + ".png")));
        image.setFitHeight(aHeight);
        image.setFitWidth(aWidth);
        return image;
    }

    static public ImageView getGraphicsOf(String aName, Creature.Team aTeam) {
        return getGraphicsOf(aName, aTeam, 61, 61);
    }
}
