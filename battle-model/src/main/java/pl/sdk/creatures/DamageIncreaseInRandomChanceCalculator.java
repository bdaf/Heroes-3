package pl.sdk.creatures;

import java.util.Random;

public class DamageIncreaseInRandomChanceCalculator extends AbstractDamageCalculator {
    private final double chanceToIncrease;
    private final double increaseFactor;

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor) {
        this(aChanceToIncrease, aIncreaseFactor, new Random());
    }

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor, Random aRand) {
        super(aRand);
        chanceToIncrease = aChanceToIncrease;
        increaseFactor = aIncreaseFactor;
    }

    @Override
    protected boolean shouldChangeDamage(Creature aDefender) {
        return rand.nextDouble()<= chanceToIncrease;
    }

    @Override
    protected double changeDamageAfter(double aDamageToChange) {
        aDamageToChange*= increaseFactor;
        return aDamageToChange;
    }
}
