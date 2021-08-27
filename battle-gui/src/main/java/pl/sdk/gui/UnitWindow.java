package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;

public class UnitWindow {
    private final Stage window;

    UnitWindow(Creature aCreature, Stage aParent) {
        window = new Stage();
        window.getIcons().add(new Image("jpg/icon.jpg"));

        BorderPane pane = new BorderPane();
        pane.setTop(getLabelOfCreature(aCreature));
        pane.setLeft(GraphicsOfCreaturesMaker.getGraphicsOf(aCreature.getName(), Creature.Team.LEFT_TEAM, 200, 200));
        pane.setRight(getRightVBoxWithStats(aCreature));
        pane.setBottom(getCloseButton());


        Scene scene = new Scene(pane, 450, 250);
        scene.getStylesheets().add("fxml/main.css");

        window.setScene(scene);
        window.initOwner(aParent.getScene().getWindow());
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(aCreature.getName());
        window.setResizable(false);
    }

    private Node getLabelOfCreature(Creature aCreature) {
        Label label = new Label(aCreature.getName() + "(" + aCreature.getAmount() + ")");
        label.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private Button getCloseButton() {
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(200);
        closeButton.setAlignment(Pos.CENTER);
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) window.close();
        });
        return null;
    }

    private VBox getRightVBoxWithStats(Creature aCreature) {
        VBox vBox = new VBox();
        String shots = "";
        if (aCreature.getShots() > 0) shots = " - " + aCreature.getShots() + " shots";
        Label stats = new Label("Health: " + aCreature.getCurrentHp() + "/" + aCreature.getMaxHp() + shots
                + " |\n Attack: " + aCreature.getStats().getAttack()
                + " | Damage: " + aCreature.getStats().getDamage().lowerEndpoint() + " - " + aCreature.getStats().getDamage().upperEndpoint()
                + " |\n Defense: " + aCreature.getStats().getArmor()
                + " | Movement: " + aCreature.getStats().getMoveRange());
        vBox.getChildren().add(stats);
        return vBox;
    }

    void showAndWait() {
        window.showAndWait();
    }
}
