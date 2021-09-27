package pl.sdk.creatures;


import com.google.common.collect.Range;

public enum CreatureStatistic implements CreatureStatisticlf {

    DEFAULT("Default",5,5,100,5,Range.closed(5,5),1,"Default champion.",false),
    // NECROPOLIS
    SKELETON("Skeleton",5,6,6,4,Range.closed(1,3),1,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer. \nSpecial Abilities: None.", false),
    WALKING_DEAD("WalkingDead",5,5,15,3,Range.closed(2,3),2,"Basically its the same skeleton with more hit points. I prefer buying 2 skeletons instead. \nSpecial Abilities: None.", false),
    WIGHT("Wight",7,7,18,5,Range.closed(3,5),3,"Regenerating ability is really good when fighting weak enemies, especially shooters. \nSpecial Abilities: Top wight of the stack regenerates all lost damage in the beginning of each round.", false),
    VAMPIRE("Vampire",10,9,30,6,Range.closed(5,8),4,"NOTHING compared to their upgraded brothers. Keep the population growing and recruit after the upgrade. \nSpecial Abilities: No enemy retaliation.", false),
    LICH("Lich",13,10,30,3,Range.closed(11,13),5,"Cloud won't damage your undead units, so aim right in that crowd! \nSpecial Abilities: Death cloud range attack - damages living creatures on adjacent squares to target.", false,12),
    BLACK_KNIGHT("BlackKnight",16,16,120,7,Range.closed(15,30),6,"Awesome ground unit. As any undead it cannot be blinded, so your enemies will have to look out. \nSpecial Abilities: 20% chance to curse enemy.", false),
    BONE_DRAGON("BoneDragon",17,15,150,9,Range.closed(25,50),7,"They are truly fearsome for enemies with low morale. Simply keeping them on battlefield scares enemies. \nSpecial Abilities: None.", false),
    // UPGRADED
    SKELETON_WARRIOR("SkeletonWarrior",6,6,6,5,Range.closed(1,3),1,"Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer. \nSpecial Abilities: None.", true),
    ZOMBIE("Zombie",5,5,20,4,Range.closed(2,3),2,"Numerous skeletons become even better, but running back to town and upgrading is a problem... It might be a good idea not to upgrade cursed temple at all. \nSpecial Abilities: 80% chance to disease enemies.", true),
    WRAITH("Wraith",7,7,18,7,Range.closed(3,5),3,"Will get to those shooters even faster! \nSpecial Abilities: Top wight of the stack regenerates all lost damage in the beginning of each round.", true),
    VAMPIRE_LORD("VampireLord",10,10,40,8,Range.closed(5,8),4,"Use them as main striking unit and you might end up with no losses! \nSpecial Abilities: No enemy retaliation; Resurrects members of their own stack by restoring health equal to the amount of damage they do to enemies.", true),
    POWER_LICH("PowerLich",13,10,40,4,Range.closed(11,15),5,"Now they last longer and are able to do more damage! A must for good necropolis army. \nSpecial Abilities: Death cloud range attack - damages living creatures on adjacent squares to target.", true,24),
    DREAD_KNIGHT("DreadKnight",18,18,120,9,Range.closed(15,30),6,"Propably the best lvl6 unit in the game! Double damage ability puts Dread Knights above! \nSpecial Abilities: Special: 20% chance to curse enemy; 20% chance to do double damage.", true),
    GHOST_DRAGON("GhostDragon",19,17,200,14,Range.closed(25,50),7,"Ageing ability makes ghost dragons as dangerous as other lvl7 creatures. \nSpecial Abilities: 20% chance to age enemy.", true),
    // CASTLE
    PICKMAN("Pickman",4,5,10,4,Range.closed(1,3),1,"Toughest lvl1 unit, but a bit slow. \nSpecial Abilities: None.",false),
    ARCHER("Archer",6,3,10,2,Range.closed(2,3),2,"Archer's upgrade is literally 2 times better. Upgrade them quickly. \nSpecial Abilities: None.",false,12),
    GRIFFIN("Griffin",8,8,25,6,Range.closed(3,6),3,"High in population, griffins become castle's main unit for the mid game. \nSpecial Abilities: 2 retaliations per turn.",false),
    SWORDSMAN("Swordsman",10,12,35,5,Range.closed(6,9),4,"High in population, griffins become castle's main unit for the mid game. \nSpecial Abilities: None.",false),
    MONK("Monk",12,7,30,3,Range.closed(10,12),5,"Good shooter, nice damage. \nSpecial Abilities: None. Bad buff: Gets half of damage he deals with melee attack.",false,12),
    CAVALIER("Cavalier",15,15,100,7,Range.closed(15,25),6,"Make sure that the path is as long as possible - they need some speed! \nSpecial Abilities: None.",false),
    ANGEL("Angel",20,20,200,12,Range.closed(50,50),7,"Nice combat ratings and great constant damage - no need to bless them. I think it's still too powerful. \nSpecial Abilities: None.",false),
    // UPGRADED
    HALBERDIER("Halberdier",6,5,10,5,Range.closed(2,3),1,"Now they are faster and do more damage. Will make a good defence for shooters. \nSpecial Abilities: None.",true),
    MARKSMAN("Marksman",6,3,10,3,Range.closed(2,3),2,"Awesome upgrade, but they still lack defence... \nSpecial Abilities: Double attack.",true,24),
    ROYAL_GRIFFIN("RoyalGriffin",9,9,25,6,Range.closed(3,6),3,"Send them right in the middle of the battlefield. Everyone who comes will get some. \nSpecial Abilities: Unlimited retaliations.",true),
    CRUSADER("Crusader",12,12,35,6,Range.closed(7,10),4,"Good upgrade, but still lacks speed. Seem undefeatable in large numbers. \nSpecial Abilities: Double attack.",true),
    ZEALOT("Zealot",12,10,30,4,Range.closed(10,12),5,"Zealots are skilled enough to use the same magic powers at very close range. Better defence too.  \nSpecial Abilities: None.",true,24),
    CHAMPION("Champion",16,16,100,9,Range.closed(20,25),6,"That's up to 45% extra damage possible! Champions also have better aiming skills. \nSpecial Abilities:  20% chance to do double damage.",true),
    ARCHANGEL("Archangel",30,20,250,18,Range.closed(50,50),7,"Best attack, defence and speed in a whole game! He is very fast! Possibly the best creature in the game. \nSpecial Abilities: None.",true);

    private String name;
    private int attack;
    private int armor;
    private int maxHP;
    private int moveRange;
    private Range<Integer> damage;
    private int tier;
    private String description;
    private boolean isUpgraded;
    private int shoots;


    CreatureStatistic(String aName, int aAttack, int aArmor, int aMaxHP, int mMoveRange, Range<Integer> aDamage, int aTier, String aDescription, boolean aIsUpgraded) {
        this.name = aName;
        this.maxHP = aMaxHP;
        this.armor = aArmor;
        this.attack = aAttack;
        this.moveRange = mMoveRange;
        this.damage = aDamage;
        this.tier = aTier;
        this.description = aDescription;
        this.isUpgraded = aIsUpgraded;
        this.shoots = 0;
    }

    CreatureStatistic(String aName, int aAttack, int aArmor, int aMaxHP, int mMoveRange, Range<Integer> aDamage, int aTier, String aDescription, boolean aIsUpgraded, int aShoots) {
        this(aName,aAttack,aArmor,aMaxHP,mMoveRange,aDamage,aTier,aDescription,aIsUpgraded);
        this.shoots = aShoots;
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

    @Override
    public boolean isUpgraded() {
        return isUpgraded;
    }

    @Override
    public int getShots() {
        return shoots;
    }
}
