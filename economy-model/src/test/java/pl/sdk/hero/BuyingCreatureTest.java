package pl.sdk.hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.EconomyEngine;
import pl.sdk.creatures.EconomyCastleFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.sdk.hero.EconomyHero.Fraction.CASTLE;

public class BuyingCreatureTest {

    private EconomyEngine engine;
    private EconomyCastleFactory factory;
    private EconomyHero leftHero;
    private EconomyHero rightHero;

    @BeforeEach
    void init() {
        factory = new EconomyCastleFactory();
        leftHero = new EconomyHero(CASTLE,1000);
        rightHero = new EconomyHero(CASTLE,1000);
        engine = new EconomyEngine(leftHero,rightHero);
    }

    @Test
    void heroShouldBuyOneUnitInOneStackFor60Gold() {
        engine.buy(factory.Create(false,1,1));
        assertEquals(940,leftHero.getGold());
    }

    @Test
    void heroShouldBuyTwoUnitsInOneStackFor120Gold() {
        engine.buy(factory.Create(false,1,2));
        assertEquals(880,leftHero.getGold());
    }

    @Test
    void heroShouldBuyFewUnitsInTwoStacksFor600Gold() {
        engine.buy(factory.Create(false,3,2));
        engine.buy(factory.Create(false,2,2));
        assertEquals(400,leftHero.getGold());
    }
    @Test
    void shouldThrowExceptionBecause7FieldsForStacksAreBusyAndEightsStackCannotBeBought() {
        for (int i = 0; i < 7; i++) {
            engine.buy(factory.Create(false,1,1));
        }

        assertThrows(IllegalStateException.class, () -> engine.buy(factory.Create(false,1,1)));
        assertEquals(580,leftHero.getGold());
        assertEquals(7,leftHero.getHeroArmy().size());
    }
    @Test
    void shouldThrowExceptionBecauseOfMinusValueOfHeroGold() {
        assertThrows(IllegalStateException.class, () ->engine.buy(factory.Create(false,3,10)));
        assertEquals(1000, leftHero.getGold());
        assertEquals(0, leftHero.getHeroArmy().size());
    }
}
