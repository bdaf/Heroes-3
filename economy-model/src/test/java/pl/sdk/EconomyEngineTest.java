package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.EconomyCastleFactory;
import pl.sdk.creatures.EconomyNecropolisFactory;
import pl.sdk.hero.EconomyHero;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.ModeOfGame;

import static org.junit.jupiter.api.Assertions.*;

public class EconomyEngineTest {

    private EconomyHero castleHero;
    private EconomyHero necropolisHero;
    private EconomyCastleFactory castle;
    private EconomyNecropolisFactory necropolis;
    private EconomyEngine engine;

    @BeforeEach
    void init() {
        ModeOfGame mode = ModeOfGame.TOURNAMENT;
        castleHero = new EconomyHero(Fraction.CASTLE, mode.getStartGold());
        necropolisHero = new EconomyHero(Fraction.NECROPOLIS, mode.getStartGold());
        engine = new EconomyEngine(castleHero, necropolisHero, mode);
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
        assertEquals(1, engine.getTurnNumber());
        engine.pass();
        assertEquals(1, engine.getTurnNumber());
        engine.pass();
        assertEquals(2, engine.getTurnNumber());
        engine.pass();
        engine.pass();
        assertEquals(3, engine.getTurnNumber());
    }

    @Test
    void shouldSetCreatureToProperHero() {
        engine.buy(castle.Create(true, 1, 1));
        assertEquals(925, castleHero.getGold());
        assertEquals(1000, necropolisHero.getGold());
        engine.pass();
        engine.buy(necropolis.Create(true, 1, 1));
        assertEquals(925, castleHero.getGold());
        assertEquals(930, necropolisHero.getGold());
    }

    @Test
    void shouldCountRoundsAndToursCorrectly() {
        assertEquals(1, engine.getTurnNumber());
        assertEquals(1, engine.getRoundNumber());
        engine.pass();
        assertEquals(1, engine.getTurnNumber());
        assertEquals(1, engine.getRoundNumber());
        engine.pass();
        assertEquals(2, engine.getTurnNumber());
        assertEquals(1, engine.getRoundNumber());
        engine.pass();
        engine.pass();
        assertEquals(3, engine.getTurnNumber());
        assertEquals(1, engine.getRoundNumber());
        engine.buy(castle.Create(true, 1, 1));
        engine.pass();
        engine.buy(necropolis.Create(true, 1, 1));
        engine.pass();
        assertEquals(1, engine.getTurnNumber());
        assertEquals(2, engine.getRoundNumber());
    }

    @Test
    void shouldCountGoldInRoundsAndTours() {
        int expectedAmount = 1000;
        assertEquals(expectedAmount, engine.getActiveHero().getGold());
        engine.pass();
        assertEquals(expectedAmount, engine.getActiveHero().getGold());
        engine.pass();
        expectedAmount += engine.getFactorOfGoldAfterRounds()*2;
        assertEquals(expectedAmount, engine.getLeftHero().getGold());
        assertEquals(expectedAmount, engine.getRightHero().getGold());
        engine.pass();
        engine.pass();
        expectedAmount += engine.getFactorOfGoldAfterRounds()*3;
        assertEquals(expectedAmount, engine.getLeftHero().getGold());
        assertEquals(expectedAmount, engine.getRightHero().getGold());
        engine.buy(castle.Create(true, 1, 1));
        engine.pass();
        engine.buy(necropolis.Create(true, 1, 1));
        engine.pass();
        assertEquals(expectedAmount - 75, engine.getLeftHero().getGold());
        assertEquals(expectedAmount - 70, engine.getRightHero().getGold());
    }

    @Test
    void shouldBeVisibleWhetherIsThatRoundTheLastBeforeBattle() {
        assertFalse(engine.isLastChooseBeforeBattle());
        engine.pass();
        assertFalse(engine.isLastChooseBeforeBattle());

        engine.pass();
        assertFalse(engine.isLastChooseBeforeBattle());
        engine.pass();
        engine.pass();
        assertFalse(engine.isLastChooseBeforeBattle());
        engine.buy(castle.Create(true, 1, 1));
        engine.pass();
        assertTrue(engine.isLastChooseBeforeBattle());
        engine.buy(necropolis.Create(true, 1, 1));
        engine.pass();
        assertFalse(engine.isLastChooseBeforeBattle());
    }

    @Test
    void shouldThrowExceptionWhenTryToGoToBattleWithoutCreaturesInAnyOfArmy() {
        // First round
        engine.buy(castle.Create(true, 1, 1));
        assertEquals(925, castleHero.getGold());
        assertEquals(1000, necropolisHero.getGold());
        engine.pass();
        engine.pass();
        // Second round
        engine.pass();
        engine.pass();
        // Third round
        engine.pass();
        assertThrows(IllegalStateException.class, () -> engine.pass());
    }

    @Test
    void shouldNotThrowExceptionWhenTryToGoToBattleWithCreaturesInEachTeam() {
        // First round
        engine.buy(castle.Create(true, 1, 1));
        assertEquals(925, castleHero.getGold());
        assertEquals(1000, necropolisHero.getGold());
        engine.pass();

        engine.buy(necropolis.Create(true, 1, 1));
        assertEquals(925, castleHero.getGold());
        assertEquals(930, necropolisHero.getGold());
        engine.pass();
        // Second round
        engine.pass();
        engine.pass();
        // Third round
        engine.pass();
        engine.pass();
    }
}
