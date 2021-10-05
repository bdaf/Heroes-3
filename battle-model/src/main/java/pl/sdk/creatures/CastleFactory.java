package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class CastleFactory extends Factory {

    public CastleFactory() {
        fraction = Fraction.CASTLE;
    }

    @Override
    public Creature create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.HALBERDIER)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 2:
                    return new ShootingCreatureDecoratorW(new Creature.Builder()
                            .attacksInTurn(2)
                            .amount(amount)
                            .statistic(CreatureStatistic.MARKSMAN)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnCreatureDecoratorW(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ROYAL_GRIFFIN)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build(), Integer.MAX_VALUE);
                case 4:
                    return new Creature.Builder()
                            .attacksInTurn(2)
                            .amount(amount)
                            .statistic(CreatureStatistic.CRUSADER)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 5:
                    return new ShootingCreatureDecoratorW(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZEALOT)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 6:
                    return new TravelToIncreaseDamageCreatureDecoratorW(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.CHAMPION)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(), 0.05);
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHANGEL)
                            .damageCalculator(new DamageIncreaseAgainstParticularCreatureNameCalcDecorator(
                                    new DefaultDamageCalculator(), "devil", 1.5))
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
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build();
                case 2:
                    return new ShootingCreatureDecoratorW(new Creature.Builder()
                            .statistic(CreatureStatistic.ARCHER)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnCreatureDecoratorW(new Creature.Builder()
                            .statistic(CreatureStatistic.GRIFFIN)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(), 2);
                case 4:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.SWORDSMAN)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build();
                case 5:
                    return new SelfHealingCreatureDecoratorW(new ShootingCreatureDecoratorW(new Creature.Builder()
                            .statistic(CreatureStatistic.MONK)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build()), -0.5);
                case 6:
                    return new TravelToIncreaseDamageCreatureDecoratorW(new Creature.Builder()
                            .statistic(CreatureStatistic.CAVALIER)
                            .damageCalculator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .amount(amount)
                            .build(), 0.05);
                case 7:
                    return new Creature.Builder()
                            .statistic(CreatureStatistic.ANGEL)
                            .damageCalculator(new DamageIncreaseAgainstParticularCreatureNameCalcDecorator(new DamageIncreaseInRandomChanceCalculator(
                                    new DefaultDamageCalculator(), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK), "devil", 1.5))
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
