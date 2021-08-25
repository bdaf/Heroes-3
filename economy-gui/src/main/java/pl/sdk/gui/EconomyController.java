package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private EconomyEngine economyEngine;
    private RandomizeAmountOfCreaturesInShop randomize;

    EconomyController(EconomyHero aLeftHero, EconomyHero aRightHero) {
        economyEngine = new EconomyEngine(aLeftHero, aRightHero);
    }

    @FXML
    void initialize() {
        randomize = new RandomizeAmountOfCreaturesInShop();
        escapeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> exit());
        economyEngine.addObserver(HERO_BOUGHT_CREATURE, this);
        economyEngine.addObserver(ACTIVE_HERO_CHANGED, this);
        economyEngine.addObserver(NEXT_ROUND_STARTED, this);
        addEventHandlerForReadyButtonAndSetPlayerLabel();
        refreshGui();
    }

    private void startGame() {
        warningNeedToBuyLabel.setOpacity(0);
        if (economyEngine.getLeftHero().getHeroArmy().isEmpty() || economyEngine.getRightHero().getHeroArmy().isEmpty())
            warningNeedToBuyLabel.setOpacity(1);
        else EcoBattleConverter.start(economyEngine.getLeftHero(), economyEngine.getRightHero(), (Stage) readyButton.getScene().getWindow());

    }

    boolean buy(EconomyCreature aCreature) {
        boolean ret = true;
        warningLabel.setOpacity(0);
        try {
            economyEngine.buy(aCreature);
        }catch (IllegalStateException e){
            warningLabel.setOpacity(1);
            ret = false;
        }
        return ret;
    }

    void refreshGui() {
        clearingArmyAndShopBoxesAndMakingTheirLabels();
        EconomyFactory factory = getProperEconomyFactoryForFractionOf(economyEngine.getActiveHero());

        VBox vBoxShopCreaturesUpgraded = new VBox();
        VBox vBoxShopCreaturesNotUpgraded = new VBox();
        vBoxShopCreaturesUpgraded.setAlignment(Pos.CENTER);
        vBoxShopCreaturesNotUpgraded.setAlignment(Pos.CENTER);

        HBox hBoxShopContainer = new HBox();
        hBoxShopContainer.getChildren().addAll(vBoxShopCreaturesNotUpgraded, vBoxShopCreaturesUpgraded);
        hBoxShopContainer.setAlignment(Pos.CENTER);

        vBoxForArmyShop.getChildren().add(hBoxShopContainer);

        addingCreatureButtonsInShopToBox(vBoxShopCreaturesNotUpgraded, factory, false);
        addingCreatureButtonsInShopToBox(vBoxShopCreaturesUpgraded, factory, true);

        economyEngine.getActiveHero().getHeroArmy().forEach(c -> vBoxForUserArmy.getChildren().add(new CreatureButtonInHerosArmy(
                this, factory, c.getTier(), c.isUpgraded(), economyEngine.getActiveHero(), randomize,c.getAmount())));

        goldLabel.setText("Round: " + economyEngine.getRoundNumber() + "/4  Gold: " + economyEngine.getActiveHero().getGold());
    }

    private void addingCreatureButtonsInShopToBox(Pane aBox, EconomyFactory aFactory, boolean aIsUpgraded) {
        for (int i = 0; i < 7; i++) {
            aBox.getChildren().add(new CreatureButtonInShop(this, aFactory, i + 1, aIsUpgraded, economyEngine.getActiveHero(),
                    randomize));
        }
    }


    private void addEventHandlerForReadyButtonAndSetPlayerLabel() {
        playerLabel.setText("Left Player's Choice - " + economyEngine.getActiveHero().getFraction());
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            int roundNumber = economyEngine.getRoundNumber();
            if (economyEngine.getActiveHero().equals(economyEngine.getRightHero())) roundNumber++;
            if (roundNumber > 4) {
                startGame();
                return;
            }
            economyEngine.pass();
            randomize = new RandomizeAmountOfCreaturesInShop();
            changePlayerName();
            refreshGui();
        });
    }

    private void changePlayerName() {
        if (playerLabel.getText().contains("Left")) playerLabel.setText("Right Player's Choice - " + economyEngine.getActiveHero().getFraction());
        else playerLabel.setText("Left Player's Choice - " + economyEngine.getActiveHero().getFraction());
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
        refreshGui();
    }
}
