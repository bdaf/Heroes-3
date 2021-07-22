package pl.sdk.hero;

import pl.sdk.creatures.EconomyCreature;

public class CreatureShop {

    public void buy(EconomyHero aEconomyHero, EconomyCreature aCreate) {
        aEconomyHero.subtractGold(aCreate.getAmount() * aCreate.getGoldCost());
        try {
            aEconomyHero.addCreature(aCreate);
        } catch (IllegalStateException e) {
            aEconomyHero.addGold(aCreate.getAmount() * aCreate.getGoldCost());
            throw new IllegalStateException("Army can't include more stacks of creatures!");
        }
    }
}
