package pl.sdk;

public class DamageCalculator {
    int count(Creature attacker, Creature defender) {
        int damageToDeal = attacker.getStats().getAttack() - defender.getStats().getArmor();
        if (damageToDeal < 0) damageToDeal = 0;
        return damageToDeal;
    }
}
