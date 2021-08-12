package pl.sdk.converter;

import pl.sdk.creatures.*;
import pl.sdk.hero.EconomyHero;

public class ProperFractionConverter {

    public static Factory getProperFactoryForFractionOf(EconomyHero aEcoHero) {
        Factory factory;
        if (aEcoHero.getFraction() == EconomyHero.Fraction.NECROPOLIS)
            factory = new NecropolisFactory();
        else if (aEcoHero.getFraction() == EconomyHero.Fraction.CASTLE)
            factory = new CastleFactory();
        else
            throw new NullPointerException("Factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }

    public static EconomyFactory getProperEconomyFactoryForFractionOf(EconomyHero aEcoHero) {
        EconomyFactory factory;
        if (aEcoHero.getFraction() == EconomyHero.Fraction.NECROPOLIS)
            factory = new EconomyNecropolisFactory();
        else if (aEcoHero.getFraction() == EconomyHero.Fraction.CASTLE)
            factory = new EconomyCastleFactory();
        else
            throw new NullPointerException("Economy factory which is used to convert economy creatures to creatures is null!");
        return factory;
    }
}
