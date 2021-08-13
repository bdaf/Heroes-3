package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Creature implements PropertyChangeListener {

    private CreatureStatisticlf stats;
    private CalculateDamageStrategy damageCalculator;
    private int currentHp;
    private boolean counterAttackInThisTurn;
    private int maxAmount;
    private int amount;
    private int attacksInTurn;
    private int shots;
    private Team team;

    public enum Team{
        LEFT_TEAM("leftTeam"),
        RIGHT_TEAM("rightTeam");

        private final String path;

        Team(String aPath) {
            path = aPath;
        }

        public String getPath() {
            return path;
        }
    }

     Creature(){
        this(new CreatureStatisticForTests());
        maxAmount = amount = 1; attacksInTurn = 1;
        damageCalculator = new DefaultDamageCalculator();
    }
     Creature(CreatureStatisticlf stats){
        this.stats = stats;
        currentHp = stats.getMaxHp();
        shots = stats.getShots();
    }

    boolean wasCounterAttackInThisTurn() {
        return counterAttackInThisTurn;
    }

    void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
        counterAttackInThisTurn = aCounterAttackInThisTurn;
    }

    public Team getTeam() {
        return team;
    }

    void setTeam(Team aTeam) {
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
        if (defender.isAlive() && defender.canCounterAttack()) {
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
        return stats.getTranslatedCreatureName();
    }

    public boolean canCounterAttack() {
        return !counterAttackInThisTurn;
    }

    public int getAmount() { return amount; }

    CreatureStatisticlf getStats() {
        return stats;
    }

    void setCurrentHP(int currentHP) {
        this.currentHp = currentHP;
    }

    void setStats(CreatureStatisticForTests stats) {
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
        sb.append("  "+getAmount());
        return sb.toString();
    }

    public double getAttackRange() {
        if(getShots()>0) return Double.MAX_VALUE;
        return 1.5;
    }
    public int getShots() { return shots; }
    void setShots(int aShots){
        this.shots = aShots;
    }
    void setCurrentHPToMaxHp() {
        currentHp = getMaxHp();
    }

    void setAmount(int aAmount) {
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

    static class BuilderForTesting {
        private String name;
        private Integer attack;
        private Integer armor;
        private Integer maxHp;
        private Integer moveRange;
        private Range<Integer> damage;
        private CalculateDamageStrategy damageCalculator;
        private Integer amount;
        private Integer shots;
        private Integer attacksInTurn;

        BuilderForTesting attacksInTurn(Integer aAttacksInTurn){
            this.attacksInTurn = aAttacksInTurn;
            return this;
        }
        BuilderForTesting shots(Integer aShots){
            this.shots = aShots;
            return this;
        }
        BuilderForTesting amount(Integer aAmount){
            this.amount = aAmount;
            return this;
        }
         BuilderForTesting name(String aName){
            this.name = aName;
            return this;
        }
         BuilderForTesting attack(Integer aAttack){
            this.attack = aAttack;
            return this;
        }
         BuilderForTesting armor(Integer aArmor){
            this.armor = aArmor;
            return this;
        }
         BuilderForTesting maxHp(Integer aMaxHp){
            this.maxHp = aMaxHp;
            return this;
        }
         BuilderForTesting moveRange(Integer aMoveRange){
            this.moveRange = aMoveRange;
            return this;
        }
         BuilderForTesting damage(Range<Integer> aDamage){
            this.damage = aDamage;
            return this;
        }
         BuilderForTesting damageCalculator(CalculateDamageStrategy aDamage){
            this.damageCalculator = aDamage;
            return this;
        }
         Creature build(){
            if(name == null) name = "name";
            if(attack == null) attack = 10;
            if(armor == null) armor = 10;
            if(maxHp == null) maxHp = 100;
            if(moveRange == null) moveRange = 2;
            if(damage == null) damage = Range.closed(6,14);
            if(damageCalculator == null) damageCalculator = new DefaultDamageCalculator();
            if(amount == null) amount = 1;
            if(shots == null) shots = 0;
            if(attacksInTurn == null) attacksInTurn = 1;
            CreatureStatisticForTests stats = new CreatureStatisticForTests(name, attack, armor, maxHp, moveRange, damage, shots);
            Creature result = createInstance(stats);
            result.damageCalculator = this.damageCalculator;
            result.amount = this.amount;
            result.maxAmount = this.amount;
            result.attacksInTurn = attacksInTurn;
            result.setTeam(Team.LEFT_TEAM);
            return result;
        }

         private Creature createInstance(CreatureStatisticlf aStats) {
            return new Creature(aStats);
        }
    }

    static class Builder {
        private CalculateDamageStrategy damageCalculator;
        private Integer attacksInTurn;
        private Integer amount;
        private CreatureStatisticlf statistic;

        Builder attacksInTurn(Integer aAttacksInTurn){
            this.attacksInTurn = aAttacksInTurn;
            return this;
        }
        Builder damageCalculator(CalculateDamageStrategy aDamage){
            this.damageCalculator = aDamage;
            return this;
        }
        Builder amount(Integer aAmount){
            this.amount = aAmount;
            return this;
        }
        Builder statistic(CreatureStatisticlf aStats){
            this.statistic = aStats;
            return this;
        }

        Creature build(){
            if(damageCalculator == null)
                damageCalculator = new DefaultDamageCalculator();
            if(amount == null)
                amount = 1;
            if(attacksInTurn == null)
                attacksInTurn = 1;
            if(statistic == null)
                statistic = CreatureStatistic.DEFAULT;
            Creature result = createInstance(statistic);
            result.damageCalculator = this.damageCalculator;
            result.amount = this.amount;
            result.maxAmount = this.amount;
            result.attacksInTurn = attacksInTurn;
            result.setTeam(Team.LEFT_TEAM);
            return result;
        }

        private Creature createInstance(CreatureStatisticlf aStats) {
            return new Creature(aStats);
        }
    }
}
