package pl.sdk;

public class CreatureWithSelfHealing extends Creature {
    protected double selfHealingPercentage;

    public CreatureWithSelfHealing(CreatureStatistic aStats) {
        super(aStats);
    }
    @Override
    protected void performAfterAttack(int aDamageToChange) {
        applyDamage((int)(-aDamageToChange * selfHealingPercentage));
    }

    public static class Builder extends Creature.Builder{
        private double selfHealingPercentage;
        public CreatureWithSelfHealing.Builder selfHealingPercentage(double aSelfHealingPercentage){
            selfHealingPercentage = aSelfHealingPercentage;
            return this;
        }
        @Override
        protected Creature createInstance(CreatureStatistic aStats) {
            CreatureWithSelfHealing ret = new CreatureWithSelfHealing(aStats);
            ret.selfHealingPercentage = selfHealingPercentage;
            return ret;
        }
    }
}
