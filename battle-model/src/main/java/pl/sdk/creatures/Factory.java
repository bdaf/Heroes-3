package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public abstract class Factory {

    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    Fraction fraction;

    Fraction getFraction() {
        return fraction;
    }

    abstract public Creature create(boolean aIsUpgraded, int aTier, int amount);

    public static Creature createShootingCreatureForTests() {
        return createShootingCreatureForTests(1);
    }

    public static Creature createShootingCreatureForTests(int aMoveRange) {
        return createShootingCreatureForTests(aMoveRange, 10);
    }

    public static Creature createShootingCreatureForTests(int aMoveRange, int aAttack) {
        Creature c = new Creature.BuilderForTesting()
                .moveRange(aMoveRange)
                .shots(12)
                .attack(aAttack)
                .build();
        return new ShootingCreatureDecorator(c);
    }

    public static Creature createBlockingCreatureForTests() {
        return createBlockingCreatureForTests(1);
    }

    public static Creature createBlockingCreatureForTests(int aMoveRange) {
        return new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build());
    }

    public static Creature createDefaultCreatureForTests() {
        return createDefaultCreatureForTests(1);
    }

    public static Creature createDefaultCreatureForTests(int aMoveRange) {
        return createDefaultCreatureForTests(aMoveRange, 100);
    }

    public static Creature createDefaultCreatureForTests(int aMoveRange, int aMaxHp) {
        return new Creature.BuilderForTesting().moveRange(aMoveRange).maxHp(aMaxHp).build();
    }

    public static Creature createCounterAttackSeveralTimesInTurnCreature(int counterAttacks) {
        return createCounterAttackSeveralTimesInTurnCreature(counterAttacks, 1);
    }

    public static Creature createCounterAttackSeveralTimesInTurnCreature(int counterAttacks, int aMoveRange) {
        return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build(), counterAttacks);
    }
}
