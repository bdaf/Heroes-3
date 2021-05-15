package pl.sdk.creatures;

import com.google.common.collect.Range;

public class NecropolisFactory extends Factory{
    final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    @Override
    public Creature Create(boolean aIsUpgraded, int aTier, int amount){
        if(aIsUpgraded){
            switch(aTier){
                case 1: return new Creature.BuilderForTesting()
                        .name(CreatureStatisticForTests.SKELETON_WARRIOR.name())
                        .maxHp(6)
                        .attack(6)
                        .moveRange(5)
                        .damage(Range.closed(1,3))
                        .armor(6)
                        .amount(amount)
                        .build();
                case 2: return new Creature.BuilderForTesting()
                        .name(ZOMBIE)
                        .maxHp(20)
                        .attack(5)
                        .moveRange(4)
                        .damage(Range.closed(2,3))
                        .armor(5)
                        .amount(amount)
                        .build();
                case 3: return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.BuilderForTesting()
                        .name(WRAITH)
                        .maxHp(18)
                        .attack(7)
                        .moveRange(7)
                        .damage(Range.closed(3,5))
                        .armor(7)
                        .amount(amount)
                        .build());
                case 4:
                    return new SelfHealingCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                        .name(VAMPIRE_LORD)
                        .maxHp(40)
                        .attack(10)
                        .moveRange(9)
                        .damage(Range.closed(5,8))
                        .armor(10)
                        .amount(amount)
                        .build()),0.5);
                case 5:
                    return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.BuilderForTesting()
                        .name(POWER_LICH)
                        .maxHp(40)
                        .attack(13)
                        .moveRange(7)
                        .damage(Range.closed(11,15))
                        .armor(10)
                        .amount(amount)
                        .build()),getSplashForLich());
                case 6: return new Creature.BuilderForTesting()
                        .name(DREAD_KNIGHT)
                        .maxHp(120)
                        .attack(16)
                        .moveRange(7)
                        .damage(Range.closed(15,30))
                        .damageCalculator(new CalculateDamageIncreaseInRandomChance(0.2,2))
                        .armor(16)
                        .amount(amount)
                        .build();
                case 7: return new Creature.BuilderForTesting()
                        .name(GHOST_DRAGON)
                        .maxHp(200)
                        .attack(19)
                        .moveRange(14)
                        .damage(Range.closed(25,50))
                        .armor(17)
                        .amount(amount)
                        .build();
                default: throw new IllegalArgumentException(ERROR_MSG);
            }
        }
        else{
            switch(aTier){
                case 1: return new Creature.BuilderForTesting()
                        .name(SKELETON)
                        .maxHp(6)
                        .attack(5)
                        .moveRange(4)
                        .damage(Range.closed(1,3))
                        .armor(6)
                        .amount(amount)
                        .build();
                case 2: return new Creature.BuilderForTesting()
                        .name(WALKING_DEAD)
                        .maxHp(15)
                        .attack(5)
                        .moveRange(3)
                        .damage(Range.closed(2,3))
                        .armor(5)
                        .amount(amount)
                        .build();
                case 3: return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.BuilderForTesting()
                        .name(WIGHT)
                        .maxHp(18)
                        .attack(7)
                        .moveRange(5)
                        .damage(Range.closed(3,5))
                        .armor(7)
                        .amount(amount)
                        .build());
                case 4: return new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                        .name(VAMPIRE)
                        .maxHp(30)
                        .attack(10)
                        .moveRange(6)
                        .damage(Range.closed(5,8))
                        .armor(9)
                        .amount(amount)
                        .build());
                case 5: return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.BuilderForTesting()
                        .name(LICH)
                        .maxHp(30)
                        .attack(13)
                        .moveRange(6)
                        .damage(Range.closed(11,13))
                        .armor(10)
                        .amount(amount)
                        .build()),getSplashForLich());
                case 6: return new Creature.BuilderForTesting()
                        .name(BLACK_KNIGHT)
                        .maxHp(120)
                        .attack(16)
                        .moveRange(7)
                        .damage(Range.closed(15,30))
                        .armor(16)
                        .amount(amount)
                        .build();
                case 7: return new Creature.BuilderForTesting()
                        .name(BONE_DRAGON)
                        .maxHp(150)
                        .attack(17)
                        .moveRange(9)
                        .damage(Range.closed(25,50))
                        .armor(15)
                        .amount(amount)
                        .build();
                default: throw new IllegalArgumentException(ERROR_MSG);
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
