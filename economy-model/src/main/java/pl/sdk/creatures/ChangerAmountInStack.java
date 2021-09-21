package pl.sdk.creatures;

import pl.sdk.hero.EconomyHero;

import static pl.sdk.converter.ProperFractionConverter.getProperEconomyFactoryBasedOnFraction;

public class ChangerAmountInStack {

    public EconomyCreature getCreatureInStackWithChangedAmount(int aAmount, EconomyCreature aCreature, EconomyHero aEconomyHero){
        if(aAmount<0) throw new IllegalArgumentException("Amount of creature has to be positive!");
        EconomyFactory factory = getProperEconomyFactoryBasedOnFraction(aEconomyHero.getFraction());
        return factory.Create(aCreature.isUpgraded(),aCreature.getTier(),aAmount);
    }
}
