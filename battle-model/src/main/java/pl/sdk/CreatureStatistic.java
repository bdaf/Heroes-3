package pl.sdk;

public class CreatureStatistic {

    final private String name;
    final private int attack;
    final private int armor;
    final private int maxHP;
    final private int moveRange;
    final private int damage;




    public CreatureStatistic(String name, int attack, int armor, int maxHP, int moveRange, int damage) {
        this.name = name;
        this.maxHP = maxHP;
        this.armor = armor;
        this.attack = attack;
        this.moveRange = moveRange;
        this.damage = damage;
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
    int getDamage() { return damage; }
}
