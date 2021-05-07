package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;


public class CreatureButtonInShop extends Button {

    private Stage windowForChoosingAmount;
    private String nameOfCreature;

    public CreatureButtonInShop(EconomyController aController, Factory aFactory, int aTier, boolean aIsUpgraded){
        super(aFactory.Create(aIsUpgraded,aTier,1).getName());
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            byte amount = displayChoosingAmountAndGetCreatureAmount();
            if(amount > 0){
                Creature creature = aFactory.Create(aIsUpgraded,aTier,amount);
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
        prepareWindowFroChoosingAmount(bottom, center, top);
        Slider slider = createSlider();
        prepareSaveAndCloseButton(bottom, slider);

        center.getChildren().add(slider);
        windowForChoosingAmount.showAndWait();

        return (byte) slider.getValue();
    }

    private void prepareSaveAndCloseButton(HBox aBottom, Slider aSlider) {
        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");
        saveButton.setAlignment(Pos.CENTER);
        closeButton.setAlignment(Pos.CENTER);
        saveButton.setPrefWidth(200);
        closeButton.setPrefWidth(200);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForChoosingAmount.close());
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x ->{
            aSlider.setValue(0);
            windowForChoosingAmount.close();
        });
        aBottom.getChildren().add(saveButton);
        aBottom.getChildren().add(closeButton);
    }

    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }

    private void prepareWindowFroChoosingAmount(HBox aBottom, VBox aCenter, HBox aTop) {
        windowForChoosingAmount = new Stage();
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,500,500);
        windowForChoosingAmount.setScene(scene);
        windowForChoosingAmount.initOwner(this.getScene().getWindow());
        windowForChoosingAmount.initModality(Modality.APPLICATION_MODAL);
        windowForChoosingAmount.setTitle("Choosing amount of "+getName());

        pane.setBottom(aBottom);
        pane.setCenter(aCenter);
        pane.setTop(aTop);
    }

    private String getName() {
        return nameOfCreature;
    }
}
