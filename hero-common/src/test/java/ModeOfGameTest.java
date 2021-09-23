import org.junit.jupiter.api.Test;
import pl.sdk.hero.Fraction;
import pl.sdk.settings.ModeOfGame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModeOfGameTest {
    @Test
    void NecropolisShouldWinAfter1WinInQuickGame(){
        ModeOfGame modeOfGame = ModeOfGame.QUICK_GAME;
        Fraction necropolis = Fraction.NECROPOLIS;
        Fraction castle = Fraction.CASTLE;

        necropolis.increasePoints(1);

        assertTrue(modeOfGame.ifAnyFractionWon(necropolis,castle));
        assertFalse(modeOfGame.ifFractionWon(castle));
        assertTrue(modeOfGame.ifFractionWon(necropolis));
    }

    @Test
    void CastleShouldWinAfter2WinsInTournament(){
        ModeOfGame modeOfGame = ModeOfGame.TOURNAMENT;
        Fraction necropolis = Fraction.NECROPOLIS;
        Fraction castle = Fraction.CASTLE;

        castle.increasePoints(1); // After 1 win

        assertFalse(modeOfGame.ifAnyFractionWon(necropolis,castle));
        assertFalse(modeOfGame.ifFractionWon(castle));
        assertFalse(modeOfGame.ifFractionWon(necropolis));

        castle.increasePoints(1); // After 2 wins

        assertTrue(modeOfGame.ifAnyFractionWon(necropolis,castle));
        assertTrue(modeOfGame.ifFractionWon(castle));
        assertFalse(modeOfGame.ifFractionWon(necropolis));
    }

}
