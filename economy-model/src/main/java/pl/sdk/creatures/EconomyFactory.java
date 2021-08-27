package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public abstract class EconomyFactory {
    public Fraction fraction;

    public Fraction getFraction() {
        return fraction;
    }

    abstract public EconomyCreature Create(boolean aIsUpgraded, int aTier, int amount);
}
