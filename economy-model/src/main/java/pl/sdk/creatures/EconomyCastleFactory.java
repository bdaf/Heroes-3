package pl.sdk.creatures;

import pl.sdk.hero.Fraction;

public class EconomyCastleFactory extends EconomyFactory{

    public static final String ERROR_MSG = "Incorrect number of Tier, it should be from 1 to 7";

    public EconomyCastleFactory() {
        fraction = Fraction.CASTLE;
    }

    public EconomyCreature Create(boolean aIsUpgraded, int aTier, int amount) {
        if (aIsUpgraded) {
            switch (aTier) {
                case 1:
                    return new EconomyCreature(CreatureStatistic.HALBERDIER,amount,75);
                case 2:
                    return new EconomyCreature(CreatureStatistic.MARKSMAN,amount,150);
                case 3:
                    return new EconomyCreature(CreatureStatistic.ROYAL_GRIFFIN,amount,240);
                case 4:
                    return new EconomyCreature(CreatureStatistic.CRUSADER,amount,400);
                case 5:
                    return new EconomyCreature(CreatureStatistic.ZEALOT,amount,450);
                case 6:
                    return new EconomyCreature(CreatureStatistic.CHAMPION,amount,1200);
                case 7:
                    return new EconomyCreature(CreatureStatistic.ARCHANGEL,amount,5000);
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        } else {
            switch (aTier) {
                case 1:
                    return new EconomyCreature(CreatureStatistic.PICKMAN,amount,60);
                case 2:
                    return new EconomyCreature(CreatureStatistic.ARCHER,amount,100);
                case 3:
                    return new EconomyCreature(CreatureStatistic.GRIFFIN,amount,200);
                case 4:
                    return new EconomyCreature(CreatureStatistic.SWORDSMAN,amount,300);
                case 5:
                    return new EconomyCreature(CreatureStatistic.MONK,amount,400);
                case 6:
                    return new EconomyCreature(CreatureStatistic.CAVALIER,amount,1000);
                case 7:
                    return new EconomyCreature(CreatureStatistic.ANGEL,amount,3000);
                default:
                    throw new IllegalArgumentException(ERROR_MSG);
            }
        }
    }
}
