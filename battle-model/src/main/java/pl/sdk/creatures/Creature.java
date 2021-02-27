package pl.sdk.creatures;

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
    private int maxAmount;
    private int amount;

    protected Creature(){
        this(new CreatureStatistic());
        damageCalculator = new DefaultDamageCalculator();
    }
    protected Creature(CreatureStatistic stats){
        this.stats = stats;
        currentHp = stats.getMaxHp();
    }
    int getMaxHp(){
        return stats.getMaxHp();
    }


    void attack(Creature defender) {
        if (this == defender) throw new IllegalArgumentException();
        if (isAlive()) {
            int damageToDeal = countDamage(this, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
            counterAttack(defender);
        }
    }

    int countDamage(Creature aAttacker, Creature defender) {
        return damageCalculator.count(aAttacker, defender);
    }

    protected void counterAttack(Creature defender) {
        if (!defender.counterAttackInThisTurn && defender.isAlive()) {
            int damageToDealInCounterAttack = defender.countDamage(defender, this);
            applyDamage(damageToDealInCounterAttack);
            defender.counterAttackInThisTurn = true;
        }
    }

    protected void performAfterAttack(int aDamageToChange) {}

    void applyDamage(int aDamageToApply) {
        int hpOfWholeStack = currentHp + (amount-1)*getMaxHp();
        hpOfWholeStack-=aDamageToApply;
        if(hpOfWholeStack<=0){
            amount = 0;
            currentHp = 0;
        }
        else{
            if(hpOfWholeStack%getMaxHp() == 0){
                amount = hpOfWholeStack/getMaxHp();
                setCurrentHPToMaxHp();
            }
            else{
                amount = hpOfWholeStack / getMaxHp() + 1;
                currentHp = hpOfWholeStack % getMaxHp();
            }
        }
        if(amount>maxAmount){
            amount = maxAmount;
            setCurrentHPToMaxHp();
        }



//        int amountToDelete = aDamageToApply/getMaxHp();
//        aDamageToApply = aDamageToApply%getMaxHp();
//        amount-=amountToDelete;
//        if(amount<0){ // pokaz ile zadal po przekroczeniu skali, na minusie :D
//            currentHp = currentHp-(getMaxHp()*((amount*-1)+1)+ aDamageToApply);
//            amount = 0;
//        }
//        else
//            currentHp-=aDamageToApply;
//        if(currentHp<=0 && amount > 1){// kiedy aDamageToApply > currentHP
//            currentHp+=getMaxHp();
//            amount--;
//        }
//        if(amount < 1 && currentHp>0)// kiedy zostaną usunięte same amounty
//            currentHp -= getMaxHp();
//        if(currentHp<=0 && amount <= 1) // kiedy nie ma Hp amount jest 1 to musimy zmienić na 0
//            amount=0;
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

    protected double getAttackRange() {
        return 1.5;
    }

    protected void setCurrentHPToMaxHp() {
        currentHp = getMaxHp();
    }

    void setAmount(int aAmount) {
        amount = aAmount;
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
        private int maxAmount;

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
            Creature result = createInstance(stats);
            result.damageCalculator = this.damageCalculator;
            result.currentMovePoints = stats.getMoveRange();
            result.attacksInTurn = 1;
            result.amount = this.amount;
            result.maxAmount = this.amount;
            return result;
        }

        protected Creature createInstance(CreatureStatistic aStats) {
            return new Creature(aStats);
        }
    }
}