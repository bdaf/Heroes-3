package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.sdk.EconomyEngine;
import pl.sdk.converter.EcoBattleConverter;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static javafx.application.Platform.exit;
import static pl.sdk.EconomyEngine.*;
import static pl.sdk.converter.ProperFractionConverter.getProperEconomyFactoryForFractionOf;

public class EconomyController implements PropertyChangeListener {
    private EconomyEngine economyEngine;
    @FXML
    Button readyButton;
    @FXML
    Button escapeButton;
    @FXML
    VBox vBoxForUserArmy;
    @FXML
    VBox vBoxForArmyShop;
    @FXML
    Label playerLabel;
    @FXML
    Label goldLabel;
    @FXML
    Label warningLabel;
    @FXML
    Label warningNeedToBuyLabel;

    EconomyController(EconomyHero aLeftHero, EconomyHero aRightHero) {
        economyEngine = new EconomyEngine(aLeftHero, aRightHero);
    }

    @FXML
    void initialize() {
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) exit();
        });
        economyEngine.addObserver(HERO_BOUGHT_CREATURE, this);
        economyEngine.addObserver(ACTIVE_HERO_CHANGED, this);
        economyEngine.addObserver(NEXT_ROUND_STARTED, this);
        economyEngine.addObserver(END_OF_TURN, this);
        addEventHandlerForReadyButtonAndSetPlayerLabel();
        refreshGui();
    }

    private void addEventHandlerForReadyButtonAndSetPlayerLabel() {
        playerLabel.setText("Left Player's Choice - " + economyEngine.getActiveHero().getFraction());
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) pass();
        });
    }

    private void pass() {
        warningNeedToBuyLabel.setOpacity(0);
        try {
            economyEngine.pass();
        } catch (IllegalStateException e) {
            warningNeedToBuyLabel.setOpacity(1);
        }
    }

    void refreshGui() {
        clearingArmyAndShopBoxesAndMakingTheirLabels();
        EconomyFactory factory = getProperEconomyFactoryForFractionOf(economyEngine.getActiveHero().getFraction());

        VBox vBoxShopCreaturesUpgraded = new VBox();
        VBox vBoxShopCreaturesNotUpgraded = new VBox();
        vBoxShopCreaturesUpgraded.setAlignment(Pos.CENTER);
        vBoxShopCreaturesNotUpgraded.setAlignment(Pos.CENTER);

        HBox hBoxShopContainer = new HBox();
        hBoxShopContainer.getChildren().addAll(vBoxShopCreaturesNotUpgraded, vBoxShopCreaturesUpgraded);
        hBoxShopContainer.setAlignment(Pos.CENTER);

        vBoxForArmyShop.getChildren().add(hBoxShopContainer);

        addCreatureButtonsInShopToBox(vBoxShopCreaturesNotUpgraded, factory, false);
        addCreatureButtonsInShopToBox(vBoxShopCreaturesUpgraded, factory, true);

        economyEngine.getActiveHero().getHeroArmy().forEach(c -> vBoxForUserArmy.getChildren().add(new CreatureEconomyButtonInHerosArmy(
                this, factory, c.getTier(), c.isUpgraded(), economyEngine.getActiveHero(), economyEngine.getRandomizer(), c.getAmount())));

        goldLabel.setText("Round: " + economyEngine.getRoundNumber() + "/" + AMOUNT_OF_ROUNDS + " Gold: " + economyEngine.getActiveHero().getGold());
    }

    private void addCreatureButtonsInShopToBox(Pane aBox, EconomyFactory aFactory, boolean aIsUpgraded) {
        for (int i = 0; i < 7; i++) {
            aBox.getChildren().add(new CreatureEconomyButtonInShop(this, aFactory, i + 1, aIsUpgraded, economyEngine.getActiveHero(),
                    economyEngine.getRandomizer()));
        }
    }

    boolean buy(EconomyCreature aCreature) {
        boolean ret = true;
        warningLabel.setOpacity(0);
        try {
            economyEngine.buy(aCreature);
        } catch (IllegalStateException e) {
            warningLabel.setOpacity(1);
            ret = false;
        }
        return ret;
    }

    boolean sell(EconomyCreature aCreature) {
        return economyEngine.sell(aCreature);
    }

    private void clearingArmyAndShopBoxesAndMakingTheirLabels() {
        vBoxForArmyShop.getChildren().clear();
        vBoxForUserArmy.getChildren().clear();

        Label label = new Label("SHOP");
        label.setId("bigLabel");
        vBoxForArmyShop.getChildren().add(label);

        label = new Label("YOUR ARMY");
        label.setId("bigLabel");
        vBoxForUserArmy.getChildren().add(label);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        if (aPropertyChangeEvent.getPropertyName().equals(ACTIVE_HERO_CHANGED)) changePlayerName();
        if (aPropertyChangeEvent.getPropertyName().equals(END_OF_TURN)) goToBattle();
        refreshGui();
    }

    private void changePlayerName() {
        if (playerLabel.getText().contains("Left"))
            playerLabel.setText("Right Player's Choice - " + economyEngine.getActiveHero().getFraction());
        else playerLabel.setText("Left Player's Choice - " + economyEngine.getActiveHero().getFraction());
    }

    private void goToBattle() {
        EcoBattleConverter.start(economyEngine, (Stage) readyButton.getScene().getWindow());
    }
}
