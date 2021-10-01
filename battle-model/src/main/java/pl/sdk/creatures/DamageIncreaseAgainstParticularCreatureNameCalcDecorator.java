package pl.sdk.creatures;


public class DamageIncreaseAgainstParticularCreatureNameCalcDecorator extends AbstractDamageCalculator {
    private final AbstractDamageCalculator decorated;
    private final String nameToBeAgainst;
    private final double increaseFactor;

    DamageIncreaseAgainstParticularCreatureNameCalcDecorator(AbstractDamageCalculator aCalculator, String aNameToBeAgainst, double aIncreaseFactor) {
        decorated = aCalculator;
        nameToBeAgainst = aNameToBeAgainst;
        increaseFactor = aIncreaseFactor;
    }

    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender) {
        int aDamageToChange = decorated.calculateDamage(aAttacker, aDefender);
        if(aDefender.getName().toLowerCase().contains(nameToBeAgainst.toLowerCase())){
            aDamageToChange*= increaseFactor;
        }
        return aDamageToChange;
    }

}
