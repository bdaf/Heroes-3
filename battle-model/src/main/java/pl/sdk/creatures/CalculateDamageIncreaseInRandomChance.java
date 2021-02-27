package pl.sdk.creatures;

import java.util.Random;

public class CalculateDamageIncreaseInRandomChance extends AbstractDamageCalculator {
    private final double ChanceToIncrease;
    private final double IncreaseFactor;

    CalculateDamageIncreaseInRandomChance(double aChanceToIncrease, double aIncreaseFactor) {
        this(aChanceToIncrease, aIncreaseFactor, new Random());
    }

    CalculateDamageIncreaseInRandomChance(double aChanceToIncrease, double aIncreaseFactor, Random aRand) {
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
