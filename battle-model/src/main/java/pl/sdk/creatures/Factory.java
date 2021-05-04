package pl.sdk.creatures;

public abstract class Factory {
    abstract public Creature Create(boolean aIsUpgraded, int aTier);

    public static Creature CreateShootingCreatureForTests(){
        return CreateShootingCreatureForTests(1);
    }
    public static Creature CreateShootingCreatureForTests(int aMoveRange){
        return new ShootingCreatureDecorator(new Creature.Builder().moveRange(aMoveRange).build());
    }

    public static Creature CreateBlockingCreatureForTests(){
        return CreateBlockingCreatureForTests(1);
    }
    public static Creature CreateBlockingCreatureForTests(int aMoveRange){
        return new BlockingCounterAttackCreatureDecorator(new Creature.Builder().moveRange(aMoveRange).build());
    }

    public static Creature CreateDefaultCreatureForTests(){
        return CreateDefaultCreatureForTests(1);
    }
    public static Creature CreateDefaultCreatureForTests(int aMoveRange){
        return new Creature.Builder().moveRange(aMoveRange).build();
    }

    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks) {
        return CreateCounterAttackSeveralTimesInTurnCreature(counterAttacks,1);
    }
    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks, int aMoveRange) {
        return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.Builder().moveRange(aMoveRange).build(),counterAttacks);
    }
}
