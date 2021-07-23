package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.sdk.EconomyEngine;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.creatures.EconomyNecropolisFactory;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static javafx.application.Platform.exit;
import static pl.sdk.EconomyEngine.*;

public class EconomyController implements PropertyChangeListener {
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
    Label goldLabel;
    @FXML
    Label warningLabel;

    private List<EconomyCreature> creaturesInShop;
    private EconomyEngine engine;

    EconomyController(EconomyHero aLeftHero, EconomyHero aRightHero){
        engine = new EconomyEngine(aLeftHero,aRightHero);
    }

    @FXML
    void initialize() {
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> exit());
        engine.addObserver(HERO_BOUGHT_CREATURE,this);
        engine.addObserver(ACTIVE_HERO_CHANGED,this);
        engine.addObserver(NEXT_ROUND_STARTED,this);
        creaturesInShop = new LinkedList<EconomyCreature>();
        addEventHandlerForReadyButton();
        refreshGui();
    }

    private void startGame() {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/battleMap.fxml"));
            //loader.setController(new BattleMapController(creaturesInPlayers1Army, creaturesInPlayers2Army));
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

    void buy(EconomyCreature aCreature) {
        warningLabel.setOpacity(0);
        if(engine.getActiveHero().getHeroArmy().size()<15)
            engine.buy(aCreature);
        else{
            warningLabel.setOpacity(1);
        }
    }

    void refreshGui() {
        clearingArmyAndShopBoxesAndMakingTheirLabels();
        EconomyFactory factory = new EconomyCastleFactory();
        VBox shopCreatures = new VBox();
        for (int i = 0; i < 14; i++) {
            if (i == 7)
                factory = new EconomyNecropolisFactory();
            shopCreatures.getChildren().add(new CreatureButtonInShop(this, factory, (i % 7) + 1, true));
        }
        hBoxForArmyShop.getChildren().add(shopCreatures);
        engine.getActiveHero().getHeroArmy().forEach(x -> hBoxForUserArmy.getChildren().add(new CreatureButtonInArmy(this, x)));
        goldLabel.setText("Gold: "+engine.getActiveHero().getGold());
    }

    private void addEventHandlerForReadyButton() {
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            if (engine.getRoundNumber() >= 3) {
                startGame();
                return;
            }
            engine.pass();
            changePlayerName();
            if (engine.getRoundNumber() >= 3) {
                startGame();
                return;
            }
            refreshGui();
        });
    }

    private void changePlayerName() {
        if(playerLabel.getText().equals("Player 1's Choice")){
            playerLabel.setText("Player 2's Choice");
        } else {
            playerLabel.setText("Player 1's Choice");
        }
    }

    void sell(EconomyCreature aCreature) {
        List<EconomyCreature> creaturesInArmy = engine.getActiveHero().getHeroArmy();
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

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        refreshGui();
    }
}
