package pl.sdk.creatures;

public class Weakness {

    final int attackToDecrease;
    final int defenseToDecrease;
    final double percentage;
    int duration;

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration) {
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
}
