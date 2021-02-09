package pl.sdk;

public class NewDamageCalculator extends DamageCalculator {
    int count(Creature aAttacker, Creature aDefender) {
        double damageToDeal;
        if (aAttacker.getStats().getAttack() >= aDefender.getStats().getArmor()) {
            double attackPoints = (aAttacker.getStats().getAttack() - aDefender.getStats().getArmor()) * 0.5;
            if (attackPoints > 60)
                attackPoints = 60;
            damageToDeal = (aAttacker.getStats().getDamage() * (attackPoints + 1));
        } else {
            double defencePoints = (aDefender.getStats().getArmor() - aAttacker.getStats().getAttack()) * 0.025;
            if (defencePoints < 12)
                defencePoints = 12;
            damageToDeal = (aAttacker.getStats().getDamage() * (1 - defencePoints));
        }
        return (int) damageToDeal;
    }
}
