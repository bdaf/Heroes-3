package pl.sdk.creatures;

public class ReduceDamageToLessThanMinimumWeaknessDecorator extends Weakness {
    public final int FACTOR_UNDER_MINIMUM;
    private final Weakness decorated;

    public ReduceDamageToLessThanMinimumWeaknessDecorator(Weakness aDecorated, int aFactorUnderMinimum) {
        decorated = aDecorated;
        FACTOR_UNDER_MINIMUM = aFactorUnderMinimum;
    }


    @Override
    int getStartDuration() {
        return decorated.getStartDuration();
    }

    @Override
    void setWeak(Creature aCreature, Integer aDealtDmg) {
        setDamageToDecrease(FACTOR_UNDER_MINIMUM, FACTOR_UNDER_MINIMUM + aCreature.getDamage().upperEndpoint() - aCreature.getDamage().lowerEndpoint());
        super.setWeak(aCreature, aDealtDmg);
    }

    @Override
    Integer getMaxHpToDecrease() {
        return decorated.getMaxHpToDecrease();
    }

    @Override
    void setMaxHpToDecrease(int aMaxHpToDecrease) {
        decorated.setMaxHpToDecrease(aMaxHpToDecrease);
    }

    @Override
    protected void setDamageToDecrease(int aMinDamageToDecrease, int aMaxDamageToDecrease) {
        decorated.setDamageToDecrease(aMinDamageToDecrease, aMaxDamageToDecrease);
    }

    @Override
    int getAttackToDecrease() {
        return decorated.getAttackToDecrease();
    }

    @Override
    int getDefenseToDecrease() {
        return decorated.getDefenseToDecrease();
    }

    @Override
    public int getDuration() {
        return decorated.getDuration();
    }

    @Override
    double getPercentage() {
        return decorated.getPercentage();
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    void restartDuration() {
        decorated.restartDuration();
    }

    @Override
    Integer getMinDamageToDecrease() {
        return decorated.getMinDamageToDecrease();
    }

    @Override
    Integer getMaxDamageToDecrease() {
        return decorated.getMaxDamageToDecrease();
    }

    @Override
    public boolean equals(Object aO) {
        return decorated.equals(aO);
    }
}
