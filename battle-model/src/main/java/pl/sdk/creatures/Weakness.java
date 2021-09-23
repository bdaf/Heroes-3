package pl.sdk.creatures;

public class Weakness {

    final private String name;
    final int attackToDecrease;
    final int defenseToDecrease;
    final double percentage;
    int duration;

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration) {
        this(aAttackToDecrease,aDefenseToDecrease,aPercentage,aDuration,"Unnamed weakness");
    }

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration, String aName) {
        name = aName;
        attackToDecrease = aAttackToDecrease;
        defenseToDecrease = aDefenseToDecrease;
        percentage = aPercentage;
        duration = aDuration;
    }

    int getAttackToDecrease() {
        return attackToDecrease;
    }

    int getDefenseToDecrease() {
        return defenseToDecrease;
    }

    int getDuration() {
        return duration;
    }

    double getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }
}
