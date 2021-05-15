package pl.sdk.creatures;

import com.google.common.collect.Range;

public class NecropolisFactory extends Factory {
    final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    @Override
    public Creature Create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.SKELETON_WARRIOR)
                            .build();
                case 2:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.ZOMBIE)
                            .build();
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.WRAITH)
                            .build());
                case 4:
                    return new SelfHealingCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.VAMPIRE_LORD)
                            .build()), 0.5);
                case 5:
                    return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.POWER_LICH)
                            .build()), getSplashForLich());
                case 6:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.DREAD_KNIGHT)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.GHOST_DRAGON)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.SKELETON)
                            .build();
                case 2:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.WALKING_DEAD)
                            .build();
                case 3:
                    return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.WIGHT)
                            .build());
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.VAMPIRE)
                            .build());
                case 5:
                    return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.LICH)
                            .build()), getSplashForLich());
                case 6:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.BLACK_KNIGHT)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .attacksInTurn(1)
                            .amount(amount)
                            .stats(CreatureStatisticForEconomy.BONE_DRAGON)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }

    private boolean[][] getSplashForLich() {
        boolean[][] splashDamageTable = new boolean[3][3];
        splashDamageTable[1][1] = true;
        splashDamageTable[0][1] = true;
        splashDamageTable[1][0] = true;
        splashDamageTable[2][1] = true;
        splashDamageTable[1][2] = true;
        return splashDamageTable;
    }
}
