package pl.sdk.creatures;

import com.google.common.collect.Range;

public class CastleFactory extends Factory {

    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    @Override
    public Creature Create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.HALBERDIER)
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(2)
                            .amount(amount)
                            .statistic(CreatureStatistic.MARKSMAN)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ROYAL_GRIFFIN)
                            .build(), Integer.MAX_VALUE);
                case 4:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.CRUSADER)
                            .build();
                case 5:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZEALOT)
                            .build());
                case 6:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZEALOT)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(0.2, 2))
                            .amount(amount)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHANGEL)
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.PICKMAN)
                            .amount(amount)
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHER)
                            .amount(amount)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.GRIFFIN)
                            .amount(amount)
                            .build(), 2);
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.SWORDSMAN)
                            .amount(amount)
                            .build());
                case 5:
                    return new ShootingCreatureDecorator(new SelfHealingCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.MONK)
                            .amount(amount)
                            .build(), -0.5));
                case 6:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.CAVALIER)
                            .amount(amount)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ANGEL)
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
