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
import pl.sdk.converter.EcoBattleConverter;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.creatures.EconomyNecropolisFactory;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

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

    private EconomyEngine engine;

    EconomyController(EconomyHero aLeftHero, EconomyHero aRightHero) {
        engine = new EconomyEngine(aLeftHero, aRightHero);
    }

    @FXML
    void initialize() {
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> exit());
        engine.addObserver(HERO_BOUGHT_CREATURE, this);
        engine.addObserver(ACTIVE_HERO_CHANGED, this);
        engine.addObserver(NEXT_ROUND_STARTED, this);
        addEventHandlerForReadyButton();
        refreshGui();
    }

    private void startGame() {

        EcoBattleConverter.start(engine.getLeftHero(), engine.getRightHero());

    }

    void buy(EconomyCreature aCreature) {
        warningLabel.setOpacity(0);
        if (engine.getActiveHero().getHeroArmy().size() < 7)
            engine.buy(aCreature);
        else {
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
        goldLabel.setText("Round: " + engine.getRoundNumber() + " Gold: " + engine.getActiveHero().getGold());
    }

    private void addEventHandlerForReadyButton() {
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            int roundNumber = engine.getRoundNumber();
            if (engine.getActiveHero().equals(engine.getRightHero())) roundNumber++;
            if (roundNumber > 4) {
                startGame();
                return;
            }
            engine.pass();
            changePlayerName();
            refreshGui();
        });
    }

    private void changePlayerName() {
        if (playerLabel.getText().contains("Player 1's Choice"))
            playerLabel.setText("Player 2's Choice - " + engine.getActiveHero().toString());
        else
            playerLabel.setText("Player 1's Choice - " + engine.getActiveHero().toString());
    }

    void sell(EconomyCreature aCreature) {
        engine.sell(aCreature);
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
