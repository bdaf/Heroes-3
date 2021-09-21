package pl.sdk.settings;

public class EconomySettings {
    private final int roundsAmount;
    private final int turnsAmount;
    private final int factorOfGoldAfterRound;
    private final int startGold;

    public EconomySettings(int aRoundsAmount, int aTurnsAmount) {
        this(aRoundsAmount,aTurnsAmount, 0);
    }

    public EconomySettings(int aRoundsAmount, int aTurnsAmount, int aStartGold) {
        this(aRoundsAmount, aTurnsAmount, aStartGold, 0);
    }

    public EconomySettings(int aRoundsAmount, int aTurnsAmount, int aStartGold, int aFactorOfGoldAfterRound) {
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
