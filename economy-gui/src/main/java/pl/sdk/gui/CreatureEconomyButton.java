package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.creatures.RandomizeAmountOfCreaturesInShop;
import pl.sdk.hero.EconomyHero;

import static pl.sdk.converter.EcoBattleConverter.convert;

public abstract class CreatureEconomyButton extends Button {

    protected int amountOfCreaturesInStack;
    private Stage windowForChoosingAmount;
    private String nameOfCreature;
    protected boolean trading;

    public CreatureEconomyButton(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount) {
        super(aFactory.Create(aIsUpgraded, aTier, 1).getName());
        nameOfCreature = getText();
        setAmountForStackInShop(aTier, aIsUpgraded, aHero, aRandomize);
        setAppearance(aFactory.Create(aIsUpgraded, aTier, aAmount));

        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            EconomyCreature eCreature = aFactory.Create(aIsUpgraded, aTier, aAmount);
            if (e.getButton() == MouseButton.PRIMARY) {
                int amount = displayChoosingAmountAndGetCreatureAmount(eCreature, aHero);
                tradeCreatureAndSetRandomize(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomize, amount);
                aController.refreshGui();
            }
            else if(e.getButton() == MouseButton.SECONDARY) {
                Creature creature = convert(eCreature,aFactory.getFraction());
                new UnitWindow(creature, (Stage) getScene().getWindow()).showAndWait();
            }
        });
    }

    public CreatureEconomyButton(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize) {
        this(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomize, 1);
    }

    private void tradeCreatureAndSetRandomize(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount) {
        if (aAmount > 0 && trading) {
            EconomyCreature creature = aFactory.Create(aIsUpgraded, aTier, aAmount);
            tradeAction(aController, aTier, aIsUpgraded, aHero, aRandomize, aAmount, creature);
        }
    }

    protected abstract void tradeAction(EconomyController aController, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount, EconomyCreature aCreature);

    protected abstract void setAppearance(EconomyCreature aEconomyCreature);

    protected abstract Slider createSlider(EconomyCreature aCreate, EconomyHero aHero);

    protected void setAmountForStackInShop(int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize) {
        amountOfCreaturesInStack = aRandomize.getAmountOfTier(aTier, aHero.getFraction(), aIsUpgraded);
    }

    protected int getAmountOfCreaturesInStack() {
        return amountOfCreaturesInStack;
    }

    private int displayChoosingAmountAndGetCreatureAmount(EconomyCreature aCreate, EconomyHero aHero) {
        HBox top = new HBox();
        VBox center = new VBox();
        HBox bottom = new HBox();
        prepareWindowForChoosingAmount(bottom, center, top);
        Slider slider = createSlider(aCreate, aHero);
        prepareSaveAndCloseButton(bottom);
        prepareTop(top, slider, aCreate);
        center.getChildren().add(slider);
        windowForChoosingAmount.showAndWait();

        return (int) slider.getValue();
    }

    private void prepareWindowForChoosingAmount(HBox aBottom, VBox aCenter, HBox aTop) {
        windowForChoosingAmount = new Stage();
        windowForChoosingAmount.getIcons().add(new Image("jpg/icon.jpg"));
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 500, 300);
        scene.getStylesheets().add("fxml/main.css");
        windowForChoosingAmount.setScene(scene);
        windowForChoosingAmount.initOwner(this.getScene().getWindow());
        windowForChoosingAmount.initModality(Modality.APPLICATION_MODAL);
        windowForChoosingAmount.setResizable(false);
        setTitle(windowForChoosingAmount);
        aBottom.setAlignment(Pos.CENTER);
        aCenter.setAlignment(Pos.CENTER);
        aTop.setAlignment(Pos.CENTER);
        pane.setBottom(aBottom);
        pane.setCenter(aCenter);
        pane.setTop(aTop);
    }

    private void prepareTop(HBox aTop, Slider aSlider, EconomyCreature aCreate) {
        VBox vbox = new VBox(5);
        Label sliderValueLabel = new Label("0");
        Label purchaseCostValueLabel = new Label("0");
        aSlider.valueProperty().addListener((slider, aOld, aNew) -> sliderValueLabel.setText(String.valueOf(aNew.intValue())));
        aSlider.valueProperty().addListener((slider, aOld, aNew) -> purchaseCostValueLabel.setText(String.valueOf(aNew.intValue() * aCreate.getGoldCost())));
        HBox hBox = new HBox();
        Label label = new Label("Amount:");
        hBox.getChildren().add(label);
        hBox.getChildren().add(sliderValueLabel);
        hBox.setAlignment(Pos.CENTER);
        HBox hBoxForCosts = new HBox(0, new Label("Single Cost:  " + aCreate.getGoldCost() + "  Purchase Cost:"), purchaseCostValueLabel);
        vbox.getChildren().add(hBoxForCosts);
        vbox.getChildren().add(hBox);
        aTop.getChildren().add(vbox);
    }

    private void prepareSaveAndCloseButton(HBox aBottom) {
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        saveButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);
        saveButton.setAlignment(Pos.CENTER);
        cancelButton.setAlignment(Pos.CENTER);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                trading = true;
                windowForChoosingAmount.close();
            }
        });
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) windowForChoosingAmount.close();
        });
        aBottom.setAlignment(Pos.CENTER);
        aBottom.getChildren().add(saveButton);
        aBottom.getChildren().add(cancelButton);
    }

    protected abstract void setTitle(Stage aWindowForChoosingAmount);

    protected String getName() {
        return nameOfCreature;
    }
}
