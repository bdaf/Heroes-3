package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ChoosingAmountController {
    private final EconomyController economyController;
    private CreatureButton creatureButton;
    private short amount;
    @FXML
    TextField amountField;
    @FXML
    Button saveButton;
    @FXML
    Label errorLabel;
    private Stage stage;


    public ChoosingAmountController(CreatureButton aCreatureButton, EconomyController aEconomyController) {
        creatureButton = aCreatureButton;
        economyController = aEconomyController;
    }

    @FXML
    void initialize(){
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if(!errorLabel.getText().equals(""))
                errorLabel.setText("");
            try {
                amount = Short.parseShort(amountField.getText());
            }catch(Exception eM){
                amount = 1;
                System.out.println("Not properly value. Has been set on '1'");
                errorLabel.setText("Not properly value!");
            }
            creatureButton.setAmount(amount);
            economyController.refreshGui();
        });
    }

    void setStage(Stage aStage) {
        stage = aStage;
    }

}
