package pl.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    void shouldAddCreature(){
        Board board = new Board();
        Creature creature = new Creature();

        board.add(0,0,creature);
        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }
}
