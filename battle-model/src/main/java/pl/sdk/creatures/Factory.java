package pl.sdk.creatures;

public abstract class Factory {
    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";
    abstract public Creature Create(boolean aIsUpgraded, int aTier, int amount);

    public static Creature CreateShootingCreatureForTests(){
        return CreateShootingCreatureForTests(1);
    }
    public static Creature CreateShootingCreatureForTests(int aMoveRange){
        return CreateShootingCreatureForTests(aMoveRange,10);
    }

    public static Creature CreateShootingCreatureForTests(int aMoveRange, int aAttack){
        Creature c = new Creature.BuilderForTesting()
                .moveRange(aMoveRange)
                .shots(12)
                .attack(aAttack)
                .build();
        return new ShootingCreatureDecorator(c);
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
        return CreateDefaultCreatureForTests(aMoveRange,100);
    }
    public static Creature CreateDefaultCreatureForTests(int aMoveRange, int aMaxHp){
        return new Creature.BuilderForTesting().moveRange(aMoveRange).maxHp(aMaxHp).build();
    }

    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks) {
        return CreateCounterAttackSeveralTimesInTurnCreature(counterAttacks,1);
    }
    public static Creature CreateCounterAttackSeveralTimesInTurnCreature(int counterAttacks, int aMoveRange) {
        return new CounterAttackingSeveralTimesInTurnDecorator(new Creature.BuilderForTesting().moveRange(aMoveRange).build(),counterAttacks);
    }
}
