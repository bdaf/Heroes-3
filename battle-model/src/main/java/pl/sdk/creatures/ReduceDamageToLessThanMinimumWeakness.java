package pl.sdk.creatures;

public class ReduceDamageToLessThanMinimumWeakness extends Weakness {
    ReduceDamageToLessThanMinimumWeakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration, String aName, Integer aMinDamage, Integer aMaxDamage) {
        super(aAttackToDecrease, aDefenseToDecrease, aPercentage, aDuration, aName, aMinDamage, aMaxDamage);
    }

    @Override
    void setWeak(Creature aCreature) {
        minDamageToDecrease = 1;
        maxDamageToDecrease = 1 + aCreature.getDamage().upperEndpoint() - aCreature.getDamage().lowerEndpoint() ;
    }
}
