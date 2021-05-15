package pl.sdk.creatures;

public class EconomyCreature {
    private final CreatureStatisticForTests stats;
    private final int amount;
    private final int goldCost;

    EconomyCreature(CreatureStatisticForTests aStats, int aAmount, int aGoldCost) {
        stats = aStats;
        amount = aAmount;
        goldCost = aGoldCost;
    }
}
