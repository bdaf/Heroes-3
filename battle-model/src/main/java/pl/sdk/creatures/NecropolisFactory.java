package pl.sdk.creatures;

import com.google.common.collect.Range;

public class NecropolisFactory {
    public static final String SKELETON_WARRIOR = "SkeletonWarrior";
    public static final String ZOMBIE = "Zombie";
    public static final String WRAITH = "Wraith";
    public static final String VAMPIRE_LORD = "VampireLord";
    public static final String POWER_LICH = "PowerLich";
    public static final String DREAD_KNIGHT = "DreadKnight";
    public static final String GHOST_DRAGON = "GhostDragon";
    public static final String SKELETON = "Skeleton";
    public static final String WALKING_DEAD = "WalkingDead";
    public static final String WIGHT = "Wight";
    public static final String VAMPIRE = "Vampire";
    public static final String LICH = "Lich";
    public static final String BLACK_KNIGHT = "BlackKnight";
    public static final String BONE_DRAGON = "BoneDragon";
    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    public static Creature CreateCreatureDefaultForTests(){
        return new Creature.Builder().build();
    }
    public static Creature CreateCreatureDefaultForTests(int aMoveRange){
        return new Creature.Builder()
                .moveRange(aMoveRange)
                .build();
    }

    public static Creature CreateShootingCreatureForTests(){
        return new ShootingCreatureDecorator(new Creature.Builder().build());
    }

    public static Creature CreateBlockingCreatureForTests(){
        return new BlockingCounterAttackCreatureDecorator(new Creature.Builder().build());
    }

    public Creature Create(boolean aIsUpgraded, int aTier){
        if(aIsUpgraded){
            switch(aTier){
                case 1: return new Creature.Builder()
                        .name(SKELETON_WARRIOR)
                        .maxHp(6)
                        .attack(6)
                        .moveRange(5)
                        .damage(Range.closed(1,3))
                        .armor(6)
                        .amount(20)
                        .build();
                case 2: return new Creature.Builder()
                        .name(ZOMBIE)
                        .maxHp(20)
                        .attack(5)
                        .moveRange(4)
                        .damage(Range.closed(2,3))
                        .armor(5)
                        .amount(20)
                        .build();
                case 3: return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                        .name(WRAITH)
                        .maxHp(18)
                        .attack(7)
                        .moveRange(7)
                        .damage(Range.closed(3,5))
                        .armor(7)
                        .amount(15)
                        .build());
                case 4:
                    return new SelfHealingCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                        .name(VAMPIRE_LORD)
                        .maxHp(40)
                        .attack(10)
                        .moveRange(9)
                        .damage(Range.closed(5,8))
                        .armor(10)
                        .amount(15)
                        .build()),0.5);
                case 5: return new SplashDamageCreature(new ShootingCreatureDecorator(new Creature.Builder()
                        .name(POWER_LICH)
                        .maxHp(40)
                        .attack(13)
                        .moveRange(7)
                        .damage(Range.closed(11,15))
                        .armor(10)
                        .amount(15)
                        .build()));
                case 6: return new Creature.Builder()
                        .name(DREAD_KNIGHT)
                        .maxHp(120)
                        .attack(16)
                        .moveRange(7)
                        .damage(Range.closed(15,30))
                        .damageCalculator(new CalculateDamageIncreaseInRandomChance(0.2,2))
                        .armor(16)
                        .amount(10)
                        .build();
                case 7: return new Creature.Builder()
                        .name(GHOST_DRAGON)
                        .maxHp(200)
                        .attack(19)
                        .moveRange(14)
                        .damage(Range.closed(25,50))
                        .armor(17)
                        .amount(10)
                        .build();
                default: throw new IllegalArgumentException(ERROR_MSG);
            }
        }
        else{
            switch(aTier){
                case 1: return new Creature.Builder()
                        .name(SKELETON)
                        .maxHp(6)
                        .attack(5)
                        .moveRange(4)
                        .damage(Range.closed(1,3))
                        .armor(6)
                        .amount(50)
                        .build();
                case 2: return new Creature.Builder()
                        .name(WALKING_DEAD)
                        .maxHp(15)
                        .attack(5)
                        .moveRange(3)
                        .damage(Range.closed(2,3))
                        .armor(5)
                        .amount(40)
                        .build();
                case 3: return new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                        .name(WIGHT)
                        .maxHp(18)
                        .attack(7)
                        .moveRange(5)
                        .damage(Range.closed(3,5))
                        .armor(7)
                        .amount(20)
                        .build());
                case 4: return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                        .name(VAMPIRE)
                        .maxHp(30)
                        .attack(10)
                        .moveRange(6)
                        .damage(Range.closed(5,8))
                        .armor(9)
                        .amount(20)
                        .build());
                case 5: return new ShootingCreatureDecorator(new Creature.Builder()
                        .name(LICH)
                        .maxHp(30)
                        .attack(13)
                        .moveRange(6)
                        .damage(Range.closed(11,13))
                        .armor(10)
                        .amount(20)
                        .build());
                case 6: return new Creature.Builder()
                        .name(BLACK_KNIGHT)
                        .maxHp(120)
                        .attack(16)
                        .moveRange(7)
                        .damage(Range.closed(15,30))
                        .armor(16)
                        .amount(12)
                        .build();
                case 7: return new Creature.Builder()
                        .name(BONE_DRAGON)
                        .maxHp(150)
                        .attack(17)
                        .moveRange(9)
                        .damage(Range.closed(25,50))
                        .armor(15)
                        .amount(12)
                        .build();
                default: throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
