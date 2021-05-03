package pl.sdk.creatures;

import java.util.Random;

 abstract class AbstractDamageCalculator implements CalculateDamageStrategy {
    public static final double _28 = 27.9999999;
    Random rand;

    AbstractDamageCalculator() { this(new Random()); }
    AbstractDamageCalculator(Random aRand) { rand = aRand; }

    public int calculateDamage(Creature aAttacker, Creature aDefender) {
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
            if (defencePoints > _28) // min 30% of DMG
                defencePoints = _28; // it is written like this  because when I put this '28' it doesn't work
            damageToDeal = (randedDamage * (1 - (defencePoints * 0.025)));
        }
        damageToDeal = changeDamageAfter(damageToDeal*aAttacker.getAmount());
        int maxDamageToDeal = (aDefender.getAmount()-1)*aDefender.getMaxHp()+aDefender.getCurrentHp();
        if(damageToDeal>maxDamageToDeal)
            damageToDeal = maxDamageToDeal;

        return (int) damageToDeal;
    }

    protected double changeDamageAfter(double aDamageToChange) {
        return aDamageToChange;
    }

}
