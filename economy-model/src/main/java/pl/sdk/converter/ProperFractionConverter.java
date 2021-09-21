package pl.sdk.converter;

import pl.sdk.creatures.*;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;

public class ProperFractionConverter {

    public static Factory getProperFactoryBasedOnFraction(Fraction aFraction) {
        Factory factory;
        if (aFraction == Fraction.NECROPOLIS)
            factory = new NecropolisFactory();
        else if (aFraction == Fraction.CASTLE)
            factory = new CastleFactory();
        else
            throw new NullPointerException("Factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }

    public static EconomyFactory getProperEconomyFactoryBasedOnFraction(Fraction aFraction) {
        EconomyFactory factory;
        if (aFraction == Fraction.NECROPOLIS)
            factory = new EconomyNecropolisFactory();
        else if (aFraction == Fraction.CASTLE)
            factory = new EconomyCastleFactory();
        else
            throw new NullPointerException("Economy factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }
}
