package pl.sdk;

public class ShootingCreature extends Creature {

    public ShootingCreature(CreatureStatistic aStats) {
        super(aStats);
    }

    @Override
    protected double getAttackRange() {
        return Double.MAX_VALUE;
    }

    @Override
    protected void counterAttack(Creature defender) {}

    public static class Builder extends Creature.Builder {
        @Override
        protected Creature createInstance(CreatureStatistic aStats) {
            return new ShootingCreature(aStats);
        }
    }

}
