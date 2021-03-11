package pl.sdk.creatures;

import java.beans.PropertyChangeEvent;

public class DoubleAttackDecorator extends Creature{
    private Creature decorated;

    DoubleAttackDecorator(Creature aDecorated) {
        decorated = aDecorated;
    }

    @Override
    public String getTeam() {
        return decorated.getTeam();
    }

    @Override
    public void setTeam(String aTeam) {
        decorated.setTeam(aTeam);
    }

    @Override
    int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    public void attack(Creature defender) {
        decorated.attack(defender);
        decorated.attack(defender);
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    void counterAttack(Creature defender) {
        decorated.counterAttack(defender);
    }

    @Override
    void performAfterAttack(int aDamageToChange) {
        decorated.performAfterAttack(aDamageToChange);
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
    public int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    public double getMoveRange() {
        return decorated.getMoveRange();
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
    CreatureStatistic getStats() {
        return decorated.getStats();
    }

    @Override
    void setCurrentHP(int currentHP) {
        decorated.setCurrentHP(currentHP);
    }

    @Override
    void setStats(CreatureStatistic stats) {
        decorated.setStats(stats);
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

    @Override
    void setCurrentHPToMaxHp() {
        decorated.setCurrentHPToMaxHp();
    }

    @Override
    void setAmount(int aAmount) {
        decorated.setAmount(aAmount);
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
    public boolean[][] getSplashDamage() {
        return decorated.getSplashDamage();
    }
}
