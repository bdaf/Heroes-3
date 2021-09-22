package pl.sdk.creatures;

public class Weakness {

    final int attackToDecrease;
    final int defenseToDecrease;
    int duration;

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, int aDuration) {
        attackToDecrease = aAttackToDecrease;
        defenseToDecrease = aDefenseToDecrease;
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
}
