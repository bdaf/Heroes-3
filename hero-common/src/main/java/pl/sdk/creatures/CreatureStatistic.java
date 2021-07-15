package pl.sdk.creatures;


import com.google.common.collect.Range;

enum CreatureStatistic implements CreatureStatisticlf {

    DEFAULT("Default",5,5,100,5,Range.closed(5,5),1,"Default description",false),
    // NECROPOLIS
    SKELETON_WARRIOR("SkeletonWarrior",6,6,6,5,Range.closed(1,3),1,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer.", true),
    ZOMBIE("Zombie",5,5,20,4,Range.closed(2,3),1,"Numerous skeletons become even better, but running back to town and upgrading is a problem... If there is no room in your army for ordinary skeletons, necromancy skill will resurrect skeleton warriors, but there will be less of them than normal skeletons, so it might be a good idea not to upgrade cursed temple at all.", true),
    WRAITH("Wraitch",7,7,18,7,Range.closed(3,5),2,"Will get to those shooters even faster and will really annoy spellcasting heroes by slowly draining their mana!", true),
    VAMPIRE_LORD("VampireLord",10,10,40,8,Range.closed(5,8),2,"Special: no enemy retaliation ; resurrects members of their own stack by restoring health equal to the amount of damage they do to living enemies.", true),
    POWER_LICH("PowerLich",13,10,40,7,Range.closed(11,15),3,"Special: death cloud range attack - damages living creatures on adjacent hexes to target.", true),
    DREAD_KNIGHT("DreadKnight",16,16,120,7,Range.closed(15,30),3,"Special: 20% chance to curse enemy ; 20% chance to do double damage.", true),
    GHOST_DRAGON("GhostDragon",19,17,200,14,Range.closed(25,50),4,"I think it's the best lvl6 unit in the game! Double damage ability puts Dread Knights above Naga Queens.", true),
    SKELETON("Skeleton",5,6,6,4,Range.closed(1,3),4,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer.", false),
    WALKING_DEAD("WalkingDead",5,5,15,3,Range.closed(2,3),5,"Basically its the same skeleton with more hit points. I prefer buying 2 skeletons instead.", false),
    WIGHT("Wight",7,7,18,5,Range.closed(3,5),5,"Special: top wight of the stack regenerates all lost damage in the beginning of each round", false),
    VAMPIRE("Vampire",10,9,30,6,Range.closed(5,8),6,"Special: no enemy retaliation.", false),
    LICH("Lich",13,10,30,6,Range.closed(11,13),6,"Special: death cloud range attack - damages living creatures on adjacent hexes to target.", false),
    BLACK_KNIGHT("BlackKnight",16,16,120,7,Range.closed(15,30),7,"Special: 20% chance to curse enemy.", false),
    BONE_DRAGON("BoneDragon",17,15,150,9,Range.closed(25,50),7,"Special: -1 to enemy morale. They are truly fearsome for enemies with low morale. Simply keeping them on battlefield scares enemies.", false),
    // CASTLE
    HALBERDIER("Halberdier",4,5,10,4,Range.closed(1,3),1,"Now they are faster and do more damage. Will make a good defence for shooters.",true),
    MARKSMAN("Marksman",6,3,10,6,Range.closed(2,3),2,"Awesome upgrade, but they still lack defence...",true),
    ROYAL_GRIFFIN("RoyalGriffin",9,9,25,6,Range.closed(3,6),3,"Send them right in the middle of the battlefield. Everyone who comes will get some ;)",true),
    CRUSADER("Crusader",12,12,35,6,Range.closed(7,10),4,"Good upgrade, but still lacks speed. Seem undefeatable in large numbers.",true),
    ZEALOT("Zealot",12,10,30,7,Range.closed(10,12),5,"Zealots are skilled enough to use the same magic powers at very close range. Better defence too.",true),
    CHAMPION("Champion",16,16,100,9,Range.closed(20,25),6,"That's up to 45% extra damage possible! Champions also have better aiming skills.",true),
    ARCHANGEL("Archangel",30,20,250,18,Range.closed(50,50),7,"Best attack, defence and speed in a whole game! Resurrection is a very convenient abbility. Those fast wings take up a whole extra hex! :] Possibly the best creature in the game.",true),
    PICKMAN("Pickman",4,6,10,4,Range.closed(1,3),1,"Toughest lvl1 unit, but a bit slow.",false),
    ARCHER("Archangel",6,3,10,4,Range.closed(2,3),2,"Archer's upgrade is literally 2 times better. Upgrade them quickly.",false),
    GRIFFIN("Griffin",8,8,25,6,Range.closed(3,6),3,"High in population, griffins become castle's main unit for the midgame.",false),
    SWORDSMAN("Swordsman",10,12,35,5,Range.closed(6,9),4,"High in population, griffins become castle's main unit for the midgame.",false),
    MONK("Monk",12,7,30,5,Range.closed(10,12),5,"Good shooter, nice damage.",false),
    CAVALIER("Cavalier",15,15,100,7,Range.closed(15,25),6,"Make sure that the path is as long as possible - they need some speed!",false),
    ANGEL("Angel",20,20,200,12,Range.closed(50,50),7,"Nice combat ratings and great constant damage - no need to bless them. Note that before the update patches Angels and Archangels didn't cost any gems, just gold. This has been added to try to balance the castle a little. I think it's still too powerful.",false);

    private String name;
    private int attack;
    private int armor;
    private int maxHP;
    private int moveRange;
    private Range<Integer> damage;
    private int tier;
    private String description;
    private boolean isUpgraded;


    CreatureStatistic(String name, int attack, int armor, int maxHP, int moveRange, Range<Integer> damage, int aTier, String aDescription, boolean aIsUpgraded) {
        this.name = name;
        this.maxHP = maxHP;
        this.armor = armor;
        this.attack = attack;
        this.moveRange = moveRange;
        this.damage = damage;
        this.tier = aTier;
        this.description = aDescription;
        this.isUpgraded = aIsUpgraded;
    }


    public String getName(){
        return name;
    }

    @Override
    public String getTranslatedCreatureName() {
        return getName();
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
        return maxHP;
    }

    @Override
    public int getMoveRange() {
        return moveRange;
    }

    @Override
    public Range<Integer> getDamage() {
        return damage;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
