package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;


public class CreatureButtonInShop extends Button {

    private Stage windowForChoosingAmount;
    private String nameOfCreature;

    public CreatureButtonInShop(EconomyController aController, Factory aFactory, int aTier, boolean aIsUpgraded) {
        super(aFactory.Create(aIsUpgraded, aTier, 1).getName());
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            byte amount = displayChoosingAmountAndGetCreatureAmount();
            if (amount > 0) {
                Creature creature = aFactory.Create(aIsUpgraded, aTier, amount);
                nameOfCreature = creature.getName();
                aController.buy(creature);
            }
            aController.refreshGui();
        });
    }

    private byte displayChoosingAmountAndGetCreatureAmount() {
        HBox top = new HBox();
        VBox center = new VBox();
        HBox bottom = new HBox();
        prepareWindowForChoosingAmount(bottom, center, top);
        Slider slider = createSlider();
        prepareSaveAndCloseButton(bottom, slider);
        prepareTop(top, slider);
        center.getChildren().add(slider);
        windowForChoosingAmount.showAndWait();

        return (byte) slider.getValue();
    }

    private void prepareTop(HBox aTop, Slider aSlider) {
        //TODO Costing And Adding Gold
        VBox vbox = new VBox();
        Label sliderValueLabel = new Label("0");
        aSlider.valueProperty().addListener((slider, aOld, aNew) -> sliderValueLabel.setText(String.valueOf(aNew.byteValue())));
        HBox hBox = new HBox();
        Label label = new Label("Amount: ");
        hBox.getChildren().add(label);
        hBox.getChildren().add(sliderValueLabel);
        hBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(new Label("Single Cost: " + "0" + " Purchase Cost: " + "0"));
        vbox.getChildren().add(hBox);
        aTop.getChildren().add(vbox);
    }

    private void prepareSaveAndCloseButton(HBox aBottom, Slider aSlider) {
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        saveButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);
        saveButton.setAlignment(Pos.CENTER);
        cancelButton.setAlignment(Pos.CENTER);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForChoosingAmount.close());
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            aSlider.setValue(0);
            windowForChoosingAmount.close();
        });
        aBottom.setAlignment(Pos.CENTER);
        aBottom.getChildren().add(saveButton);
        aBottom.getChildren().add(cancelButton);
    }

    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(99);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }

    private void prepareWindowForChoosingAmount(HBox aBottom, VBox aCenter, HBox aTop) {
        windowForChoosingAmount = new Stage();
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 500, 300);
        scene.getStylesheets().add("fxml/main.css");
        windowForChoosingAmount.setScene(scene);
        windowForChoosingAmount.initOwner(this.getScene().getWindow());
        windowForChoosingAmount.initModality(Modality.APPLICATION_MODAL);
        windowForChoosingAmount.setTitle("Choosing amount of " + getName());
        windowForChoosingAmount.setResizable(false);
        aBottom.setAlignment(Pos.CENTER);
        aCenter.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setCenter(aCenter);
        pane.setTop(aTop);
    }

    private String getName() {
        return nameOfCreature;
    }
}
