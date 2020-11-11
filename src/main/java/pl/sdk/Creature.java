package pl.sdk;

public class Creature {

    private CreatureStatistic stats;
    private int currentHp;
    private boolean counterAttackInThisTurn;

    public Creature() {
        this("DefName",1,1,1,1);
    }
    
    public Creature(String name, int attack, int armor, int maxHP, int moveRange) {
        stats = new CreatureStatistic(name,attack,armor,maxHP,moveRange);
        currentHp = stats.getMaxHP();
    }

    public void attack(Creature defender) {
        if(isAlive()){
            int damageToDeal = getDamageToDeal(defender);
            defender.currentHp-=damageToDeal;
            if(!defender.counterAttackInThisTurn && defender.isAlive()){
                int damageToDealInCounterAttack = defender.getDamageToDeal(this);
                currentHp-=damageToDealInCounterAttack;
                defender.counterAttackInThisTurn = true;
            }
        }

    }

    private int getDamageToDeal(Creature defender) {
        int damageToDeal = this.getStats().getAttack() - defender.getStats().getArmor();
        if(damageToDeal<0) damageToDeal = 0;
        return damageToDeal;
    }

    private boolean isAlive() {
        return currentHp>0;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHp = currentHP;
    }

    public CreatureStatistic getStats() {
        return stats;
    }

    public void setStats(CreatureStatistic stats) {
        this.stats = stats;
    }
}
