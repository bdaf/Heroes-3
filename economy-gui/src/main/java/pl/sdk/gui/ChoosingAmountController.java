package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ChoosingAmountController {
    private final EconomyController economyController;
    private CreatureButtonInShop creatureButton;
    private byte amount;
    @FXML
    Slider amountSlider;
    @FXML
    Button saveButton;
    @FXML
    Label amountOfCreatureLabel;
    private Stage stage;


    public ChoosingAmountController(CreatureButtonInShop aCreatureButtonInShop, EconomyController aEconomyController) {
        creatureButton = aCreatureButtonInShop;
        economyController = aEconomyController;
    }

    @FXML
    void initialize(){
        amountSlider.setMin(1);
        amountSlider.setMax(99);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            amount = (byte) amountSlider.getValue();
            creatureButton.setAmount(amount);
            economyController.refreshGui();
        });
        amountSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, x ->{
            amountOfCreatureLabel.setText(String.valueOf((byte)amountSlider.getValue()));
        });

    }

    void setStage(Stage aStage) {
        stage = aStage;
    }

}
