package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.creatures.EconomyNecropolisFactory;
import pl.sdk.hero.EconomyHero;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EconomyEngineTest {

    private EconomyHero castleHero;
    private EconomyHero necropolisHero;
    private EconomyCastleFactory castle;
    private EconomyNecropolisFactory necropolis;
    private EconomyEngine engine;

    @BeforeEach
    void init() {
        castleHero = new EconomyHero(EconomyHero.Fraction.CASTLE, 1000);
        necropolisHero = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, 1000);
        engine = new EconomyEngine(castleHero, necropolisHero);
        castle = new EconomyCastleFactory();
        necropolis = new EconomyNecropolisFactory();
    }

    @Test
    void shouldPassToNecropolisHero() {
        assertEquals(castleHero, engine.getActiveHero());
        engine.pass();
        assertEquals(necropolisHero, engine.getActiveHero());
    }

    @Test
    void shouldCountRoundsCorrectly() {
        assertEquals(1, engine.getRoundNumber());
        engine.pass();
        assertEquals(1, engine.getRoundNumber());
        engine.pass();
        assertEquals(2, engine.getRoundNumber());
    }

    @Test
    void shouldSetCreatureToProperHero() {
        engine.buy(castle.Create(true,1,1));
        assertEquals(925,castleHero.getGold());
        assertEquals(1000,necropolisHero.getGold());
        engine.pass();
        engine.buy(necropolis.Create(true,1,1));
        assertEquals(925,castleHero.getGold());
        assertEquals(930,necropolisHero.getGold());
    }
}
