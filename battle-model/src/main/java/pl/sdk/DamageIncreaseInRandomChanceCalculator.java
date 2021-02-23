package pl.sdk;

import java.util.Random;

public class DamageIncreaseInRandomChanceCalculator extends DefaultDamageCalculator {
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
    public int count(Creature aAttacker, Creature aDefender) {
        int multiply = 1;
        if (rand.nextDouble() <= ChanceToIncrease)
            multiply *= IncreaseFactor;
        return super.count(aAttacker, aDefender) * multiply;
    }
}
