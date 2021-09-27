package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class NecropolisFactory extends Factory {

    public NecropolisFactory() {
        fraction = Fraction.NECROPOLIS;
    }

    @Override
    public Creature create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.SKELETON_WARRIOR)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 2:
                    return new InfectsWithWeaknessCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZOMBIE)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build(), getDisease());
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WRAITH)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new SelfHealingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.VAMPIRE_LORD)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build(), 1));
                case 5:
                    return new SplashDamageCreatureDecorator(new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.POWER_LICH)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build()), getSplashForLich());
                case 6:
                    return new InfectsWithWeaknessCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.DREAD_KNIGHT)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(0.2, 2))
                            .build(), getCurse());
                case 7:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.GHOST_DRAGON)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.SKELETON)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 2:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WALKING_DEAD)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WIGHT)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.VAMPIRE)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build());
                case 5:
                    return new SplashDamageCreatureDecorator(new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.LICH)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build()), getSplashForLich());
                case 6:
                    return new InfectsWithWeaknessCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.BLACK_KNIGHT)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build(), getCurse());
                case 7:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.BONE_DRAGON)
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK))
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }

    private Weakness getDisease() {
        return new Weakness.Builder()
        .attackToDecrease(2)
        .defenseToDecrease(2)
        .percentage(0.8)
        .duration(3)
        .name("Disease").build();
    }

    private ReduceDamageToLessThanMinimumWeaknessDecorator getCurse() {
        return new ReduceDamageToLessThanMinimumWeaknessDecorator(
                new Weakness.Builder()
                        .percentage(0.2)
                        .duration(Integer.MAX_VALUE)
                        .name("Curse")
                        .build(),1);
    }

    boolean[][] getSplashForLich() {
        boolean[][] splashDamageTable = new boolean[3][3];
        splashDamageTable[1][1] = true;
        splashDamageTable[0][1] = true;
        splashDamageTable[0][2] = true;
        splashDamageTable[1][2] = true;
        splashDamageTable[2][2] = true;
        splashDamageTable[2][1] = true;
        splashDamageTable[2][0] = true;
        splashDamageTable[1][0] = true;
        splashDamageTable[0][0] = true;
        return splashDamageTable;
    }
}
