package pl.sdk.creatures;

import pl.sdk.hero.EconomyHero;

import static pl.sdk.converter.ProperFractionConverter.getProperEconomyFactoryForFractionOf;

public class ChangerAmountInStack {

    public EconomyCreature returnCreatureInStackWithChangedAmount(int aAmount, EconomyCreature aCreature, EconomyHero aEconomyHero){
        if(aAmount<0) throw new IllegalArgumentException("Amount of creature has to be positive!");
        EconomyFactory factory = getProperEconomyFactoryForFractionOf(aEconomyHero);
        return factory.Create(aCreature.isUpgraded(),aCreature.getTier(),aAmount);
    }
}
