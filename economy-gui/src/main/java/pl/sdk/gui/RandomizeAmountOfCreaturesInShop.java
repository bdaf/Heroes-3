package pl.sdk.gui;

import pl.sdk.hero.EconomyHero;

import java.util.Random;

import static pl.sdk.creatures.Factory.ERROR_MSG;

public class RandomizeAmountOfCreaturesInShop {

    public static final String INCORRECT_HERO_FRACTION = "Incorrect hero fraction!";
    private final int necropolis[];
    private final int castle[];

    RandomizeAmountOfCreaturesInShop() {
        necropolis = new int[14];
        castle = new int[14];
        setDefaultValueForFractions();
        for (int i = 0; i < 14; i++) {
            Float number = (new Random().nextFloat() * 3 + 2) / 5;
            if(necropolis[i]==1) necropolis[i] = new Random().nextInt(2);
            else necropolis[i] = (int) (Math.ceil(number * necropolis[i]));
            if(castle[i]==1) castle[i] = new Random().nextInt(2);
            else castle[i] = (int) (Math.ceil(number * castle[i]));
        }
    }

    private void setDefaultValueForFractions() {
        //Castle
        // Not upgraded
        castle[0] = 14;
        castle[1] = 9;
        castle[2] = 7;
        castle[3] = 4;
        castle[4] = 3;
        castle[5] = 2;
        castle[6] = 1;
        // Upgraded
        castle[7] = 14;
        castle[8] = 9;
        castle[9] = 7;
        castle[10] = 4;
        castle[11] = 3;
        castle[12] = 2;
        castle[13] = 1;

        //Necropolis
        // Not upgraded
        necropolis[0] = 12;
        necropolis[1] = 8;
        necropolis[2] = 7;
        necropolis[3] = 4;
        necropolis[4] = 3;
        necropolis[5] = 2;
        necropolis[6] = 1;
        // Upgraded
        necropolis[7] = 12;
        necropolis[8] = 8;
        necropolis[9] = 7;
        necropolis[10] = 4;
        necropolis[11] = 3;
        necropolis[12] = 2;
        necropolis[13] = 1;
    }

    public void setAmountOfTier(int aTier, EconomyHero.Fraction aFraction, int aAmount, boolean aIsUpgraded) {
        if(aIsUpgraded) aTier+=7;
        checkRangeOfTier(aTier);
        if (aFraction == EconomyHero.Fraction.NECROPOLIS) necropolis[aTier - 1] = aAmount;
        else if (aFraction == EconomyHero.Fraction.CASTLE) castle[aTier - 1] = aAmount;
        else throw new IllegalArgumentException(INCORRECT_HERO_FRACTION);
    }

    public int getAmountOfTier(int aTier, EconomyHero.Fraction aFraction, boolean aIsUpgraded) {
        if(aIsUpgraded) aTier+=7;
        checkRangeOfTier(aTier);
        if (aFraction == EconomyHero.Fraction.NECROPOLIS) return necropolis[aTier - 1];
        else if (aFraction == EconomyHero.Fraction.CASTLE) return castle[aTier - 1];
        else throw new IllegalArgumentException(INCORRECT_HERO_FRACTION);
    }

    private void checkRangeOfTier(int aTier) {
        if(aTier < 1 || aTier > 14) throw new IllegalArgumentException(ERROR_MSG);
    }

}
