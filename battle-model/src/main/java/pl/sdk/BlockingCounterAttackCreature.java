package pl.sdk;

public class BlockingCounterAttackCreature extends Creature {

    public BlockingCounterAttackCreature(CreatureStatistic aStats) {
        super(aStats);
    }
    public BlockingCounterAttackCreature() {
        super();
    }

    @Override
    protected void counterAttack(Creature defender) { }

    public static class Builder extends Creature.Builder{
        @Override
        protected Creature createInstance(CreatureStatistic aStats) {
            return new BlockingCounterAttackCreature(aStats);
        }
    }
}
