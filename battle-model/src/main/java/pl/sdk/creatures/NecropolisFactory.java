package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class NecropolisFactory extends Factory {

    public NecropolisFactory() { fraction = Fraction.NECROPOLIS; }

    @Override
    public Creature create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.SKELETON_WARRIOR)
                            .build();
                case 2:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.ZOMBIE)
                            .build();
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WRAITH)
                            .build());
                case 4:
                    return new SelfHealingCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.VAMPIRE_LORD)
                            .build()), 0.5);
                case 5:
                    return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.POWER_LICH)
                            .build()), getSplashForLich());
                case 6:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.DREAD_KNIGHT)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.GHOST_DRAGON)
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
                            .build();
                case 2:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WALKING_DEAD)
                            .build();
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.WIGHT)
                            .build());
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.VAMPIRE)
                            .build());
                case 5:
                    return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.LICH)
                            .build()), getSplashForLich());
                case 6:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.BLACK_KNIGHT)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .amount(amount)
                            .statistic(CreatureStatistic.BONE_DRAGON)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
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
