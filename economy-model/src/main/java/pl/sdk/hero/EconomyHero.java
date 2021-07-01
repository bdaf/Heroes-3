package pl.sdk.hero;

import pl.sdk.creatures.EconomyCreature;

import java.util.List;

public class EconomyHero {

    public enum Fraction{
        NECROPOLIS,CASTLE
    }

    private final Fraction fraction;
    private final List<EconomyCreature> heroArmy;
    private int gold;

    public EconomyHero(Fraction aFraction, List<EconomyCreature> aHeroArmy, int aGold) {
        fraction = aFraction;
        heroArmy = aHeroArmy;
        gold = aGold;
    }

    public void subtractGold(int aGold){
        if(gold<0)
            throw new IllegalStateException("Hero doesn't have enough money!");
        gold-=aGold;
    }

    public void addGold(int aGold){
        gold+=aGold;
    }
    public void addCreature(EconomyCreature aCreature){
        if(heroArmy.size()>6)
            throw new IllegalStateException("Army can't include more type of creature!");
        heroArmy.add(aCreature);
    }
}
