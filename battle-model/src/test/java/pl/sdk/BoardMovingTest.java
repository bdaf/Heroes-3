package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.GameEngine.BOARD_WIDTH;
import static pl.sdk.creatures.Factory.CreateShootingCreatureForTests;
import static pl.sdk.creatures.NecropolisFactory.CreateDefaultCreatureForTests;

public class BoardMovingTest {

    private static final int NOT_IMPORTANT = 2;
    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = NecropolisFactory.CreateDefaultCreatureForTests();
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

        Creature creature2 = NecropolisFactory.CreateDefaultCreatureForTests();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () ->board.moveAndReturnDistance(new Point(0,0), new Point(2,3)));
        Creature creatureFromBoard = board.get(2,3);

        assertEquals(creature2, creatureFromBoard);
    }

    @Test
    void shouldThrowExceptionWhenCreatureIsTryingToBeAddedToNotEmptyField(){

        Creature creature2 = NecropolisFactory.CreateDefaultCreatureForTests();
        board.add(new Point(2,3), creature2);

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(2,3), NecropolisFactory.CreateDefaultCreatureForTests()));
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
        Creature creature1 = CreateDefaultCreatureForTests(1);

        board.add(new Point(5,5), creature1);
        assertTrue(board.isFieldFreeToTake(5,6));
        assertTrue(board.isFieldFreeToTake(6,5));
        assertTrue(board.isFieldFreeToTake(5,4));
        assertTrue(board.isFieldFreeToTake(4,5));
    }

    @Test
    void shouldNotBeAbleToGoToFieldWhenHisMoveIsNotAttemptingForThis(){
        Creature creature1 = CreateDefaultCreatureForTests(1);

        GameEngine engine = new GameEngine(List.of(creature1),List.of(creature));
        //creature1 is set on (0,0)
        //creature is set on (WIDTH-1,0)
        assertFalse(engine.canMove(1,1));
        assertFalse(engine.canMove(2,0));
    }

    @Test
    void cannotGoWhenFieldIsTaken(){
        Creature creature1 = NecropolisFactory.CreateDefaultCreatureForTests();
        board.add(new Point(5,5),creature1);

        assertFalse(board.isFieldFreeToTake(0,0));

    }

    @Test
    void shouldBeAbleToGoToFieldWhenHisMoveIsAttemptingForThisInParts(){
        Creature creature1 = CreateDefaultCreatureForTests(2);
        GameEngine engine = new GameEngine(List.of(creature1),List.of(creature));
        //creature1 is set on (0,0)
        //creature is set on (WIDTH-1,0)
        assertEquals(creature1, engine.getActiveCreature());
        assertTrue(engine.canMove(0,2));
        engine.move(new Point(0,2));
        engine.pass();
        assertTrue(engine.canMove(0,4));
        assertFalse(engine.canMove(0,5));

    }

    @Test
    void shouldBeAbleToGoToFieldWhenOtherCreatureIsDead(){
        Creature leftShootingCreature = CreateShootingCreatureForTests(Integer.MAX_VALUE,100);
        Creature rightDeadCreature = CreateDefaultCreatureForTests(Integer.MAX_VALUE-1,1);
        Creature rightCreature = CreateDefaultCreatureForTests(Integer.MAX_VALUE-2);
        GameEngine engine = new GameEngine(List.of(leftShootingCreature),List.of(rightDeadCreature,rightCreature));

        assertTrue(engine.canAttack(BOARD_WIDTH-1,0));
        engine.attack(BOARD_WIDTH-1,0);
        assertTrue(!rightDeadCreature.isAlive());
        assertEquals(rightCreature, engine.getActiveCreature());
        assertTrue(engine.canMove(BOARD_WIDTH-1,0));
        engine.move(new Point(BOARD_WIDTH-1,0));
        assertFalse(engine.canMove(BOARD_WIDTH-1,0));

    }
}
