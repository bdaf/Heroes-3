package pl.sdk.creatures;

import com.google.common.collect.Range;
import pl.sdk.hero.Fraction;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfectsWithWeaknessCreatureDecorator extends Creature {
    protected final Creature decorated;
    private final Weakness weakness;
    private final Random rand;


    public InfectsWithWeaknessCreatureDecorator(Creature aDecorated, Weakness aWeakness) {
        this(aDecorated, aWeakness, new Random());
    }

    public InfectsWithWeaknessCreatureDecorator(Creature aDecorated, Weakness aWeakness, Random aRandom) {
        decorated = aDecorated;
        weakness = aWeakness;
        rand = aRandom;
    }

    private void infect(Creature defender, Integer aDealtDmg) {
        if (rand.nextDouble() <= weakness.getPercentage()) {
            ArrayList<Weakness> weaknesses =  new ArrayList<>(defender.getWeaknesses());
            for (Weakness w : weaknesses) {
                if (w.equals(weakness)) {
                    w.restartDuration();
                    defender.setWeaknesses(weaknesses);
                    return;
                }
            }
            weakness.setWeak(defender, aDealtDmg);
            weaknesses.add(Weakness.copyOf(weakness));
            defender.setWeaknesses(weaknesses);
        }
    }

    @Override
    void setHpIfNotAlive() {
        decorated.setHpIfNotAlive();
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
    public Integer meleeAttack(Creature defender) {
        return attack(defender);
    }

    @Override
    public Integer attack(Creature defender) {
        Integer dealtDmg = decorated.attack(defender);
        if(dealtDmg == null) throw new NullPointerException("Method \"attack()\" returns null");
        infect(defender, dealtDmg);
        return dealtDmg;
    }

    @Override
    int countDamage(Creature aAttacker, Creature defender) {
        return decorated.countDamage(aAttacker, defender);
    }

    @Override
    Integer counterAttack(Creature defender) {
        Integer counterAttackDamage = decorated.counterAttack(defender);
        infect(defender, counterAttackDamage);
        return counterAttackDamage;
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
}
