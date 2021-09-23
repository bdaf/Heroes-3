package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.sdk.settings.ModeOfGame;

public class ChooseModeOfGameController {

    private final EconomyStart economyStart;
    @FXML
    Button playButton;
    @FXML
    ToggleGroup modeOfGame;
    @FXML
    RadioButton quickGameRadioButton;
    @FXML
    RadioButton tournamentRadioButton;

    ChooseModeOfGameController(EconomyStart aStart){
        economyStart = aStart;
    }

    @FXML
    void initialize(){
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            if(e.getButton() == MouseButton.PRIMARY){
                if(modeOfGame.getSelectedToggle().equals(quickGameRadioButton))
                    economyStart.setModeOfGame(ModeOfGame.QUICK_GAME);
                else if(modeOfGame.getSelectedToggle().equals(tournamentRadioButton))
                    economyStart.setModeOfGame(ModeOfGame.TOURNAMENT);
                else throw new IllegalStateException("Neither of kinds of game has been selected!");
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.close();
            }
        });

    }
}
