package pl.sdk;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Creature implements PropertyChangeListener {

    private CreatureStatistic stats;
    private DamageCalculator damageCalculator;
    private int currentHp;
    private double currentMovePoints;
    private boolean counterAttackInThisTurn;
    private int attacksInTurn;

    public Creature(){
        this(new CreatureStatistic());
        damageCalculator = new DefaultDamageCalculator();
    }
    private Creature(CreatureStatistic stats){
        this.stats = stats;
        currentHp = stats.getMaxHP();
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

    public String getStringOfCurrentHp() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentHp());
        sb.append("/");
        sb.append(getStats().getMaxHP());
        return sb.toString();
    }

    public static class Builder {
        private String name;
        private Integer attack;
        private Integer armor;
        private Integer maxHp;
        private Integer moveRange;
        private Range<Integer> damage;
        private DamageCalculator damageCalculator;

        public Builder name(String aName){
            this.name = aName;
            return this;
        }
        public Builder attack(Integer aAttack){
            this.attack = aAttack;
            return this;
        }
        public Builder armor(Integer aArmor){
            this.armor = aArmor;
            return this;
        }
        public Builder maxHp(Integer aMaxHp){
            this.maxHp = aMaxHp;
            return this;
        }
        public Builder moveRange(Integer aMoveRange){
            this.moveRange = aMoveRange;
            return this;
        }
        public Builder damage(Range<Integer> aDamage){
            this.damage = aDamage;
            return this;
        }
        public Builder damageCalculator(DamageCalculator aDamage){
            this.damageCalculator = aDamage;
            return this;
        }

        public Creature build(){
            if(name == null)
                name = "name";
            if(attack == null)
                attack = 10;
            if(armor == null)
                armor = 10;
            if(maxHp == null)
                maxHp = 100;
            if(moveRange == null)
                moveRange = 10;
            if(damage == null)
                damage = Range.closed(6,14);
            if(damageCalculator == null)
                damageCalculator = new DefaultDamageCalculator();
            CreatureStatistic stats = new CreatureStatistic(name, attack, armor, maxHp, moveRange, damage);
            Creature result = new Creature(stats);
            result.damageCalculator = this.damageCalculator;
            result.currentMovePoints = stats.getMoveRange();
            result.attacksInTurn = 1;
            return result;
        }
    }
}
