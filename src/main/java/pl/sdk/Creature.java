package pl.sdk;

public class Creature {

    private CreatureStatistic stats;
    private int currentHp;

    public Creature() {
        this("DefName",1,1,1,1);
    }
    
    public Creature(String name, int attack, int armor, int maxHP, int moveRange) {
        stats = new CreatureStatistic(name,attack,armor,maxHP,moveRange);
        currentHp = stats.getMaxHP();
    }

    public void attack(Creature defender) {
        int damageToDeal = this.getStats().getAttack() - defender.getStats().getArmor();
        if(damageToDeal<0) damageToDeal = 0;
        defender.setCurrentHP(defender.getCurrentHp()-damageToDeal);
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
