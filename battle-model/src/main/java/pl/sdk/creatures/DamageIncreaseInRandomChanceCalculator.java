package pl.sdk.creatures;


public class DamageIncreaseInRandomChanceCalculator extends AbstractDamageCalculator {
    private final double chanceToIncrease;
    private final double increaseFactor;
    private AbstractDamageCalculator decorated;


    DamageIncreaseInRandomChanceCalculator(AbstractDamageCalculator aCalculator, double aChanceToIncrease, double aIncreaseFactor) {
        decorated = aCalculator;
        chanceToIncrease = aChanceToIncrease;
        increaseFactor = aIncreaseFactor;
    }

    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender) {
        int aDamageToChange = decorated.calculateDamage(aAttacker, aDefender);
        if (decorated.getRand().nextDouble() <= chanceToIncrease){
            aDamageToChange *= increaseFactor;
        }
        return aDamageToChange;
    }
}
