package pl.sdk;

public class NewDamageCalculator extends DamageCalculator {
    int count(Creature aAttacker, Creature aDefender) {
        double damageToDeal;
        if (aAttacker.getStats().getAttack() >= aDefender.getStats().getArmor()) {
            double attackPoints = aAttacker.getStats().getAttack() - aDefender.getStats().getArmor();
            if (attackPoints > 60) // max 400% DMG
                attackPoints = 60;
            damageToDeal = (aAttacker.getStats().getDamage().lowerEndpoint() * (1 + attackPoints * 0.05));
        } else {
            double defencePoints = aDefender.getStats().getArmor() - aAttacker.getStats().getAttack();
            if (defencePoints > 27.9999999) // min 30% DMG
                defencePoints = 27.9999999; // tak dziwnie napisane bo jak daje równo 28 to źle liczy
            damageToDeal = (aAttacker.getStats().getDamage().lowerEndpoint() * (1 - (defencePoints * 0.025)));

        }
        return (int) damageToDeal;
    }
}
