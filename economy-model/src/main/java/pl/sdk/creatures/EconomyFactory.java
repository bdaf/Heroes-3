package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public abstract class EconomyFactory {

    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    public Fraction fraction;

    public Fraction getFraction() {
        return fraction;
    }

    abstract public EconomyCreature Create(boolean aIsUpgraded, int aTier, int amount);
}
