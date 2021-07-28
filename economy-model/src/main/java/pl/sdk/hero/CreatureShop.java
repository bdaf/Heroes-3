package pl.sdk.hero;

import pl.sdk.creatures.EconomyCreature;

public class CreatureShop {

    public void buy(EconomyHero aEconomyHero, EconomyCreature aCreature) {
        aEconomyHero.subtractGold(aCreature.getAmount() * aCreature.getGoldCost());
        try {
            aEconomyHero.addCreature(aCreature);
        } catch (IllegalStateException e) {
            aEconomyHero.addGold(aCreature.getAmount() * aCreature.getGoldCost());
            throw new IllegalStateException("Army can't include more stacks of creatures!");
        }
    }

    public boolean sell(EconomyHero aEconomyHero, EconomyCreature aCreature) {
        if(aEconomyHero.removeCreature(aCreature)){
            aEconomyHero.addGold(aCreature.getAmount() * aCreature.getGoldCost());
            return true;
        }
        return false;
    }
}
