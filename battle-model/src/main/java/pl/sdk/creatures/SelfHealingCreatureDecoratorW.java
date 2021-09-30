package pl.sdk.creatures;

import com.google.common.collect.Range;
import pl.sdk.hero.Fraction;

import java.beans.PropertyChangeEvent;
import java.util.List;

class SelfHealingCreatureDecoratorW extends Creature {
    private Creature decorated;
    private double selfHealingPercentage;

    SelfHealingCreatureDecoratorW(Creature aDecorated) {
        decorated = aDecorated;
    }

    SelfHealingCreatureDecoratorW(Creature aDecorated, double aSelfHealingPercentage) {
        this(aDecorated);
        selfHealingPercentage = aSelfHealingPercentage;
    }

    @Override
    public Range<Integer> getDamage() {
        return decorated.getDamage();
    }

    @Override
    public int getArmor() {
        return decorated.getArmor();
    }

    @Override
    public List<Weakness> getWeaknesses() {
        return decorated.getWeaknesses();
    }

    @Override
    void setWeaknesses(List<Weakness> aWeaknesses) {
        decorated.setWeaknesses(aWeaknesses);
    }

    @Override
    public int getAttack() {
        return decorated.getAttack();
    }

    @Override
    protected void performAfterAttack(int aDamageToChange) {
        decorated.applyDamage((int) (-aDamageToChange * selfHealingPercentage));
    }

    @Override
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    void setAmount(int aAmount) {
        decorated.setAmount(aAmount);
    }

    @Override
    public Integer attack(Creature defender) {
        return decorated.attack(defender);
    }

    @Override
    protected void setCurrentHPToMaxHp() {
        decorated.setCurrentHPToMaxHp();
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    protected Integer counterAttack(Creature defender) {
        if (isAlive() && canCounterAttack()) {
            int damageToDealInCounterAttack = countDamage(this, defender);
            defender.applyDamage(damageToDealInCounterAttack);
            performAfterAttack(damageToDealInCounterAttack);
            setIfWasCounterAttackInThisTurn(true);
            return damageToDealInCounterAttack;
        }
        return null;
    }

    @Override
    boolean wasCounterAttackInThisTurn() {
        return decorated.wasCounterAttackInThisTurn();
    }

    @Override
    void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
        decorated.setIfWasCounterAttackInThisTurn(aCounterAttackInThisTurn);
    }

    @Override
    public Fraction getFraction() {
        return decorated.getFraction();
    }

    @Override
    public Team getTeam() {
        return decorated.getTeam();
    }

    @Override
    void setTeam(Team aTeam) {
        decorated.setTeam(aTeam);
    }

    @Override
    public Integer meleeAttack(Creature defender) {
        if (decorated == defender) throw new IllegalArgumentException();
        if (decorated.isAlive()) {
            int damageToDeal = countDamage(decorated, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
            defender.counterAttack(this);
            return damageToDeal;
        }
        return null;
    }

    @Override
    public boolean[][] getSplashDamage() {
        return decorated.getSplashDamage();
    }

    @Override
    public void applyDamage(int aDamageToApply) {
        decorated.applyDamage(aDamageToApply);
    }

    @Override
    public boolean isAlive() {
        return decorated.isAlive();
    }

    @Override
    public int getShots() {
        return decorated.getShots();
    }

    @Override
    void setShots(int aShoots) {
        decorated.setShots(aShoots);
    }

    @Override
    public int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    void setCurrentHP(int currentHP) {
        decorated.setCurrentHP(currentHP);
    }

    @Override
    public CreatureStatisticlf getStats() {
        return decorated.getStats();
    }

    @Override
    void setStats(CreatureStatisticForTests stats) {
        decorated.setStats(stats);
    }

    @Override
    public double getMoveRange() {
        return decorated.getMoveRange();
    }

    @Override
    public int getMaxAttacksInTurn() {
        return decorated.getMaxAttacksInTurn();
    }

    @Override
    void setAttacksInTurn(int aAttacksInTurn) {
        decorated.setAttacksInTurn(aAttacksInTurn);
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    public boolean canCounterAttack() {
        return decorated.canCounterAttack();
    }

    @Override
    public int getAmount() {
        return decorated.getAmount();
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        decorated.propertyChange(aPropertyChangeEvent);
    }

    @Override
    public String toString() {
        return decorated.toString();
    }

    @Override
    public String getStringOfCurrentHp() {
        return decorated.getStringOfCurrentHp();
    }

    @Override
    public double getAttackRange() {
        return decorated.getAttackRange();
    }
}
