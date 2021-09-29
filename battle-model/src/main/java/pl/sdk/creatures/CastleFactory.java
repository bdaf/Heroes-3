package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class CastleFactory extends Factory {

    public CastleFactory() { fraction = Fraction.CASTLE; }

    @Override
    public Creature create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.HALBERDIER)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(2)
                            .amount(amount)
                            .statistic(CreatureStatistic.MARKSMAN)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ROYAL_GRIFFIN)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build(), Integer.MAX_VALUE);
                case 4:
                    return new Creature.Builder()
                            .attacksInTurn(2)
                            .amount(amount)
                            .statistic(CreatureStatistic.CRUSADER)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 5:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZEALOT)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 6:
                    return new TravelToIncreaseDamageCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.CHAMPION)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(),0.05);
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHANGEL)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
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
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHER)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.GRIFFIN)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(), 2);
                case 4:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.SWORDSMAN)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build();
                case 5:
                    return new SelfHealingCreatureDecorator(new ShootingCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.MONK)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build()),-0.5);
                case 6:
                    return new TravelToIncreaseDamageCreatureDecorator(new Creature.Builder()
                            .statistic(CreatureStatistic.CAVALIER)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(),0.05);
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ANGEL)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
