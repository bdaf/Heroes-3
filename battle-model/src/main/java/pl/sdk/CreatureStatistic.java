package pl.sdk;

public class CreatureStatistic {
    final private String name;
    final private int attack;
    final private int armor;
    final private int maxHP;
    final private int moveRange;


    public CreatureStatistic(String name, int attack, int armor, int maxHP, int moveRange) {
        this.name = name;
        this.maxHP = maxHP;
        this.armor = armor;
        this.attack = attack;
        this.moveRange = moveRange;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMoveRange() {
        return moveRange;
    }
}
