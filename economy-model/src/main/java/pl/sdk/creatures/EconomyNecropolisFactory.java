package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class EconomyNecropolisFactory extends EconomyFactory {
    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    public EconomyNecropolisFactory() {
        fraction = Fraction.NECROPOLIS;
    }

    public EconomyCreature Create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new EconomyCreature(CreatureStatistic.SKELETON_WARRIOR,amount,70);
                case 2:
                    return new EconomyCreature(CreatureStatistic.ZOMBIE,amount,125);
                case 3:
                    return new EconomyCreature(CreatureStatistic.WRAITH,amount,230);
                case 4:
                    return new EconomyCreature(CreatureStatistic.VAMPIRE_LORD,amount,500);
                case 5:
                    return new EconomyCreature(CreatureStatistic.POWER_LICH,amount,600);
                case 6:
                    return new EconomyCreature(CreatureStatistic.DREAD_KNIGHT,amount,1500);
                case 7:
                    return new EconomyCreature(CreatureStatistic.GHOST_DRAGON,amount,3000);
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new EconomyCreature(CreatureStatistic.SKELETON,amount,60);
                case 2:
                    return new EconomyCreature(CreatureStatistic.WALKING_DEAD,amount,100);
                case 3:
                    return new EconomyCreature(CreatureStatistic.WIGHT,amount,200);
                case 4:
                    return new EconomyCreature(CreatureStatistic.VAMPIRE,amount,360);
                case 5:
                    return new EconomyCreature(CreatureStatistic.LICH,amount,550);
                case 6:
                    return new EconomyCreature(CreatureStatistic.BLACK_KNIGHT,amount,1200);
                case 7:
                    return new EconomyCreature(CreatureStatistic.BONE_DRAGON,amount,1800);
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}

