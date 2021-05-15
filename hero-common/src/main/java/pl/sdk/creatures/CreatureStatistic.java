package pl.sdk.creatures;


import com.google.common.collect.Range;

import java.util.Random;

enum CreatureStatistic {

    DEFAULT("Default",5,5,100,5,Range.closed(5,5),1,"Default description",false),

    SKELETON_WARRIOR("SkeletonWarrior",6,6,6,5,Range.closed(1,3),1,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer.", false),
    ZOMBIE("Zombie",5,5,20,4,Range.closed(2,3),1,"Numerous skeletons become even better, but running back to town and upgrading is a problem... If there is no room in your army for ordinary skeletons, necromancy skill will resurrect skeleton warriors, but there will be less of them than normal skeletons, so it might be a good idea not to upgrade cursed temple at all.", false),
    WRAITH("Wraitch",7,7,18,7,Range.closed(3,5),2,"Will get to those shooters even faster and will really annoy spellcasting heroes by slowly draining their mana!", false),
    VAMPIRE_LORD("VampireLord",10,10,40,8,Range.closed(5,8),2,"Special: no enemy retaliation ; resurrects members of their own stack by restoring health equal to the amount of damage they do to living enemies.", false),
    POWER_LICH("PowerLich",13,10,40,7,Range.closed(11,15),3,"Special: death cloud range attack - damages living creatures on adjacent hexes to target.", false),
    DREAD_KNIGHT("DreadKnight",16,16,120,7,Range.closed(15,30),3,"Special: 20% chance to curse enemy ; 20% chance to do double damage.", false),
    GHOST_DRAGON("GhostDragon",19,17,200,14,Range.closed(25,50),4,"I think it's the best lvl6 unit in the game! Double damage ability puts Dread Knights above Naga Queens.", false),
    SKELETON("Skeleton",5,6,6,4,Range.closed(1,3),4,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer.", false),
    WALKING_DEAD("WalkingDead",5,5,15,3,Range.closed(2,3),5,"Basically its the same skeleton with more hit points. I prefer buying 2 skeletons instead.", false),
    WIGHT("Wight",7,7,18,5,Range.closed(3,5),5,"Special: top wight of the stack regenerates all lost damage in the beginning of each round", false),
    VAMPIRE("Vampire",10,9,30,6,Range.closed(5,8),6,"Special: no enemy retaliation.", false),
    LICH("Lich",13,10,30,6,Range.closed(11,13),6,"Special: death cloud range attack - damages living creatures on adjacent hexes to target.", false),
    BLACK_KNIGHT("BlackKnight",16,16,120,7,Range.closed(15,30),7,"Special: 20% chance to curse enemy.", false),
    BONE_DRAGON("BoneDragon",17,15,150,9,Range.closed(25,50),7,"Special: -1 to enemy morale. They are truly fearsome for enemies with low morale. Simply keeping them on battlefield scares enemies.", false);


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
        this.description = aDescription;
    }
}
