package pl.sdk.creatures;

import java.util.Random;

public abstract class AbstractDamageCalculator implements CalculateDamageStrategy {
    public static final double _28 = 27.9999999;
    Random rand;

    AbstractDamageCalculator() { this(new Random()); }
    AbstractDamageCalculator(Random aRand) { rand = aRand; }

    public int count(Creature aAttacker, Creature aDefender) {
        int upper = aAttacker.getStats().getDamage().upperEndpoint();
        int lower = aAttacker.getStats().getDamage().lowerEndpoint();
        int randedDamage = rand.nextInt(upper-lower +1) + lower;
        double damageToDeal;
        if (aAttacker.getStats().getAttack() >= aDefender.getStats().getArmor()) {
            double attackPoints = aAttacker.getStats().getAttack() - aDefender.getStats().getArmor();
            if (attackPoints > 60) // max 400% DMG
                attackPoints = 60;
            damageToDeal = (randedDamage * (1 + attackPoints * 0.05));
        } else {
            double defencePoints = aDefender.getStats().getArmor() - aAttacker.getStats().getAttack();
            if (defencePoints > _28) // min 30% DMG
                defencePoints = _28; // tak dziwnie napisane bo jak daje równo 28 to źle liczy
            damageToDeal = (randedDamage * (1 - (defencePoints * 0.025)));
        }
        damageToDeal = changeDamageAfter(damageToDeal*aAttacker.getAmount());
        return (int) damageToDeal;
    }

    protected double changeDamageAfter(double aDamageToChange) {
        return aDamageToChange;
    }

}
