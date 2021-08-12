package pl.sdk;

import pl.sdk.creatures.Creature;

public class DecreaserShotsOfCreature extends Creature {

    static void decrease(Creature aCreature, int aShotsToDecrease) {
        aCreature.setShots(aCreature.getShots()-aShotsToDecrease);
    }
}
