package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.sdk.settings.EconomySettings;

public class ChooseKindOfGameController {

    private final EconomyStart economyStart;
    @FXML
    Button playButton;
    @FXML
    ToggleGroup kindOfGame;
    @FXML
    RadioButton quickGameRadioButton;
    @FXML
    RadioButton tournamentRadioButton;

    ChooseKindOfGameController(EconomyStart aStart){
        economyStart = aStart;
    }

    @FXML
    void initialize(){
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            if(e.getButton() == MouseButton.PRIMARY){
                if(kindOfGame.getSelectedToggle().equals(quickGameRadioButton))
                    economyStart.setKindOfGame(new EconomySettings(1,1,5000));
                else if(kindOfGame.getSelectedToggle().equals(tournamentRadioButton))
                    economyStart.setKindOfGame(new EconomySettings(3,3,3000,1000));
                else throw new IllegalStateException("Neither of kinds of game has been selected!");
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.close();
            }
        });

    }
}
