package pl.sdk.hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.EconomyEngine;
import pl.sdk.creatures.EconomyCastleFactory;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.hero.EconomyHero.Fraction.CASTLE;

public class SellingCreatureTest {

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
    void heroShouldSellArchersFromHisArmy(){
        assertThrows(IllegalStateException.class, () -> engine.sell(factory.Create(false,1,1)));

        engine.buy(factory.Create(false,1,1));
        engine.buy(factory.Create(false,1,2));
        engine.buy(factory.Create(false,1,3));

        engine.buy(factory.Create(false,2,1));
        engine.buy(factory.Create(false,2,2));
        engine.buy(factory.Create(false,2,3));

        assertEquals(40,engine.getActiveHero().getGold());

        assertTrue(engine.sell(factory.Create(false,2,2)));
        assertTrue(!engine.sell(factory.Create(false,2,2)));

        assertEquals(240,engine.getActiveHero().getGold());
        assertEquals(5,engine.getActiveHero().getHeroArmy().size());
    }
}
