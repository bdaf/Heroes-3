package pl.sdk.creatures;

public abstract class EconomyFactory {
    abstract public EconomyCreature Create(boolean aIsUpgraded, int aTier, int amount);
}
