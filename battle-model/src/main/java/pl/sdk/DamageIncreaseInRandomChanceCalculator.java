package pl.sdk;

import java.util.Random;

public class DamageIncreaseInRandomChanceCalculator extends DefaultDamageCalculator {
    private final double ChanceToIncrease;
    private final double IncreaseFactor;

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor) { this(aChanceToIncrease,aIncreaseFactor,new Random()); }

    DamageIncreaseInRandomChanceCalculator(double aChanceToIncrease, double aIncreaseFactor, Random aRand) {
        super(aRand);
        ChanceToIncrease = aChanceToIncrease;
        IncreaseFactor = aIncreaseFactor;
    }

    @Override
    int count(Creature aAttacker, Creature aDefender) {
        double number = rand.nextDouble();
        int multiply = 1;
        if(number<=0.2)
            multiply = 2;
        return super.count(aAttacker, aDefender)*multiply;
    }
}
