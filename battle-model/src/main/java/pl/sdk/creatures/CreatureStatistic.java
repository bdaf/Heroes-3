package pl.sdk.creatures;


import com.google.common.collect.Range;

 class CreatureStatistic {

    final private String name;
    final private int attack;
    final private int armor;
    final private int maxHP;
    final private int moveRange;
    final private Range<Integer> damage;

    public CreatureStatistic(){
        this("name", 10, 10, 100, 10, Range.closed(8,12));
    }


    public CreatureStatistic(String name, int attack, int armor, int maxHP, int moveRange, Range<Integer> damage) {
        this.name = name;
        this.maxHP = maxHP;
        this.armor = armor;
        this.attack = attack;
        this.moveRange = moveRange;
        this.damage = damage;
    }


     String getName() {
        return name;
    }

     int getAttack() {
        return attack;
    }

     int getArmor() {
        return armor;
    }

     int getMaxHp() {
        return maxHP;
    }

     int getMoveRange() {
        return moveRange;
    }
    Range<Integer> getDamage() { return damage; }
}
