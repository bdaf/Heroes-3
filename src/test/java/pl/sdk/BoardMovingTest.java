package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardMovingTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = new Creature();
    }

    @Test
    void shouldMoveCreatureToSomePoint(){
        board.add(new Point(0,0), creature);
        //board.move(creature,2,3);
        board.move(0,0,2,3);

        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature, creatureFromBoard);
        assertNull(board.get(0,0));

    }
}
