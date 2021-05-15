package pl.sdk.creatures;

import com.google.common.collect.Range;

class CreatureStatisticForTests implements CreatureStatisticlf{

    final private String name;
    final private int attack;
    final private int armor;
    final private int maxHp;
    final private int moveRange;
    final private Range<Integer> damage;

    public CreatureStatisticForTests(){
        this("name", 10, 10, 100, 10, Range.closed(8,12));
    }


    public CreatureStatisticForTests(String name, int attack, int armor, int maxHp, int moveRange, Range<Integer> damage) {
        this.name = name;
        this.maxHp = maxHp;
        this.armor = armor;
        this.attack = attack;
        this.moveRange = moveRange;
        this.damage = damage;
    }


    @Override
    public String getTranslatedCreatureName() {
        return name;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getMoveRange() {
        return moveRange;
    }

    @Override
    public Range<Integer> getDamage() {
        return damage;
    }
}