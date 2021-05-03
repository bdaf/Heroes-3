package pl.sdk.creatures;

import java.beans.PropertyChangeEvent;

 class BlockingCounterAttackCreatureDecorator extends Creature {

    protected final Creature decorated;

    BlockingCounterAttackCreatureDecorator(Creature aDecorated) {
        decorated = aDecorated;
    }

    @Override
    int getMaxHp() {
        return decorated.getMaxHp();
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
    void setAmount(int aAmount) {
        decorated.setAmount(aAmount);
    }

    @Override
    public void attack(Creature defender) {
        if (decorated == defender) throw new IllegalArgumentException();
        if (decorated.isAlive()) {
            int damageToDeal = countDamage(decorated, defender);
            defender.applyDamage(damageToDeal);
            performAfterAttack(damageToDeal);
            counterAttack(defender);
        }
    }

    @Override
    protected void counterAttack(Creature defender) {
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
     boolean wasCounterAttackInThisTurn() {
         return decorated.wasCounterAttackInThisTurn();
     }

     @Override
     void setIfWasCounterAttackInThisTurn(boolean aCounterAttackInThisTurn) {
         decorated.setIfWasCounterAttackInThisTurn(aCounterAttackInThisTurn);
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
     public void meleeAttack(Creature defender) {
         attack(defender);
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
    CreatureStatistic getStats() {
        return decorated.getStats();
    }

    @Override
    void setStats(CreatureStatistic stats) {
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
