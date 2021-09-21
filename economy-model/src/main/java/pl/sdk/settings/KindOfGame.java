package pl.sdk.settings;

public enum KindOfGame {

    QUICK_GAME(1,1,5000,0),
    TOURNAMENT(3,3,1000,1000),
    NOT_SETTLED(1,1,0,0);

    private final int roundsAmount;
    private final int turnsAmount;
    private final int factorOfGoldAfterRound;
    private final int startGold;


    KindOfGame(int aRoundsAmount, int aTurnsAmount, int aStartGold, int aFactorOfGoldAfterRound) {
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

    public int getFactorOfGoldAfterRound() {
        return factorOfGoldAfterRound;
    }

    public int getStartGold() {
        return startGold;
    }
}
