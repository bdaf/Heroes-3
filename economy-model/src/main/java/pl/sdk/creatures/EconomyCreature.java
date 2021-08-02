package pl.sdk.creatures;

import java.util.Objects;

public class EconomyCreature {
    private final CreatureStatistic stats;

    private final int amount;
    private final int goldCost;
    EconomyCreature(CreatureStatistic aStats, int aAmount, int aGoldCost) {
        stats = aStats;
        amount = aAmount;
        goldCost = aGoldCost;
    }

    public CreatureStatistic getStats() {
        return stats;
    }

    public int getAmount() {
        return amount;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public String getName() {
        return stats.getTranslatedCreatureName();
    }

    public boolean isUpgraded() {
        return stats.isUpgraded();
    }

    public int getTier() { return stats.getTier(); }

    @Override
    public String toString() {
        return "EconomyCreature{" +
                "name=" + getName() +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        EconomyCreature that = (EconomyCreature) aO;
        return amount == that.amount &&
                goldCost == that.goldCost &&
                stats == that.stats;
    }
}
