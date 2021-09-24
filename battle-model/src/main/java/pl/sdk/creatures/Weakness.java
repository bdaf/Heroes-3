package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.util.List;
import java.util.Objects;

public class Weakness {

    private String name;
    int attackToDecrease;
    int defenseToDecrease;
    double percentage;
    int startDuration;
    int duration;

    Integer minDamageToDecrease;
    Integer maxDamageToDecrease;

    Weakness() {}

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration, String aName, Integer aMinDamageToDecrease, Integer aMaxDamageToDecrease) {
        name = aName;
        attackToDecrease = aAttackToDecrease;
        defenseToDecrease = aDefenseToDecrease;
        percentage = aPercentage;
        startDuration = duration = aDuration;
        minDamageToDecrease = aMinDamageToDecrease;
        maxDamageToDecrease = aMaxDamageToDecrease;
    }

    void setWeak(Creature aCreature) {
    }

    int getAttackToDecrease() {
        return attackToDecrease;
    }

    int getDefenseToDecrease() {
        return defenseToDecrease;
    }

    public int getDuration() {
        return duration;
    }

    double getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }

    void restartDuration() {
        duration = startDuration;
    }

    Integer getMinDamageToDecrease() {
        return minDamageToDecrease;
    }

    Integer getMaxDamageToDecrease() {
        return maxDamageToDecrease;
    }

    protected void setDamageToDecrease(int aMinDamageToDecrease, int aMaxDamageToDecrease) {
        minDamageToDecrease = aMinDamageToDecrease;
        maxDamageToDecrease = aMaxDamageToDecrease;
    }

    static Range<Integer> filterDamageWithWeaknesses(List<Weakness> aWeaknesses, Range<Integer> aRange) {
        for (Weakness weakness : aWeaknesses) {
            if (weakness.getMinDamageToDecrease() != null && weakness.getMaxDamageToDecrease() != null) {
                aRange = Range.closed(aRange.lowerEndpoint() - weakness.getMinDamageToDecrease(),
                        aRange.upperEndpoint() - weakness.getMaxDamageToDecrease());
            }
        }
        return aRange;
    }

    static Weakness copyOf(Weakness aWeakness) {
        return new Builder()
                .attackToDecrease(aWeakness.getAttackToDecrease())
                .defenseToDecrease(aWeakness.getDefenseToDecrease())
                .percentage(aWeakness.getPercentage())
                .duration(aWeakness.getDuration())
                .name(aWeakness.getName())
                .minDamage(aWeakness.getMinDamageToDecrease())
                .maxDamage(aWeakness.getMaxDamageToDecrease())
                .build();
    }

    static void addWeakness(List<Weakness> aWeaknesses, Weakness aWeakness) {
        for (Weakness weakness : aWeaknesses) {
            if (weakness.equals(aWeakness)) {
                weakness.restartDuration();
                return;
            }
        }
        aWeaknesses.add(aWeakness);
    }


    @Override
    public boolean equals(Object aO) {
        if (this == aO) return true;
        if (!(aO instanceof Weakness)) return false;
        Weakness weakness = (Weakness) aO;
        return getAttackToDecrease() == weakness.getAttackToDecrease() &&
                getDefenseToDecrease() == weakness.getDefenseToDecrease() &&
                Double.compare(weakness.getPercentage(), getPercentage()) == 0 &&
                startDuration == weakness.startDuration &&
                getName().equals(weakness.getName()) &&
                Objects.equals(getMinDamageToDecrease(), weakness.getMinDamageToDecrease()) &&
                Objects.equals(getMaxDamageToDecrease(), weakness.getMaxDamageToDecrease());
    }

    static class Builder {
        private String name;
        private Integer attackToDecrease;
        private Integer defenseToDecrease;
        private Double percentage;
        private Integer duration;
        private Integer minDamage;
        private Integer maxDamage;

        Weakness build() {
            if (name == null) name = "Unnamed weakness";
            if (attackToDecrease == null) attackToDecrease = 0;
            if (defenseToDecrease == null) defenseToDecrease = 0;
            if (percentage == null) percentage = 0.5;
            if (duration == null) duration = 3;
            return new Weakness(attackToDecrease, defenseToDecrease, percentage, duration, name, minDamage, maxDamage);
        }

        Builder name(String aName) {
            name = aName;
            return this;
        }

        Builder attackToDecrease(Integer aAttackToDecrease) {
            attackToDecrease = aAttackToDecrease;
            return this;
        }

        Builder defenseToDecrease(Integer aDefenseToDecrease) {
            defenseToDecrease = aDefenseToDecrease;
            return this;
        }

        Builder percentage(Double aPercentage) {
            percentage = aPercentage;
            return this;
        }

        Builder duration(Integer aDuration) {
            duration = aDuration;
            return this;
        }

        Builder minDamage(Integer aMinDamage) {
            minDamage = aMinDamage;
            return this;
        }

        Builder maxDamage(Integer aMaxDamage) {
            maxDamage = aMaxDamage;
            return this;
        }
    }

}
