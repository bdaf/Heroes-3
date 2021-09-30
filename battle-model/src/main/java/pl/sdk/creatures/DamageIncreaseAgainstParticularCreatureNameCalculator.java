package pl.sdk.creatures;


import java.util.Random;

public class DamageIncreaseAgainstParticularCreatureNameCalculator extends AbstractDamageCalculator {
    private final String nameToBeAgainst;
    private final double increaseFactor;

    DamageIncreaseAgainstParticularCreatureNameCalculator(String aNameToBeAgainst, double aIncreaseFactor) {
        this(aNameToBeAgainst, aIncreaseFactor, new Random());
    }

    DamageIncreaseAgainstParticularCreatureNameCalculator(String aNameToBeAgainst, double aIncreaseFactor, Random aRand) {
        super(aRand);
        nameToBeAgainst = aNameToBeAgainst;
        increaseFactor = aIncreaseFactor;
    }

    @Override
    protected boolean shouldChangeDamage(Creature aDefender) {
        return aDefender.getName().toLowerCase().contains(nameToBeAgainst.toLowerCase());
    }

    @Override
    protected double changeDamageAfter(double aDamageToChange) {
        aDamageToChange*= increaseFactor;
        return aDamageToChange;
    }
}
