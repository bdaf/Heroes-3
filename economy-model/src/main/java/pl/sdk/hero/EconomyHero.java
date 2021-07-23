package pl.sdk.hero;

import pl.sdk.creatures.EconomyCreature;

import java.util.LinkedList;
import java.util.List;

public class EconomyHero {


    public enum Fraction {
        NECROPOLIS, CASTLE
    }

    private final Fraction fraction;
    private final List<EconomyCreature> heroArmy;
    private int gold;

    public EconomyHero(Fraction aFraction, int aGold) {
        fraction = aFraction;
        heroArmy = new LinkedList<EconomyCreature>();
        gold = aGold;
    }

    void subtractGold(int aGold) {
        if ((gold - aGold) < 0)
            throw new IllegalStateException("Hero doesn't have enough money!");
        gold -= aGold;
    }

    public void addGold(int aAmount) {
        gold += aAmount;
    }

    void addCreature(EconomyCreature aCreature) {
        if (heroArmy.size() >= 7)
            throw new IllegalStateException("Army can't include more stacks of creatures!");
        heroArmy.add(aCreature);
    }

    public int getGold() {
        return gold;
    }

    public List<EconomyCreature> getHeroArmy() {
        return List.copyOf(heroArmy);
    }
}
