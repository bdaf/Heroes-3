package pl.sdk.hero;

public enum Fraction {

    NECROPOLIS, CASTLE, INFERNO, NON_SETTLED;

    int points;

    public void increasePoints(int aAmount){
        points+=aAmount;
    }
    public int getPoints(){
        return points;
    }
}
