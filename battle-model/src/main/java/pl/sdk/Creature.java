package pl.sdk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Creature implements PropertyChangeListener {

    private CreatureStatistic stats;
    private DamageCalculator damageCalculator;
    private int currentHp;
    private double currentMovePoints;
    private boolean counterAttackInThisTurn;
    private int attacksInTurn;

    public Creature() {
        this("DefName", 2, 1, 2, 10,1);
    }

    Creature(String name, int attack, int armor, int maxHP, int moveRange, int aDamage) {
        this(name, attack, armor, maxHP, moveRange, new NewDamageCalculator(), aDamage);
    }
    Creature(String name, int attack, int armor, int maxHP, int moveRange, DamageCalculator aDamageCalculator, int aDamage) {
        stats = new CreatureStatistic(name, attack, armor, maxHP, moveRange, aDamage);
        currentHp = stats.getMaxHP();
        currentMovePoints = stats.getMoveRange();
        attacksInTurn = 1;
        damageCalculator = aDamageCalculator;
    }

    void attack(Creature defender) {
        if (this == defender) throw new IllegalArgumentException();
        if (isAlive()) {
            int damageToDeal = damageCalculator.count(this, defender);
            defender.currentHp -= damageToDeal;
            if (!defender.counterAttackInThisTurn && defender.isAlive()) {
                int damageToDealInCounterAttack = defender.damageCalculator.count(defender, this);
                currentHp -= damageToDealInCounterAttack;
                defender.counterAttackInThisTurn = true;
            }
        }

    }



    boolean isAlive() {
        return currentHp > 0;
    }

    int getCurrentHp() {
        return currentHp;
    }

    void setCurrentHP(int currentHP) {
        this.currentHp = currentHP;
    }

    double getCurrentMovePoints() { return currentMovePoints; }

    CreatureStatistic getStats() {
        return stats;
    }

    void setStats(CreatureStatistic stats) {
        this.stats = stats;
    }

    public String getName() {
        return stats.getName();
    }

    int getAttacksInTurn() {
        return attacksInTurn;
    }

    void setAttacksInTurn(int aAttacksInTurn) {
        attacksInTurn = aAttacksInTurn;
    }

    boolean canCounterAttack() {
        return !counterAttackInThisTurn;
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        counterAttackInThisTurn = false;
        currentMovePoints = stats.getMoveRange();
        attacksInTurn = 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(System.lineSeparator());
        sb.append(getCurrentHp());
        sb.append("/");
        sb.append(getStats().getMaxHP());
        return sb.toString();
    }

    void setCurrentMovePoints(double aCurrentMovePoints) {
        currentMovePoints = aCurrentMovePoints;
    }
}
