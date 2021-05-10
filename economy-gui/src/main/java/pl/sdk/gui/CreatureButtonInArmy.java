package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;

public class CreatureButtonInArmy extends Button {

    private Stage windowForDeletingCreature;

    private String nameOfCreature;
    private boolean ifRemove;

    public CreatureButtonInArmy(EconomyController aController, Creature aCreature) {
        super(aCreature.getName()+"("+aCreature.getAmount()+")");
        nameOfCreature = aCreature.getName();
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if(displayWindowForRemovingAndReturnIfRemove())
                aController.sell(aCreature);
            aController.refreshGui();
        });
    }



    private boolean displayWindowForRemovingAndReturnIfRemove() {
        HBox top = new HBox();
        HBox bottom = new HBox();
        preparingWindow(bottom,top);
        prepareDeleteAndCloseButtonsAndTop(bottom, top);
        windowForDeletingCreature.showAndWait();
        return ifRemove;
    }

    private void prepareDeleteAndCloseButtonsAndTop(HBox aBottom, HBox aTop) {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Are you sure you want"));
        vBox.getChildren().add(new Label("to delete it?"));
        aTop.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");
        deleteButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);
        deleteButton.setAlignment(Pos.CENTER);
        cancelButton.setAlignment(Pos.CENTER);
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForDeletingCreature.close());
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            ifRemove = true;
            windowForDeletingCreature.close();
        });
        aBottom.getChildren().add(deleteButton);
        aBottom.getChildren().add(cancelButton);
    }

    private void preparingWindow(HBox aBottom, HBox aTop) {
        windowForDeletingCreature = new Stage();
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 300, 200);
        scene.getStylesheets().add("fxml/main.css");
        windowForDeletingCreature.setScene(scene);
        windowForDeletingCreature.initOwner(this.getScene().getWindow());
        windowForDeletingCreature.initModality(Modality.APPLICATION_MODAL);
        windowForDeletingCreature.setTitle("Deleting" + getName());
        windowForDeletingCreature.setResizable(false);
        aBottom.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setTop(aTop);
    }

    String getName() {
        return nameOfCreature;
    }
}
