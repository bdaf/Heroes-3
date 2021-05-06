package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.sdk.creatures.CastleFactory;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;
import pl.sdk.creatures.NecropolisFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static javafx.application.Platform.exit;

public class EconomyController {
    @FXML
    Button readyButton;
    @FXML
    Button escapeButton;
    @FXML
    VBox hBoxForUserArmy;
    @FXML
    VBox hBoxForArmyShop;
    @FXML
    Label playerLabel;

    private List<Creature> creaturesInShop;
    private List<Creature> creaturesInArmy;
    private List<Creature> creaturesInPlayers1Army;
    private List<Creature> creaturesInPlayers2Army;
    private int countScene;

    @FXML
    void initialize() {
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> exit());
        creaturesInPlayers1Army = new LinkedList<Creature>();
        creaturesInPlayers2Army = new LinkedList<Creature>();
        creaturesInArmy = creaturesInPlayers1Army;
        creaturesInShop = new LinkedList<Creature>();
        Factory factory = new CastleFactory();
        for (int i = 0; i < 14; i++) {
            if (i == 7)
                factory = new NecropolisFactory();
            creaturesInShop.add(factory.Create(true, (i % 7) + 1,1));
        }
        addEventHandlerForReadyButton();
        countScene = 0;
        refreshGui();
    }

    private void startGame(){
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/battleMap.fxml"));
            loader.setController(new BattleMapController(creaturesInPlayers1Army,creaturesInPlayers2Army));
            scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException aE) {
            aE.printStackTrace();
        }

    }

    void buyOrSell(Creature aCreatureButton) {
        if (creaturesInArmy.contains(aCreatureButton)) {
            creaturesInArmy.remove(aCreatureButton);
        } else if (creaturesInShop.contains(aCreatureButton)) {
            creaturesInArmy.add(aCreatureButton);
        }
    }

    void refreshGui() {
        hBoxForArmyShop.getChildren().clear();
        hBoxForUserArmy.getChildren().clear();
        hBoxForArmyShop.getChildren().add(new Button("SHOP"));
        hBoxForUserArmy.getChildren().add(new Button("YOUR ARMY"));
        creaturesInShop.forEach(x -> hBoxForArmyShop.getChildren().add(new CreatureButton(this, x)));
        creaturesInArmy.forEach(x -> hBoxForUserArmy.getChildren().add(new CreatureButton(this, x)));
    }

    private void addEventHandlerForReadyButton() {
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if(countScene >=3){
                startGame();
            }
            else if (playerLabel.getText().equalsIgnoreCase("Player 1's Choice")) {
                playerLabel.setText("Player 2's Choice");
                creaturesInArmy = creaturesInPlayers2Army;
                countScene++;
            } else {
                playerLabel.setText("Player 1's Choice");
                creaturesInArmy = creaturesInPlayers1Army;
            }
            refreshGui();
        });
    }
}
