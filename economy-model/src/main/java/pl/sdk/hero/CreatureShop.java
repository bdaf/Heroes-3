package pl.sdk.hero;

import pl.sdk.creatures.EconomyCreature;
import pl.sdk.creatures.ChangerAmountInStack;
import java.util.List;

public class CreatureShop {

    final private ChangerAmountInStack changer = new ChangerAmountInStack();

    public void buy(EconomyHero aEconomyHero, EconomyCreature aCreature) {
        aEconomyHero.subtractGold(aCreature.getAmount() * aCreature.getGoldCost());
        try {
            List<EconomyCreature> armyOfHero = aEconomyHero.getHeroArmy();
            if (!armyOfHero.contains(aCreature)) aEconomyHero.addCreature(aCreature);
            else changeNumberOfAmountInStack(aEconomyHero, aCreature, Operation.addition);
        } catch (IllegalStateException e) {
            aEconomyHero.addGold(aCreature.getAmount() * aCreature.getGoldCost());
            throw new IllegalStateException("Army can't include more stacks of creatures!");
        }
    }

    private int changeNumberOfAmountInStack(EconomyHero aEconomyHero, EconomyCreature aCreature, Operation aOperation) {
        List<EconomyCreature> aArmyOfHero = aEconomyHero.getHeroArmy();
        int finalAmount = -1;
        for (int i = 0; i < aArmyOfHero.size(); i++) {
            if (aArmyOfHero.get(i).equals(aCreature)) {
                int amountToChange = aCreature.getAmount() * aOperation.getMultiplier();
                finalAmount = aArmyOfHero.get(i).getAmount() + amountToChange;
                EconomyCreature creatureToExchange = changer.returnCreatureInStackWithChangedAmount(finalAmount, aCreature, aEconomyHero);
                aEconomyHero.replaceCreature(creatureToExchange, i);
                break;
            }
        }
        return finalAmount;
    }

    public boolean sell(EconomyHero aEconomyHero, EconomyCreature aCreature) {
        boolean result = true;
        int amountInStack = changeNumberOfAmountInStack(aEconomyHero, aCreature, Operation.subtraction);
        if (amountInStack == -1) throw new IllegalStateException("Probably cannot find this creature in Hero's Army!");
        else if (amountInStack == 0) {
            if (aEconomyHero.removeCreature(aCreature)) result = true;
            else result = false;
        }
        aEconomyHero.addGold(aCreature.getAmount() * aCreature.getGoldCost());
        return result;
    }

    private enum Operation {
        addition(1),
        subtraction(-1);

        private final int multiplier;

        Operation(int aMultiplier) {
            multiplier = aMultiplier;
        }

        int getMultiplier() {
            return multiplier;
        }
    }

}
