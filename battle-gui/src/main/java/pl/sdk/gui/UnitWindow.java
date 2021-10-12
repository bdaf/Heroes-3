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
import pl.sdk.creatures.Weakness;

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

        addWeaknessesToDescription(aCreature, description);

        description.setStyle("-fx-font: 12 arial;");

        description.setWrappingWidth(480);
        VBox vBox = new VBox(5, description, closeButton);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox getRightVBoxWithStats(Creature aCreature) {
        String resultOfWeaknesses = "";
        String statsString;
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(0, 0, 20, 0));
        String shots = "";
        if (aCreature.getShots() > 0) shots = " - " + aCreature.getShots() + " shots";

        resultOfWeaknesses = correctIfIsWeakness(aCreature.getStats().getMaxHp(), aCreature.getMaxHp());
        statsString = "Health: " + aCreature.getCurrentHp() + "/" + aCreature.getStats().getMaxHp() + " " + resultOfWeaknesses + shots;
        Text healthAndShots = new Text(statsString);

        resultOfWeaknesses = correctIfIsWeakness(aCreature.getStats().getAttack(), aCreature.getAttack());
        statsString = "Attack: " + aCreature.getStats().getAttack() + " " + resultOfWeaknesses;

        shots = correctIfIsWeakness(aCreature.getStats().getDamage().lowerEndpoint(), aCreature.getDamage().lowerEndpoint());
        resultOfWeaknesses = correctIfIsWeakness(aCreature.getStats().getDamage().upperEndpoint(), aCreature.getDamage().upperEndpoint());

        statsString += " | Damage: " + aCreature.getStats().getDamage().lowerEndpoint() + shots + " - " + aCreature.getStats().getDamage().upperEndpoint() + " " + resultOfWeaknesses;
        Text firstHalfOfStats = new Text(statsString);

        resultOfWeaknesses = correctIfIsWeakness(aCreature.getStats().getArmor(), aCreature.getArmor());
        Text secondHalfOfStats = new Text("Defense: " + aCreature.getStats().getArmor() + " " + resultOfWeaknesses
                + " | Movement: " + aCreature.getStats().getMoveRange());

        healthAndShots.setStyle("-fx-font: 18 arial;");
        firstHalfOfStats.setStyle("-fx-font: 18 arial;");
        secondHalfOfStats.setStyle("-fx-font: 18 arial;");

        vBox.getChildren().addAll(healthAndShots, firstHalfOfStats, secondHalfOfStats);
        vBox.setPrefWidth(370);
        return vBox;
    }

    // It displays difference between values from stats and changed ones by weaknesses
    private String correctIfIsWeakness(int aStatsValue, int aCreatureValue) {
        if (aStatsValue != aCreatureValue) {
            String differenceBetweenValues = "";
            if (aCreatureValue - aStatsValue > 0)
                differenceBetweenValues += "+";
            differenceBetweenValues += String.valueOf(aCreatureValue - aStatsValue);
            return "(" + (differenceBetweenValues) + ")";
        }
        return "";
    }

    private void addWeaknessesToDescription(Creature aCreature, Text aDescription) {
        if (!aCreature.getWeaknesses().isEmpty()) {
            String weaknesses = " Weaknesses: ";
            for (int i = 0; i < aCreature.getWeaknesses().size(); i++) {
                Weakness weakness = aCreature.getWeaknesses().get(i);
                String duration = String.valueOf(weakness.getDuration());
                if (weakness.getDuration() > 100) duration = "inf";
                weaknesses += weakness.getName() + " (" + duration + ")" + ", ";
            }
            weaknesses = weaknesses.substring(0, weaknesses.length() - 2);
            weaknesses += ".";
            aDescription.setText(aDescription.getText() + weaknesses);
        }
    }

    void showAndWait() {
        window.showAndWait();
    }
}
