package pl.sdk.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.sdk.EconomyEngine;
import pl.sdk.converter.EcoBattleConverter;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.hero.EconomyHero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static javafx.application.Platform.exit;
import static pl.sdk.EconomyEngine.*;

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
    private AmountOfCreaturesInStacksToBuyRandomize randomize;

    EconomyController(EconomyHero aLeftHero, EconomyHero aRightHero) {
        economyEngine = new EconomyEngine(aLeftHero, aRightHero);
    }

    @FXML
    void initialize() {
        randomize = new AmountOfCreaturesInStacksToBuyRandomize();
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
        else EcoBattleConverter.start(economyEngine.getLeftHero(), economyEngine.getRightHero());

    }

    void buy(EconomyCreature aCreature) {
        warningLabel.setOpacity(0);
        if (economyEngine.getActiveHero().getHeroArmy().size() < 7)
            economyEngine.buy(aCreature);
        else warningLabel.setOpacity(1);
    }

    void refreshGui() {
        clearingArmyAndShopBoxesAndMakingTheirLabels();
        EconomyFactory factory = EcoBattleConverter.getProperEconomyFactoryForFraction(economyEngine.getActiveHero());

        VBox vBoxShopCreaturesUpgraded = new VBox();
        VBox vBoxShopCreaturesNotUpgraded = new VBox();
        vBoxShopCreaturesUpgraded.setAlignment(Pos.CENTER);
        vBoxShopCreaturesNotUpgraded.setAlignment(Pos.CENTER);

        HBox hBoxShopContainer = new HBox();
        hBoxShopContainer.getChildren().addAll(vBoxShopCreaturesUpgraded, vBoxShopCreaturesNotUpgraded);
        hBoxShopContainer.setAlignment(Pos.CENTER);

        vBoxForArmyShop.getChildren().add(hBoxShopContainer);

        addingCreatureButtonsInShopToBox(vBoxShopCreaturesNotUpgraded,factory,false);
        addingCreatureButtonsInShopToBox(vBoxShopCreaturesUpgraded,factory,true);

        economyEngine.getActiveHero().getHeroArmy().forEach(x -> vBoxForUserArmy.getChildren().add(new CreatureButtonInArmy(this, x)));
        goldLabel.setText("Round: " + economyEngine.getRoundNumber() + " Gold: " + economyEngine.getActiveHero().getGold());
    }

    private void addingCreatureButtonsInShopToBox(Pane aBox, EconomyFactory aFactory, boolean aIsUpgraded) {
        for (int i = 0; i < 7; i++) {
            aBox.getChildren().add(new CreatureButtonInShop(this, aFactory, i +1 , aIsUpgraded, economyEngine.getActiveHero(),
            randomize));
        }
    }


    private void addEventHandlerForReadyButtonAndSetPlayerLabel() {
        playerLabel.setText("Player 1's Choice - " + economyEngine.getActiveHero().toString());
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            int roundNumber = economyEngine.getRoundNumber();
            if (economyEngine.getActiveHero().equals(economyEngine.getRightHero())) roundNumber++;
            if (roundNumber > 4) {
                startGame();
                return;
            }
            economyEngine.pass();
            randomize = new AmountOfCreaturesInStacksToBuyRandomize();
            changePlayerName();
            refreshGui();
        });
    }

    private void changePlayerName() {
        if (playerLabel.getText().contains("Player 1's Choice"))
            playerLabel.setText("Player 2's Choice - " + economyEngine.getActiveHero().toString());
        else
            playerLabel.setText("Player 1's Choice - " + economyEngine.getActiveHero().toString());
    }

    void sell(EconomyCreature aCreature) {
        economyEngine.sell(aCreature);
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
