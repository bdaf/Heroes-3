package pl.sdk.creatures;

import com.google.common.collect.Range;

public class CastleFactory extends Factory {

    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";
    public static final String HALBERDIER = "Halberdier";
    public static final String ROYAL_GRIFFIN = "RoyalGriffin";
    public static final String CRUSADER = "Crusader";
    public static final String ZEALOT = "Zealot";
    public static final String CHAMPION = "Champion";
    public static final String ARCHANGEL = "Archangel";
    public static final String PICKMAN = "Pickman";
    public static final String ARCHER = "Archer";
    public static final String GRIFFIN = "Griffin";
    public static final String SWORDSMAN = "Swordsman";
    public static final String MONK = "Monk";
    public static final String CAVALIER = "Cavalier";
    public static final String ANGEL = "Angel";
    public static final String MARKSMAN = "Marksman";

    @Override
    public Creature Create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .name(HALBERDIER)
                            .maxHp(10)
                            .attack(4)
                            .moveRange(4)
                            .damage(Range.closed(1, 3))
                            .armor(5)
                            .amount(amount)
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .name(MARKSMAN)
                            .maxHp(10)
                            .attack(6)
                            .moveRange(6)
                            .damage(Range.closed(2, 3))
                            .armor(3)
                            .amount(amount)
                            .attacksInTurn(2)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .name(ROYAL_GRIFFIN)
                            .maxHp(25)
                            .attack(9)
                            .moveRange(9)
                            .damage(Range.closed(3, 6))
                            .armor(9)
                            .amount(amount)
                            .build(), Integer.MAX_VALUE);
                case 4:
                    return new Creature.Builder()
                            .name(CRUSADER)
                            .maxHp(35)
                            .attack(12)
                            .moveRange(6)
                            .damage(Range.closed(7, 10))
                            .armor(12)
                            .amount(amount)
                            .build();
                case 5:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .name(ZEALOT)
                            .maxHp(30)
                            .attack(12)
                            .moveRange(7)
                            .damage(Range.closed(10, 12))
                            .armor(10)
                            .amount(amount)
                            .build());
                case 6:
                    return new Creature.Builder()
                            .name(CHAMPION)
                            .maxHp(100)
                            .attack(16)
                            .moveRange(9)
                            .damage(Range.closed(20, 25))
                            .damageCalculator(new CalculateDamageIncreaseInRandomChance(0.2, 2))
                            .armor(16)
                            .amount(amount)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .name(ARCHANGEL)
                            .maxHp(250)
                            .attack(30)
                            .moveRange(18)
                            .damage(Range.closed(50, 50))
                            .armor(20)
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new Creature.Builder()
                            .name(PICKMAN)
                            .maxHp(10)
                            .attack(4)
                            .moveRange(4)
                            .damage(Range.closed(1, 3))
                            .armor(6)
                            .amount(amount)
                            .build();
                case 2:
                    return new ShootingCreatureDecorator(new Creature.Builder()
                            .name(ARCHER)
                            .maxHp(10)
                            .attack(6)
                            .moveRange(4)
                            .damage(Range.closed(2, 3))
                            .armor(3)
                            .amount(amount)
                            .build());
                case 3:
                    return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder()
                            .name(GRIFFIN)
                            .maxHp(25)
                            .attack(8)
                            .moveRange(6)
                            .damage(Range.closed(3, 6))
                            .armor(8)
                            .amount(amount)
                            .build(), 2);
                case 4:
                    return new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                            .name(SWORDSMAN)
                            .maxHp(35)
                            .attack(10)
                            .moveRange(5)
                            .damage(Range.closed(6, 9))
                            .armor(12)
                            .amount(amount)
                            .build());
                case 5:
                    return new ShootingCreatureDecorator(new SelfHealingCreatureDecorator(new Creature.Builder()
                            .name(MONK)
                            .maxHp(30)
                            .attack(12)
                            .moveRange(5)
                            .damage(Range.closed(10, 12))
                            .armor(7)
                            .amount(amount)
                            .build(), -0.5));
                case 6:
                    return new Creature.Builder()
                            .name(CAVALIER)
                            .maxHp(100)
                            .attack(15)
                            .moveRange(7)
                            .damage(Range.closed(15, 25))
                            .armor(15)
                            .amount(amount)
                            .build();
                case 7:
                    return new Creature.Builder()
                            .name(ANGEL)
                            .maxHp(200)
                            .attack(20)
                            .moveRange(12)
                            .damage(Range.closed(50, 50))
                            .armor(20)
                            .amount(amount)
                            .build();
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
