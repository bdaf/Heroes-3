package pl.sdk;

public class Creature {
    private CreatureStatistic stats;
    private int currentHP;

    public Creature() {
        stats = new CreatureStatistic("DefName",1,1,1,1);
    }
//test
    public Creature(String name, int attack, int armor, int maxHP, int moveRange) {
        stats = new CreatureStatistic(name,attack,armor,maxHP,moveRange);
    }

}
