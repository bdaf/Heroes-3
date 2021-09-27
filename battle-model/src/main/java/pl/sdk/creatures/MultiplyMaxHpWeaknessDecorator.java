package pl.sdk.creatures;

public class MultiplyMaxHpWeaknessDecorator extends Weakness{
    private final Weakness decorated;
    private final double FACTOR_MULTIPLYING_MAX_HP;

    MultiplyMaxHpWeaknessDecorator(Weakness aWeakness, double aFactorOfMultiplyingMaxHp) {
        decorated = aWeakness;
        FACTOR_MULTIPLYING_MAX_HP = aFactorOfMultiplyingMaxHp;
    }

    @Override
    void setWeak(Creature aCreature, Integer aDealtDmg) {
        setMaxHpToDecrease((int) (aCreature.getMaxHp()*FACTOR_MULTIPLYING_MAX_HP));
        int currentHp = aCreature.getCurrentHp() + aDealtDmg;
        currentHp = Integer.max(1,currentHp/2 - aDealtDmg);
        aCreature.setCurrentHP(currentHp);
        super.setWeak(aCreature, aDealtDmg);
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
    int getStartDuration() {
        return decorated.getStartDuration();
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
    Integer getMaxHpToDecrease() {
        return decorated.getMaxHpToDecrease();
    }

    @Override
    void setDamageToDecrease(int aMinDamageToDecrease, int aMaxDamageToDecrease) {
        decorated.setDamageToDecrease(aMinDamageToDecrease, aMaxDamageToDecrease);
    }

    @Override
    void setMaxHpToDecrease(int aMaxHpToDecrease) {
        decorated.setMaxHpToDecrease(aMaxHpToDecrease);
    }

    @Override
    public boolean equals(Object aO) {
        return decorated.equals(aO);
    }
}
