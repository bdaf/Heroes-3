package pl.sdk.creatures;

import java.beans.PropertyChangeEvent;

 class RegenerationOnEndOfTurnCreatureDecorator extends Creature{

    private final Creature decorated;

    RegenerationOnEndOfTurnCreatureDecorator(Creature aDecorated) {
        decorated = aDecorated;
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
    int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    public void attack(Creature defender) {
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
     public void setTeam(Team aTeam) {
         decorated.setTeam(aTeam);
     }

     @Override
     public void meleeAttack(Creature defender) {
         decorated.meleeAttack(defender);
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
    public int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    void setCurrentHP(int currentHP) {
        decorated.setCurrentHP(currentHP);
    }

    @Override
    CreatureStatisticlf getStats() {
        return decorated.getStats();
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
    public int getAmount() {
        return decorated.getAmount();
    }

    @Override
    public void setAmount(int aAmount) {
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
    public String getStringOfCurrentHp() {
        return decorated.getStringOfCurrentHp();
    }

    @Override
    public double getAttackRange() {
        return decorated.getAttackRange();
    }
}
