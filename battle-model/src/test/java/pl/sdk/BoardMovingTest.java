package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.creatures.NecropolisFactory.CreateCreatureDefaultForTests;

public class BoardMovingTest {

    private static final int NOT_IMPORTANT = 2;
    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = NecropolisFactory.CreateCreatureDefaultForTests();
        board.add(new Point(0,0), creature);
    }



    @Test
    void shouldMoveCreatureToSomePoint(){
        board.moveAndReturnDistance(new Point(0,0), new Point(2,3));

        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldMoveCreatureToSomePointWhenArgumentIsCreature(){
        board.moveAndReturnDistance(creature, new Point(2,3));

        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature, creatureFromBoard);

    }

    @Test
    void shouldThrowExceptionWhenCreatureIsTryingToGoToNotEmptyField(){

        Creature creature2 = NecropolisFactory.CreateCreatureDefaultForTests();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () ->board.moveAndReturnDistance(new Point(0,0), new Point(2,3)));
        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature2, creatureFromBoard);
    }

    @Test
    void shouldThrowExceptionWhenCreatureIsTryingToBeAddedToNotEmptyField(){

        Creature creature2 = NecropolisFactory.CreateCreatureDefaultForTests();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(2,3), NecropolisFactory.CreateCreatureDefaultForTests()));
        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature2, creatureFromBoard);

    }


    @Test //2 the shortest tests
    void shouldNotMoveCreatureToSomePointWhenThereIsNoCreature(){
        assertThrows(IllegalArgumentException.class, () -> board.moveAndReturnDistance(new Point(0,1), new Point(2,3)));
    }

    @Test
    void shouldThrowExceptionWhenTryingToAddCreatureOutsideTheMap(){
        assertThrows(IllegalArgumentException.class, ()->board.add(new Point(20,16),creature));
    }

    @Test
    void shouldBeAbleToGoToFieldWhenHisMoveIsAttemptingForThis(){
        Creature creature1 = CreateCreatureDefaultForTests(1);

        board.add(new Point(5,5), creature1);
        assertTrue(board.canMove(creature1,5,6));
        assertTrue(board.canMove(creature1,6,5));
        assertTrue(board.canMove(creature1,5,4));
        assertTrue(board.canMove(creature1,4,5));
    }

    @Test
    void shouldNotBeAbleToGoToFieldWhenHisMoveIsNotAttemptingForThis(){
        Creature creature1 = CreateCreatureDefaultForTests(1);

        GameEngine engine = new GameEngine(List.of(creature1),List.of(creature));
        //creature1 is set on (0,0)
        //creature is set on (WIDTH-1,0)
        assertFalse(engine.canMove(1,1));
        assertFalse(engine.canMove(2,0));
    }

    @Test
    void cannotGoWhenFieldIsTaken(){
        Creature creature1 = NecropolisFactory.CreateCreatureDefaultForTests();
        board.add(new Point(5,5),creature1);

        assertFalse(board.canMove(creature1,0,0));

    }

    @Test
    void shouldBeAbleToGoToFieldWhenHisMoveIsAttemptingForThisInParts(){
        Creature creature1 = CreateCreatureDefaultForTests(1);
        GameEngine engine = new GameEngine(List.of(creature1),List.of(creature));

        //creature1 is set on (0,0)
        //creature is set on (WIDTH-1,0)
        assertTrue(engine.canMove(0,1));
        engine.move(new Point(0,1));
        engine.pass();
        engine.pass();
        assertTrue(engine.canMove(0,2));
        assertFalse(engine.canMove(0,3));

    }
}
