package pl.sdk;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Creature implements PropertyChangeListener {

    private CreatureStatistic stats;
    private CalculateDamageStrategy damageCalculator;
    private int currentHp;
    private double currentMovePoints;
    private boolean counterAttackInThisTurn;
    private int attacksInTurn;
    private int amount;

    public Creature(){
        this(new CreatureStatistic());
        damageCalculator = new DefaultDamageCalculator();
    }
    private Creature(CreatureStatistic stats){
        this.stats = stats;
        currentHp = stats.getMaxHp();
    }
     int getMaxHp(){
        return stats.getMaxHp();
    }


    void attack(Creature defender) {
        if (this == defender) throw new IllegalArgumentException();
        if (isAlive()) {
            int damageToDeal = damageCalculator.count(this, defender);
            defender.ApplyDamage(damageToDeal);
            if (!defender.counterAttackInThisTurn && defender.isAlive()) {
                int damageToDealInCounterAttack = defender.damageCalculator.count(defender, this);
                ApplyDamage(damageToDealInCounterAttack);
                defender.counterAttackInThisTurn = true;
            }
        }
    }

    private void ApplyDamage(int aDamageToApply) {
        int amountToDelete = aDamageToApply/getMaxHp();
        aDamageToApply = aDamageToApply%getMaxHp();
        amount-=amountToDelete;
        if(amount<0){ // pokaz ile zadal po przekroczeniu skali, na minusie :D
            currentHp = currentHp-(getMaxHp()*((amount*-1)+1)+ aDamageToApply);
            amount = 0;
        }
        else
            currentHp-=aDamageToApply;
        if(currentHp<=0 && amount > 1){// kiedy aDamageToApply > currentHP
            currentHp+=getMaxHp();
            amount--;
        }
        else if(amount < 1 && currentHp>0)// kiedy zostaną usunięte same amounty
            currentHp -= getMaxHp();
        else if(currentHp<=0 && amount <= 1) // kiedy nie ma Hp amount jest 1 to musimy zmienić na 0
            amount=0;
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

    int getAmount() { return amount; }

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
        sb.append(getStats().getMaxHp());
        return sb.toString();
    }

    void setCurrentMovePoints(double aCurrentMovePoints) {
        currentMovePoints = aCurrentMovePoints;
    }

    public String getStringOfCurrentHp() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentHp());
        sb.append("/");
        sb.append(getStats().getMaxHp());
        sb.append("  "+amount);
        return sb.toString();
    }

    public static class Builder {
        private String name;
        private Integer attack;
        private Integer armor;
        private Integer maxHp;
        private Integer moveRange;
        private Range<Integer> damage;
        private CalculateDamageStrategy damageCalculator;
        private Integer amount;

        public Builder amount(Integer aAmount){
            this.amount = aAmount;
            return this;
        }
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
        public Builder damageCalculator(CalculateDamageStrategy aDamage){
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
            if(amount == null)
                amount = 1;
            CreatureStatistic stats = new CreatureStatistic(name, attack, armor, maxHp, moveRange, damage);
            Creature result = new Creature(stats);
            result.damageCalculator = this.damageCalculator;
            result.currentMovePoints = stats.getMoveRange();
            result.attacksInTurn = 1;
            result.amount = this.amount;
            return result;
        }
    }
}
