package pl.sdk.creatures;

import com.google.common.collect.Range;
import pl.sdk.Point;
import pl.sdk.hero.Fraction;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static pl.sdk.GameEngine.*;

public class TravelToIncreaseDamageCreatureDecoratorW extends Creature {
    private final Creature decorated;
    private final double percentageOfAttackIncreasePerPoint;
    private int traveledPoints;
    private boolean shouldCountTravelPoints;

    public TravelToIncreaseDamageCreatureDecoratorW(Creature aDecorated, double aPercentageOfAttackIncreasePerPoint) {
        decorated = aDecorated;
        percentageOfAttackIncreasePerPoint = aPercentageOfAttackIncreasePerPoint;
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
    public Range<Integer> getDamage() {
        Range<Integer> range = decorated.getDamage();
        double increase = 1 + traveledPoints * percentageOfAttackIncreasePerPoint;
        int lowerEndPoint = (int) (range.lowerEndpoint() * increase);
        int upperEndPoint = (int) (range.upperEndpoint() * increase);
        return Range.closed(lowerEndPoint, upperEndPoint);
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
    public Integer meleeAttack(Creature defender) {
        return attack(defender);
    }

    @Override
    public Integer attack(Creature defender) {
        if (this == defender) throw new IllegalArgumentException("Creature cannot attack himself!");
        if (isAlive()) {
            int damageToDeal = countDamage(this, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
            defender.counterAttack(this);
            return damageToDeal;
        }
        return null;
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    Integer counterAttack(Creature defender) {
        return decorated.counterAttack(defender);
    }

    @Override
    public void applyDamage(int aDamageToApply) {
        decorated.applyDamage(aDamageToApply);
    }

    @Override
    void performAfterAttack(int aDamageToChange) {
        decorated.performAfterAttack(aDamageToChange);
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
    public int getMaxHp() {
        return decorated.getMaxHp();
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
        if (shouldCountTravelPoints && aPropertyChangeEvent.getPropertyName().equals(CREATURE_MOVED)) {
            Point oldPoint = (Point) aPropertyChangeEvent.getOldValue();
            Point newPoint = (Point) aPropertyChangeEvent.getNewValue();
            traveledPoints = (int) oldPoint.distance(newPoint);
        } else if (aPropertyChangeEvent.getPropertyName().equals(CURRENT_CREATURE_CHANGED)) {
            setIfShouldCountTravelPoints(aPropertyChangeEvent);
        } else if (aPropertyChangeEvent.getPropertyName().equals(BEGINNING_OF_GAME) && aPropertyChangeEvent.getNewValue() == this)
            setIfShouldCountTravelPoints(aPropertyChangeEvent);
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
    public int getShots() {
        return decorated.getShots();
    }

    @Override
    void setShots(int aShots) {
        decorated.setShots(aShots);
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

    private void setIfShouldCountTravelPoints(PropertyChangeEvent aPropertyChangeEvent) {
        if (aPropertyChangeEvent.getNewValue() == this) {
            shouldCountTravelPoints = true;
        } else {
            shouldCountTravelPoints = false;
            traveledPoints = 0;
        }
    }
}
