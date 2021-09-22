import org.junit.jupiter.api.Test;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.KindOfGame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KindOfGameTest {
    @Test
    void NecropolisShouldWinAfter1WinInQuickGame(){
        KindOfGame kindOfGame = KindOfGame.QUICK_GAME;
        Fraction necropolis = Fraction.NECROPOLIS;
        Fraction castle = Fraction.CASTLE;

        necropolis.increasePoints(1);

        assertTrue(kindOfGame.ifAnyFractionWon(necropolis,castle));
        assertFalse(kindOfGame.ifFractionWon(castle));
        assertTrue(kindOfGame.ifFractionWon(necropolis));
    }

    @Test
    void CastleShouldWinAfter2WinsInTournament(){
        KindOfGame kindOfGame = KindOfGame.TOURNAMENT;
        Fraction necropolis = Fraction.NECROPOLIS;
        Fraction castle = Fraction.CASTLE;

        castle.increasePoints(1); // After 1 win

        assertFalse(kindOfGame.ifAnyFractionWon(necropolis,castle));
        assertFalse(kindOfGame.ifFractionWon(castle));
        assertFalse(kindOfGame.ifFractionWon(necropolis));

        castle.increasePoints(1); // After 2 wins

        assertTrue(kindOfGame.ifAnyFractionWon(necropolis,castle));
        assertTrue(kindOfGame.ifFractionWon(castle));
        assertFalse(kindOfGame.ifFractionWon(necropolis));
    }

}
