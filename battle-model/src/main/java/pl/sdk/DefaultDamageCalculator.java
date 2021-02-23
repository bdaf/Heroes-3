package pl.sdk;

import java.util.Random;

public class DefaultDamageCalculator extends DamageCalculator {
    public static final double _30 = 27.9999999;
    Random rand;

    DefaultDamageCalculator() { this(new Random()); }
    DefaultDamageCalculator(Random aRand) { rand = aRand; }

    int count(Creature aAttacker, Creature aDefender) {
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
            if (defencePoints > _30) // min 30% DMG
                defencePoints = _30; // tak dziwnie napisane bo jak daje równo 28 to źle liczy
            damageToDeal = (randedDamage * (1 - (defencePoints * 0.025)));

        }
        return (int) damageToDeal*aAttacker.getAmount();
    }
}
