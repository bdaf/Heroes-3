package pl.sdk.creatures;

public abstract class Factory {
    abstract public Creature Create(boolean aIsUpgraded, int aTier, int amount);

    public static Creature CreateShootingCreatureForTests(){
        return CreateShootingCreatureForTests(1);
    }
    public static Creature CreateShootingCreatureForTests(int aMoveRange){
        return new ShootingCreatureDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build());
    }

    public static Creature CreateBlockingCreatureForTests(){
        return CreateBlockingCreatureForTests(1);
    }
    public static Creature CreateBlockingCreatureForTests(int aMoveRange){
        return new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build());
    }

    public static Creature CreateDefaultCreatureForTests(){
        return CreateDefaultCreatureForTests(1);
    }
    public static Creature CreateDefaultCreatureForTests(int aMoveRange){
        return new Creature.BuilderForTesting().moveRange(aMoveRange).build();
    }

    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks) {
        return CreateCounterAttackSeveralTimesInTurnCreature(counterAttacks,1);
    }
    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks, int aMoveRange) {
        return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build(),counterAttacks);
    }
}
