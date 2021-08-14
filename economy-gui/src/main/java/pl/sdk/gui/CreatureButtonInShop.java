package pl.sdk.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.hero.EconomyHero;

public class CreatureButtonInShop extends Button {


    private int amountOfCreaturesInStack;
    private Stage windowForChoosingAmount;
    private String nameOfCreature;
    protected boolean trading;

    public CreatureButtonInShop(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount) {
        super(aFactory.Create(aIsUpgraded, aTier, 1).getName());
        nameOfCreature = getText();
        getAmountForStack(aTier, aIsUpgraded, aHero, aRandomize);
        setAppearance(aFactory.Create(aIsUpgraded, aTier, aAmount));
        addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            EconomyCreature eCreature = aFactory.Create(aIsUpgraded, aTier, aAmount);
            int amount = displayChoosingAmountAndGetCreatureAmount(eCreature, aHero);
            tradeCreatureAndSetRandomize(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomize, amount);
            aController.refreshGui();
        });
    }

    public CreatureButtonInShop(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize) {
        this(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomize, 1);
    }

    protected void getAmountForStack(int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize) {
        amountOfCreaturesInStack =  aRandomize.getAmountOfTier(aTier, aHero.getFraction(), aIsUpgraded);
    }

    protected void tradeCreatureAndSetRandomize(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount) {
        if (aAmount > 0 && trading) {
            EconomyCreature creature = aFactory.Create(aIsUpgraded, aTier, aAmount);
            if (aController.buy(creature))
                aRandomize.setAmountOfTier(aTier, aHero.getFraction(), aRandomize.getAmountOfTier(aTier, aHero.getFraction(), aIsUpgraded) - aAmount, aIsUpgraded);
        }
    }


    protected void setAppearance(EconomyCreature aEconomyCreature) {
        setTextAlignment(TextAlignment.CENTER);
        String shots = "";
        if(aEconomyCreature.getStats().getShots() > 0) shots = " - "+aEconomyCreature.getStats().getShots()+" shots";
        setStyle("-fx-font-size: 18px;");
        setText(nameOfCreature + " - " + amountOfCreaturesInStack + " amount"+shots
                + "\nHealth: " + aEconomyCreature.getStats().getMaxHp()
                + " | Attack: " + aEconomyCreature.getStats().getAttack()
                + " | Damage: " + aEconomyCreature.getStats().getDamage().lowerEndpoint() + " - " + aEconomyCreature.getStats().getDamage().upperEndpoint()
                + " |\nCost: " + aEconomyCreature.getGoldCost()
                + " | Defense: " + aEconomyCreature.getStats().getArmor()
                + " | Movement: " + aEconomyCreature.getStats().getMoveRange());
        ImageView image = GraphicsOfCreaturesMaker.getGraphicsOf(nameOfCreature, Creature.Team.RIGHT_TEAM);
        setPrefHeight(200);
        setPrefWidth(500);
        setGraphic(image);
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
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> {
            trading = true;
            windowForChoosingAmount.close();
        });
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, x -> windowForChoosingAmount.close());
        aBottom.setAlignment(Pos.CENTER);
        aBottom.getChildren().add(saveButton);
        aBottom.getChildren().add(cancelButton);
    }

    protected Slider createSlider(EconomyCreature aCreate, EconomyHero aHero) {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(Integer.min(aHero.getGold() / aCreate.getGoldCost(), amountOfCreaturesInStack));
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(10);
        return slider;
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

    protected void setTitle(Stage aWindowForChoosingAmount) {
        aWindowForChoosingAmount.setTitle("Buying " + getName());
    }


    protected String getName() {
        return nameOfCreature;
    }
}
