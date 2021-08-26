package pl.sdk.gui;


import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.creatures.RandomizeAmountOfCreaturesInShop;
import pl.sdk.hero.EconomyHero;

public class CreatureEconomyButtonInShop extends CreatureEconomyButton {

    public CreatureEconomyButtonInShop(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomizer) {
        super(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomizer);
    }

    @Override
    protected void tradeAction(EconomyController aController, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount, EconomyCreature aCreature) {
        if (aController.buy(aCreature))
            aRandomize.setAmountOfTier(aTier, aHero.getFraction(), aRandomize.getAmountOfTier(aTier, aHero.getFraction(), aIsUpgraded) - aAmount, aIsUpgraded);
    }


    protected void setAppearance(EconomyCreature aEconomyCreature) {
        setTextAlignment(TextAlignment.CENTER);
        String shots = "";
        if (aEconomyCreature.getStats().getShots() > 0)
            shots = " - " + aEconomyCreature.getStats().getShots() + " shots";
        setStyle("-fx-font-size: 18px;");
        setText(getName() + " - " + getAmountOfCreaturesInStack() + " amount" + shots
                + "\nHealth: " + aEconomyCreature.getStats().getMaxHp()
                + " | Attack: " + aEconomyCreature.getStats().getAttack()
                + " | Damage: " + aEconomyCreature.getStats().getDamage().lowerEndpoint() + " - " + aEconomyCreature.getStats().getDamage().upperEndpoint()
                + " |\nCost: " + aEconomyCreature.getGoldCost()
                + " | Defense: " + aEconomyCreature.getStats().getArmor()
                + " | Movement: " + aEconomyCreature.getStats().getMoveRange());
        ImageView image = GraphicsOfCreaturesMaker.getGraphicsOf(getName(), Creature.Team.RIGHT_TEAM);
        setPrefHeight(200);
        setPrefWidth(500);
        setGraphic(image);
    }


    protected Slider createSlider(EconomyCreature aCreate, EconomyHero aHero) {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(Integer.min(aHero.getGold() / aCreate.getGoldCost(), getAmountOfCreaturesInStack()));
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(10);
        return slider;
    }

    protected void setTitle(Stage aWindowForChoosingAmount) {
        aWindowForChoosingAmount.setTitle("Buying " + getName());
    }

}
