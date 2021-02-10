package pl.sdk;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

public class Creature implements PropertyChangeListener {

    private CreatureStatistic stats;
    private DamageCalculator damageCalculator;
    private int currentHp;
    private double currentMovePoints;
    private boolean counterAttackInThisTurn;
    private int attacksInTurn;

    public Creature(CreatureStatistic stats){
        this.stats = stats;
        currentHp = stats.getMaxHP();
    }

    public Creature() {
        this("DefName", 2, 1, 2, 10,1);
    }

    Creature(String name, int attack, int armor, int maxHP, int moveRange, int aDamage) {
        this(name, attack, armor, maxHP, moveRange, new DefaultDamageCalculator(), Range.closed(aDamage,aDamage));
    }
    Creature(String name, int attack, int armor, int maxHP, int moveRange, DamageCalculator aDamageCalculator, Range<Integer> aDamage) {
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

    public class Builder {
        private String name;
        private Integer attack;
        private Integer armor;
        private Integer maxHp;
        private Integer moveRange;
        private Range<Integer> damage;
        private DamageCalculator damageCalculator;

        Builder name(String aName){
            this.name = aName;
            return this;
        }
        Builder attack(Integer aAttack){
            this.attack = aAttack;
            return this;
        }
        Builder armor(Integer aArmor){
            this.armor = aArmor;
            return this;
        }
        Builder maxHp(Integer aMaxHp){
            this.maxHp = aMaxHp;
            return this;
        }
        Builder moveRange(Integer aMoveRange){
            this.moveRange = aMoveRange;
            return this;
        }
        Builder damage(Range<Integer> aDamage){
            this.damage = aDamage;
            return this;
        }
        Builder damageCalculator(DamageCalculator aDamage){
            this.damageCalculator = aDamage;
            return this;
        }

        Creature build(){
            Set<String> EmptyFields = new HashSet<String>();
            if(name == null)
                EmptyFields.add("name");
            if(attack == null)
                EmptyFields.add("attack");
            if(armor == null)
                EmptyFields.add("armor");
            if(maxHp == null)
                EmptyFields.add("maxHp");
            if(moveRange == null)
                EmptyFields.add("moveRange");
            if(damage == null)
                EmptyFields.add("damage");
            if(!EmptyFields.isEmpty())
                throw new IllegalArgumentException("U need this fields to be filled: "+ EmptyFields.toString()+" ");
            CreatureStatistic stats = new CreatureStatistic(name, attack, armor, maxHp, moveRange, damage);
            Creature result = new Creature(stats);
            if(damageCalculator == null)
                damageCalculator = new DefaultDamageCalculator();
            return result;
        }
    }
}
