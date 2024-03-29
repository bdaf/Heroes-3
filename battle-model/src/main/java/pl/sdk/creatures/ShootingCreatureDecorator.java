package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.beans.PropertyChangeEvent;
import java.util.List;

class ShootingCreatureDecorator extends Creature {

    private final Creature decorated;

    ShootingCreatureDecorator(Creature aDecorated) {
        decorated = aDecorated;
    }

    @Override
    public List<Weakness> getWeaknesses() {
        return decorated.getWeaknesses();
    }

    @Override
    void addWeakness(Weakness aWeakness) {
        decorated.addWeakness(aWeakness);
    }

    @Override
    Range<Integer> getDamage() {
        return decorated.getDamage();
    }

    @Override
    public int getArmor() {
        return decorated.getArmor();
    }

    @Override
    public int getAttack() {
        return decorated.getAttack();
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
    boolean wasCounterAttackInThisTurn() {
        return decorated.wasCounterAttackInThisTurn();
    }

    @Override
    void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
        decorated.setIfWasCounterAttackInThisTurn(aCounterAttackInThisTurn);
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
    public void meleeAttack(Creature defender) {
        decorated.meleeAttack(defender);
    }

    @Override
    void setAmount(int aAmount) {
        decorated.setAmount(aAmount);
    }

    @Override
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    public void attack(Creature defender) {
        if (decorated == defender) throw new IllegalArgumentException();
        if (decorated.isAlive()) {
            int damageToDeal = countDamage(decorated, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
        }
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    protected void counterAttack(Creature defender) {
        decorated.counterAttack(defender);
    }

    @Override
    protected void performAfterAttack(int aDamageToChange) {
        decorated.performAfterAttack(aDamageToChange);
    }

    @Override
    public void applyDamage(int aDamageToApply) {
        decorated.applyDamage(aDamageToApply);
    }

    @Override
    public boolean[][] getSplashDamage() {

        return decorated.getSplashDamage();
    }

    @Override
    public boolean isAlive() {
        return decorated.isAlive();
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
    public int getShots() {
        return decorated.getShots();
    }

    @Override
    void setShots(int aShoots) {
        decorated.setShots(aShoots);
    }

    @Override
    void setStats(CreatureStatisticForTests stats) {
        decorated.setStats(stats);
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
    protected void setCurrentHPToMaxHp() {
        decorated.setCurrentHPToMaxHp();
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
        StringBuilder sb = new StringBuilder();
        sb.append("S:\n" + getShots() + "\n");
        sb.append(getCurrentHp());
        sb.append("/");
        sb.append(getStats().getMaxHp());
        sb.append("  " + getAmount());
        return sb.toString();
    }

    @Override
    public double getAttackRange() {
        return decorated.getAttackRange();
    }
}
