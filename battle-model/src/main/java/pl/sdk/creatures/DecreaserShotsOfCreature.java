package pl.sdk.creatures;

public class DecreaserShotsOfCreature {

    public static void decrease(Creature aCreature, int aShotsToDecrease) {
        aCreature.setShots(aCreature.getShots()-aShotsToDecrease);
    }
}
