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
        int attack = this.getStats().getAttack() - defender.getStats().getArmor();
        if(attack<0) attack = 0;
        defender.setCurrentHP(defender.getCurrentHp()-attack);
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
