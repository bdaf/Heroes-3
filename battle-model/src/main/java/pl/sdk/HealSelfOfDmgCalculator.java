package pl.sdk;

import java.util.Random;

public class HealSelfOfDmgCalculator extends DefaultDamageCalculator {
    private final double healFactor;

    HealSelfOfDmgCalculator(double aHealFactor) { this(aHealFactor,new Random()); }

    HealSelfOfDmgCalculator(double aHealFactor, Random aRand) {
        super(aRand);
        healFactor = aHealFactor;
    }

    @Override
    public int count(Creature aAttacker, Creature aDefender) {
        int result = super.count(aAttacker, aDefender);
        aAttacker.setCurrentHP((int) (aAttacker.getCurrentHp()+result*healFactor));
        if(aAttacker.getCurrentHp() > aAttacker.getMaxHp())
            aAttacker.setCurrentHP(aAttacker.getMaxHp());
        return result;
    }

}
