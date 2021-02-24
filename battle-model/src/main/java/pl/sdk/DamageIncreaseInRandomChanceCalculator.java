package pl.sdk;

import java.util.Random;

public class DamageIncreaseInRandomChanceCalculator extends AbstractDamageCalculator {
    private final double ChanceToIncrease;
    private final double IncreaseFactor;

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor) {
        this(aChanceToIncrease, aIncreaseFactor, new Random());
    }

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor, Random aRand) {
        super(aRand);
        ChanceToIncrease = aChanceToIncrease;
        IncreaseFactor = aIncreaseFactor;
    }

    @Override
    protected double changeDamageAfter(double aDamageToChange) {
        if(rand.nextDouble()<=ChanceToIncrease)
            aDamageToChange*=IncreaseFactor;
        return aDamageToChange;
    }
}
