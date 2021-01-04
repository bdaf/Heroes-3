package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardMovingTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = new Creature();
        board.add(new Point(0,0), creature);
    }



    @Test
    void shouldMoveCreatureToSomePoint(){
        board.move(new Point(0,0), new Point(2,3));

        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldMoveCreatureToSomePointWhenArgumentIsCreature(){
        board.move(creature, new Point(2,3));

        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature, creatureFromBoard);

    }

    @Test
    void shouldThrowExceptionWhenCreatureIsTryingToGoToNotEmptyField(){

        Creature creature2 = new Creature();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () ->board.move(new Point(0,0), new Point(2,3)));
        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature2, creatureFromBoard);

    }

    @Test
    void shouldThrowExceptionWhenCreatureIsTryingToBeAddedToNotEmptyField(){

        Creature creature2 = new Creature();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(2,3),new Creature()));
        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature2, creatureFromBoard);

    }


    @Test //2 the shortest tests
    void shouldNotMoveCreatureToSomePointWhenThereIsNoCreature(){
        assertThrows(IllegalArgumentException.class, () -> board.move(new Point(0,1), new Point(2,3)));
    }

    @Test
    void shouldThrowExceptionWhenTryingToAddCreatureOutsideTheMap(){
        assertThrows(IllegalArgumentException.class, ()->board.add(new Point(20,16),creature));
    }

    @Test
    void shouldBeAbleToGoToFieldWhenHisMoveIsAttemptingForThis(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 1);
        board.add(new Point(5,5), creature1);
        assertTrue(board.canMove(creature1,5,6));
        assertTrue(board.canMove(creature1,6,5));
        assertTrue(board.canMove(creature1,5,4));
        assertTrue(board.canMove(creature1,4,5));
    }

    @Test
    void shouldNotBeAbleToGoToFieldWhenHisMoveIsNotAttemptingForThis(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 1);
        board.add(new Point(5,5), creature1);

        assertFalse(board.canMove(creature1,6,6));
        assertFalse(board.canMove(creature1,4,4));
    }

    @Test
    void cannotGoWhenFieldIsTaken(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 10);
        board.add(new Point(5,5),creature1);

        assertFalse(board.canMove(creature1,0,0));

    }
}
