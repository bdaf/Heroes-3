package pl.sdk.creatures;

import com.google.common.collect.Range;

import java.util.List;

class Weakness {

    final private String name;
    final int attackToDecrease;
    final int defenseToDecrease;
    final double percentage;
    final int startDuration;
    int duration;

    private final Integer minDamage;
    private final Integer maxDamage;

    Weakness(int aAttackToDecrease, int aDefenseToDecrease, double aPercentage, int aDuration, String aName, Integer aMinDamage, Integer aMaxDamage) {
        name = aName;
        attackToDecrease = aAttackToDecrease;
        defenseToDecrease = aDefenseToDecrease;
        percentage = aPercentage;
        startDuration = duration = aDuration;
        minDamage = aMinDamage;
        maxDamage = aMaxDamage;
    }

    static Range<Integer> filterDamageWithWeaknesses(List<Weakness> aWeaknesses, Range<Integer> aRange) {
        for (Weakness weakness : aWeaknesses) {
            if (weakness.getMinDamage() != null && weakness.getMaxDamage() != null) {
                aRange = Range.closed(weakness.getMinDamage(), weakness.getMaxDamage());
            }
        }
        return aRange;
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

    Integer getMinDamage() {
        return minDamage;
    }

    Integer getMaxDamage() {
        return maxDamage;
    }


    static class Builder {
        private String name;
        private Integer attackToDecrease;
        private Integer defenseToDecrease;
        private Double percentage;
        private Integer duration;
        private Integer minDamage;
        private Integer maxDamage;

        Weakness build(){
            if(name == null) name = "Unnamed weakness";
            if(attackToDecrease == null) attackToDecrease = 0;
            if(defenseToDecrease == null) defenseToDecrease = 0;
            if(percentage == null) percentage = 0.5;
            if(duration == null) duration = 3;
            return new Weakness(attackToDecrease,defenseToDecrease,percentage,duration,name,minDamage,maxDamage);
        }

        Builder name(String aName){
            name = aName;
            return this;
        }

        Builder attackToDecrease(Integer aAttackToDecrease){
            attackToDecrease = aAttackToDecrease;
            return this;
        }

        Builder defenseToDecrease(Integer aDefenseToDecrease){
            defenseToDecrease = aDefenseToDecrease;
            return this;
        }

        Builder percentage(Double aPercentage){
            percentage = aPercentage;
            return this;
        }

        Builder duration(Integer aDuration){
            duration = aDuration;
            return this;
        }

        Builder minDamage(Integer aMinDamage){
            minDamage = aMinDamage;
            return this;
        }
        Builder maxDamage(Integer aMaxDamage){
            maxDamage = aMaxDamage;
            return this;
        }
    }

}
