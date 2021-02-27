package pl.sdk.creatures;

import java.util.Random;

 class DefaultDamageCalculator extends AbstractDamageCalculator {

     DefaultDamageCalculator() {
        this(new Random());
    }
     DefaultDamageCalculator(Random aRandomizer) {
        super(aRandomizer);
    }
}
