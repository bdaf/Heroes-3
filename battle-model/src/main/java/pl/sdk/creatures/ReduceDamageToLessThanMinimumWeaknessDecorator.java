package pl.sdk.creatures;

public class ReduceDamageToLessThanMinimumWeaknessDecorator extends Weakness {
    private final Weakness decorated;

    ReduceDamageToLessThanMinimumWeaknessDecorator(Weakness aDecorated) {
        decorated = aDecorated;
    }

    @Override
    void setWeak(Creature aCreature) {
        decorated.setDamageToDecrease(1,1 + aCreature.getDamage().upperEndpoint() - aCreature.getDamage().lowerEndpoint());
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
