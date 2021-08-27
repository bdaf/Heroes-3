package pl.sdk.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;

public class UnitWindow {
    private final Stage window;

    UnitWindow(Creature aCreature, Stage aParent) {
        window = new Stage();
        window.getIcons().add(new Image("jpg/icon.jpg"));

        BorderPane pane = new BorderPane();
        pane.setTop(getLabelOfCreatureName(aCreature));
        pane.setLeft(GraphicsOfCreaturesMaker.getGraphicsOf(aCreature.getName(), Creature.Team.LEFT_TEAM, 123, 123));
        pane.setRight(getRightVBoxWithStats(aCreature));
        pane.setBottom(getDescriptionAndCloseButton(aCreature));


        Scene scene = new Scene(pane, 500, 280);
        scene.getStylesheets().add("fxml/main.css");

        window.setScene(scene);
        window.initOwner(aParent.getScene().getWindow());
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(aCreature.getName());
        window.setResizable(false);
    }


    private Node getLabelOfCreatureName(Creature aCreature) {
        Label label = new Label(aCreature.getName());
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox getDescriptionAndCloseButton(Creature aCreature) {
        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(200);
        closeButton.setAlignment(Pos.CENTER);
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) window.close();
        });
        Text description = new Text(aCreature.getStats().getDescription());
        description.setStyle("-fx-font: 12 arial;");

        description.setWrappingWidth(480);
        VBox vBox = new VBox(5,description,closeButton);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox getRightVBoxWithStats(Creature aCreature) {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(0,0,20,0));
        String shots = "";
        if (aCreature.getShots() > 0) shots = " - " + aCreature.getShots() + " shots";

        Text healthAndShots = new Text("Health: " + aCreature.getCurrentHp() + "/" + aCreature.getMaxHp() + shots);
        //healthAndShots.setAlignment(Pos.CENTER);

        Text firstHalfOfStats = new Text("Attack: " + aCreature.getStats().getAttack()
                + " | Damage: " + aCreature.getStats().getDamage().lowerEndpoint() + " - " + aCreature.getStats().getDamage().upperEndpoint());
        //firstHalfOfStats.setAlignment(Pos.CENTER);

        Text secondHalfOfStats = new Text("Defense: " + aCreature.getStats().getArmor()
                + " | Movement: " + aCreature.getStats().getMoveRange());
        //secondHalfOfStats.setAlignment(Pos.CENTER);

        healthAndShots.setStyle("-fx-font: 18 arial;");
        firstHalfOfStats.setStyle("-fx-font: 18 arial;");
        secondHalfOfStats.setStyle("-fx-font: 18 arial;");

        vBox.getChildren().add(healthAndShots);
        vBox.getChildren().add(firstHalfOfStats);
        vBox.getChildren().add(secondHalfOfStats);
        vBox.setPrefWidth(370);
        return vBox;
    }

    void showAndWait() {
        window.showAndWait();
    }
}
