package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.util.List;

public class Weakness {

    private String name;
    int attackToDecrease;
    int defenseToDecrease;
    double percentage;
    int startDuration;
    int duration;

    Integer minDamageToDecrease;
    Integer maxDamageToDecrease;
    Integer maxHpToDecrease;

    Weakness() {}

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration, String aName, Integer aMinDamageToDecrease, Integer aMaxDamageToDecrease,
    Integer aMaxHpToDecrease) {
        name = aName;
        attackToDecrease = aAttackToDecrease;
        defenseToDecrease = aDefenseToDecrease;
        percentage = aPercentage;
        startDuration = duration = aDuration;
        minDamageToDecrease = aMinDamageToDecrease;
        maxDamageToDecrease = aMaxDamageToDecrease;
        maxHpToDecrease = aMaxHpToDecrease;
    }

    static boolean addWeakness(List<Weakness> aWeaknesses, Weakness aWeakness) {
        for (Weakness weakness : aWeaknesses) {
            if (weakness.equals(aWeakness)) {
                weakness.restartDuration();
                return false;
            }
        }
        aWeaknesses.add(aWeakness);
        return true;
    }

    static Integer maxHpFilterWithWeaknesses(List<Weakness> aWeaknesses, Integer aMaxHp) {
        for (Weakness weakness : aWeaknesses) {
            if (weakness.getMaxHpToDecrease() != null) {
                aMaxHp = Integer.max(1,aMaxHp-weakness.getMaxHpToDecrease());
            }
        }
        return aMaxHp;
    }

    static Range<Integer> damageFilterWithWeaknesses(List<Weakness> aWeaknesses, Range<Integer> aRange) {
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
                .duration(aWeakness.getStartDuration())
                .name(aWeakness.getName())
                .minDamage(aWeakness.getMinDamageToDecrease())
                .maxDamage(aWeakness.getMaxDamageToDecrease())
                .maxHp(aWeakness.getMaxHpToDecrease())
                .build();
    }

    void setWeak(Creature aCreature, Integer aDealtDmg) {}

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

    int getStartDuration() {
        return startDuration;
    }

    Integer getMinDamageToDecrease() {
        return minDamageToDecrease;
    }

    Integer getMaxDamageToDecrease() {
        return maxDamageToDecrease;
    }

    Integer getMaxHpToDecrease() { return maxHpToDecrease; }

    void setDamageToDecrease(int aMinDamageToDecrease, int aMaxDamageToDecrease) {
        minDamageToDecrease = aMinDamageToDecrease;
        maxDamageToDecrease = aMaxDamageToDecrease;
    }

    void setMaxHpToDecrease(int aMaxHpToDecrease){
        maxHpToDecrease = aMaxHpToDecrease;
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
                getName().equals(weakness.getName());
    }

    static class Builder {
        private String name;
        private Integer attackToDecrease;
        private Integer defenseToDecrease;
        private Double percentage;
        private Integer duration;
        private Integer minDamageToDecrease;
        private Integer maxDamageToDecrease;
        private Integer maxHpToDecrease;

        Weakness build() {
            if (name == null) name = "Unnamed weakness";
            if (attackToDecrease == null) attackToDecrease = 0;
            if (defenseToDecrease == null) defenseToDecrease = 0;
            if (percentage == null) percentage = 0.5;
            if (duration == null) duration = 3;
            return new Weakness(attackToDecrease, defenseToDecrease, percentage, duration, name, minDamageToDecrease, maxDamageToDecrease, maxHpToDecrease);
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
            minDamageToDecrease = aMinDamage;
            return this;
        }

        Builder maxDamage(Integer aMaxDamage) {
            maxDamageToDecrease = aMaxDamage;
            return this;
        }

        Builder maxHp(Integer aMaxHpToDecrease) {
            maxHpToDecrease = aMaxHpToDecrease;
            return this;
        }
    }

}
