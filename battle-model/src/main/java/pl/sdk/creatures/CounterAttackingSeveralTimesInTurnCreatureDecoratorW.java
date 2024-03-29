package pl.sdk.creatures;

import com.google.common.collect.Range;
import pl.sdk.hero.Fraction;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static pl.sdk.GameEngine.UPDATE_AFTER_EVERY_TURN;

public class CounterAttackingSeveralTimesInTurnCreatureDecoratorW extends Creature {

    private Creature decorated;
    private final int maxCounterAttacksInTurn;
    private int counterAttacksInTurn;

    CounterAttackingSeveralTimesInTurnCreatureDecoratorW(Creature aDecorated, int aCounterAttacksInTurn) {
        decorated = aDecorated;
        maxCounterAttacksInTurn = counterAttacksInTurn = aCounterAttacksInTurn;
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
    public int getAttack() {
        return decorated.getAttack();
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
    boolean wasCounterAttackInThisTurn() {
        if (counterAttacksInTurn > 0)
            return false;
        return true;
    }

    @Override
    void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
        if (aCounterAttackInThisTurn) counterAttacksInTurn--;
        else counterAttacksInTurn = maxCounterAttacksInTurn;
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
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    public Integer attack(Creature defender) {
        return decorated.attack(defender);
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    Integer counterAttack(Creature defender) {
        if (isAlive() && canCounterAttack()) {
            int damageToDealInCounterAttack = countDamage(this, defender);
            defender.applyDamage(damageToDealInCounterAttack);
            setIfWasCounterAttackInThisTurn(true);
            return damageToDealInCounterAttack;
        }
        return null;
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
    void setHpIfNotAlive() {
        decorated.setHpIfNotAlive();
    }

    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    public boolean canCounterAttack() {
        return !wasCounterAttackInThisTurn();
    }

    @Override
    public int getAmount() {
        return decorated.getAmount();
    }

    @Override
    public CreatureStatisticlf getStats() {
        return decorated.getStats();
    }

    @Override
    void setCurrentHP(int currentHP) {
        decorated.setCurrentHP(currentHP);
    }

    @Override
    void setStats(CreatureStatisticForTests stats) {
        decorated.setStats(stats);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        if(aPropertyChangeEvent.getPropertyName().equals(UPDATE_AFTER_EVERY_TURN))
            setIfWasCounterAttackInThisTurn(false);
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
    public Integer meleeAttack(Creature defender) {
        return decorated.meleeAttack(defender);
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
