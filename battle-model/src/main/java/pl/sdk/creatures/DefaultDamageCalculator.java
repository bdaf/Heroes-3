package pl.sdk.creatures;

import java.util.Random;

public class DefaultDamageCalculator extends AbstractDamageCalculator {

    public DefaultDamageCalculator() {
        this(new Random());
    }
    public DefaultDamageCalculator(Random aRandomizer) {
        super(aRandomizer);
    }
}
