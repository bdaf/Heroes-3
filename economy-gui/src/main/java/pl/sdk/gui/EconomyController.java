package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.sdk.creatures.*;

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
    @FXML
    Label warningLabel;

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
        addEventHandlerForReadyButton();
        countScene = 0;
        refreshGui();
    }

    private void startGame() {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/battleMap.fxml"));
            loader.setController(new BattleMapController(creaturesInPlayers1Army, creaturesInPlayers2Army));
            scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("FXML Welcome");
            stage.setScene(scene);
            stage.show();
            //pane.setStyle("-fx-border-color: black");
        } catch (IOException aE) {
            aE.printStackTrace();
        }

    }

    void buy(Creature aCreature) {
        warningLabel.setOpacity(0);
        if(creaturesInArmy.size()<15)
            creaturesInArmy.add(aCreature);
        else{
            warningLabel.setOpacity(1);
        }
    }

    void refreshGui() {
        clearingArmyAndShopBoxesAndMakingTheirLabels();
        Factory factory = new CastleFactory();
        VBox shopCreatures = new VBox();
        for (int i = 0; i < 14; i++) {
            if (i == 7)
                factory = new NecropolisFactory();
            shopCreatures.getChildren().add(new CreatureButtonInShop(this, factory, (i % 7) + 1, true));
        }
        hBoxForArmyShop.getChildren().add(shopCreatures);
        creaturesInArmy.forEach(x -> hBoxForUserArmy.getChildren().add(new CreatureButtonInArmy(this, x)));
    }

    private void addEventHandlerForReadyButton() {
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if (countScene >= 3) {
                startGame();
            } else if (creaturesInArmy == creaturesInPlayers1Army) {
                playerLabel.setText("Player 2's Choice");
                creaturesInArmy = creaturesInPlayers2Army;
                countScene++;
            } else if (creaturesInArmy == creaturesInPlayers2Army) {
                playerLabel.setText("Player 1's Choice");
                creaturesInArmy = creaturesInPlayers1Army;
            }
            refreshGui();
        });
    }

    void sell(Creature aCreature) {
        if (creaturesInArmy.contains(aCreature)) {
            creaturesInArmy.remove(aCreature);
        }
    }

    private void clearingArmyAndShopBoxesAndMakingTheirLabels() {
        hBoxForArmyShop.getChildren().clear();
        hBoxForUserArmy.getChildren().clear();
        Label label = new Label("SHOP");
        label.setId("bigLabel");
        hBoxForArmyShop.getChildren().add(label);
        label = new Label("YOUR ARMY");
        label.setId("bigLabel");
        hBoxForUserArmy.getChildren().add(label);
    }
}
