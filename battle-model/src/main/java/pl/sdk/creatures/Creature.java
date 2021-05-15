package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Creature implements PropertyChangeListener {

    private CreatureStatistic stats;
    private CalculateDamageStrategy damageCalculator;
    private int currentHp;
    private boolean counterAttackInThisTurn;
    private int maxAmount;
    private int amount;
    private int attacksInTurn;
    private String team;

    Creature(){
        this(new CreatureStatistic());
        maxAmount = amount = 1; attacksInTurn = 1;
        damageCalculator = new DefaultDamageCalculator();
    }
     Creature(CreatureStatistic stats){
        this.stats = stats;
        currentHp = stats.getMaxHp();
    }

    boolean wasCounterAttackInThisTurn() {
        return counterAttackInThisTurn;
    }

    void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
        counterAttackInThisTurn = aCounterAttackInThisTurn;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String aTeam) {
        team = aTeam;
    }

    int getMaxHp(){
        return stats.getMaxHp();
    }

    public void meleeAttack(Creature defender){
        attack(defender);
    }

    public void attack(Creature defender) {
        if (this == defender) throw new IllegalArgumentException();
        if (isAlive()) {
            int damageToDeal = countDamage(this, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
            counterAttack(defender);
        }
    }

    int countDamage(Creature aAttacker, Creature defender) {
        return damageCalculator.calculateDamage(aAttacker, defender);
    }

    void counterAttack(Creature defender) {
        if (defender.canCounterAttack() && defender.isAlive()) {
            int damageToDealInCounterAttack = defender.countDamage(defender, this);
            applyDamage(damageToDealInCounterAttack);
            defender.setIfWasCounterAttackInThisTurn(true);
        }
    }

     void performAfterAttack(int aDamageToChange) {}

    public void applyDamage(int aDamageToApply) {
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
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public double getMoveRange() { return stats.getMoveRange(); }

    public String getName() {
        return stats.getName();
    }

    public boolean canCounterAttack() {
        return !counterAttackInThisTurn;
    }

    public int getAmount() { return amount; }

    CreatureStatistic getStats() {
        return stats;
    }

    void setCurrentHP(int currentHP) {
        this.currentHp = currentHP;
    }

    void setStats(CreatureStatistic stats) {
        this.stats = stats;
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        counterAttackInThisTurn = false;
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


    public String getStringOfCurrentHp() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentHp());
        sb.append("/");
        sb.append(getStats().getMaxHp());
        sb.append("  "+amount);
        return sb.toString();
    }

    public double getAttackRange() {
        return 1.5;
    }

    void setCurrentHPToMaxHp() {
        currentHp = getMaxHp();
    }

    public void setAmount(int aAmount) {
        amount = aAmount;
    }

    public int getMaxAttacksInTurn() {
        return attacksInTurn;
    }

    void setAttacksInTurn(int aAttacksInTurn) {
        attacksInTurn = aAttacksInTurn;
    }

    public boolean[][] getSplashDamage() {
        boolean[][] result = new boolean[3][3];
        result[1][1] = true;
        return result;
    }


    static class Builder {

        private CalculateDamageStrategy damageCalculator;
        private Integer amount;
        private Integer attacksInTurn;
        private int maxAmount;

        Builder attacksInTurn(Integer aAttacksInTurn){
            this.attacksInTurn = aAttacksInTurn;
            return this;
        }
        Builder amount(Integer aAmount){
            this.amount = aAmount;
            return this;
        }

         Builder damageCalculator(CalculateDamageStrategy aDamage){
            this.damageCalculator = aDamage;
            return this;
        }
         Creature build(){
            if(damageCalculator == null)
                damageCalculator = new DefaultDamageCalculator();
            if(amount == null)
                amount = 1;
            if(attacksInTurn == null)
                attacksInTurn = 1;
            CreatureStatistic stats = new CreatureStatistic.DEAFULT;
            Creature result = createInstance(stats);
            result.damageCalculator = this.damageCalculator;
            result.amount = this.amount;
            result.maxAmount = this.amount;
            result.attacksInTurn = attacksInTurn;
            result.setTeam("leftTeam");
            return result;
        }

         Creature createInstance(CreatureStatistic aStats) {
            return new Creature(aStats);
        }
    }
}
