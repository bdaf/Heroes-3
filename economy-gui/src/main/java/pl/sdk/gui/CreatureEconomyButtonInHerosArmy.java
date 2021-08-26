package pl.sdk.gui;


import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.EconomyFactory;
import pl.sdk.creatures.RandomizeAmountOfCreaturesInShop;
import pl.sdk.hero.EconomyHero;

public class CreatureEconomyButtonInHerosArmy extends CreatureEconomyButton {

    public CreatureEconomyButtonInHerosArmy(EconomyController aController, EconomyFactory aFactory, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount) {
        super(aController, aFactory, aTier, aIsUpgraded, aHero, aRandomize, aAmount);
    }

    @Override
    protected void setAmountForStackInShop(int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize) {}

    @Override
    protected void setAppearance(EconomyCreature aEconomyCreature) {
        setText(aEconomyCreature.getName() + " (" + aEconomyCreature.getAmount() + ")");
        ImageView image = GraphicsOfCreaturesMaker.getGraphicsOf(aEconomyCreature.getName(), Creature.Team.LEFT_TEAM, 95, 100);
        setGraphic(image);
        setPrefWidth(320);
        setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void tradeAction(EconomyController aController, int aTier, boolean aIsUpgraded, EconomyHero aHero, RandomizeAmountOfCreaturesInShop aRandomize, int aAmount, EconomyCreature aCreature) {
        if (aController.sell(aCreature))
            aRandomize.setAmountOfTier(aTier, aHero.getFraction(), aRandomize.getAmountOfTier(aTier, aHero.getFraction(), aIsUpgraded) + aAmount, aIsUpgraded);
    }

    @Override
    protected void setTitle(Stage aWindowForChoosingAmount) {
        aWindowForChoosingAmount.setTitle("Selling " + getName());
    }

    @Override
    protected Slider createSlider(EconomyCreature aCreate, EconomyHero aHero) {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(aCreate.getAmount());
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(10);
        return slider;
    }
}
