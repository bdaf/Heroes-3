package pl.sdk.hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.EconomyCastleFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EconomyHeroAndCreaturesTest {

    private EconomyHero hero;

    @BeforeEach
    void init(){
        hero = new EconomyHero(EconomyHero.Fraction.CASTLE,1000);
    }

    @Test
    void throwExceptionWhenAttemptingToBuySeventhCreature(){
        EconomyCastleFactory factory = new EconomyCastleFactory();
        for (int i = 0; i < 7; i++)
            hero.addCreature(factory.Create(true,1,1));
        assertThrows(IllegalStateException.class, () -> hero.addCreature(factory.Create(true,1,1)));
    }

    @Test
    void throwExceptionWhenAttemptingToSubtractGoldToMinusValue(){
        assertThrows(IllegalStateException.class, () -> hero.subtractGold(1001));
    }
}
