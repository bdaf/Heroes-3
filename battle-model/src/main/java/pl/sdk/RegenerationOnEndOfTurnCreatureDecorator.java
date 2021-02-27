package pl.sdk;

import java.beans.PropertyChangeEvent;

public class RegenerationOnEndOfTurnCreatureDecorator extends Creature{

    private final Creature decorated;

    RegenerationOnEndOfTurnCreatureDecorator(Creature aDecorated) {
        decorated = aDecorated;
    }

    @Override
    int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    void attack(Creature defender) {
        decorated.attack(defender);
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
    void applyDamage(int aDamageToApply) {
        decorated.applyDamage(aDamageToApply);
    }

    @Override
    boolean isAlive() {
        return decorated.isAlive();
    }

    @Override
    int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    void setCurrentHP(int currentHP) {
        decorated.setCurrentHP(currentHP);
    }

    @Override
    double getCurrentMovePoints() {
        return decorated.getCurrentMovePoints();
    }

    @Override
    CreatureStatistic getStats() {
        return decorated.getStats();
    }

    @Override
    void setStats(CreatureStatistic stats) {
        decorated.setStats(stats);
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    int getAttacksInTurn() {
        return decorated.getAttacksInTurn();
    }

    @Override
    void setAttacksInTurn(int aAttacksInTurn) {
        decorated.setAttacksInTurn(aAttacksInTurn);
    }

    @Override
    boolean canCounterAttack() {
        return decorated.canCounterAttack();
    }

    @Override
    int getAmount() {
        return decorated.getAmount();
    }

    @Override
    void setAmount(int aAmount) {
        decorated.setAmount(aAmount);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        decorated.propertyChange(aPropertyChangeEvent);
        if(decorated.getAmount()>0)
            setCurrentHPToMaxHp();
    }

    @Override
    protected void setCurrentHPToMaxHp() {
        decorated.setCurrentHPToMaxHp();
    }

    @Override
    public String toString() {
        return decorated.toString();
    }

    @Override
    void setCurrentMovePoints(double aCurrentMovePoints) {
        decorated.setCurrentMovePoints(aCurrentMovePoints);
    }

    @Override
    public String getStringOfCurrentHp() {
        return decorated.getStringOfCurrentHp();
    }

    @Override
    protected double getAttackRange() {
        return decorated.getAttackRange();
    }
}
