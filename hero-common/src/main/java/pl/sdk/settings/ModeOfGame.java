package pl.sdk.settings;

import pl.sdk.hero.Fraction;

public enum ModeOfGame {

    QUICK_GAME(1,1,5000,0),
    TOURNAMENT(3,3,1000,1000),
    NOT_SETTLED(1,1,0,0);

    private final int roundsAmount;
    private final int turnsAmount;
    private final int factorOfGoldAfterRound;
    private final int startGold;


    ModeOfGame(int aRoundsAmount, int aTurnsAmount, int aStartGold, int aFactorOfGoldAfterRound) {
        roundsAmount = aRoundsAmount;
        turnsAmount = aTurnsAmount;
        startGold = aStartGold;
        factorOfGoldAfterRound = aFactorOfGoldAfterRound;
    }

    public int getRoundsAmount() {
        return roundsAmount;
    }

    public int getTurnsAmount() {
        return turnsAmount;
    }

    public int getFactorOfGoldAfterRounds() {
        return factorOfGoldAfterRound;
    }

    public int getStartGold() {
        return startGold;
    }

    public boolean ifFractionWon(Fraction aFraction){
        if(aFraction.getPoints() >= getRoundsAmount()/2+1) return true;
        else return false;
    }
    public boolean ifAnyFractionWon(Fraction aF0, Fraction aF1){
        if(ifFractionWon(aF0) || ifFractionWon(aF1))
            return true;
        return false;
    }
}
