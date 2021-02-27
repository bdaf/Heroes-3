package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
         board = new Board();
         creature = new Creature.Builder().build();
    }

    @Test
    void shouldAddCreature(){
        board.add(new Point(0,0),creature);
        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldBeNotOverWrittenWhenyouTryToPutCreatureOnFieldWhenFieldIsTaken(){
        board.add(new Point(0,0),creature);
        Creature creature2 = new Creature.Builder().build();;

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(0,0),creature2));
        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldReturnCorrectLocationForGetWithArgumentCreature(){
        Creature creature1 = new Creature.Builder().build();
        Creature creature2 = new Creature.Builder().build();
        board.add(new Point(2,1),creature1);
        board.add(new Point(1,1),creature);
        board.add(new Point(1,3),creature2);
        Point result = board.get(creature);
        assertEquals(new Point(1,1),result);
    }

/*    @Test
    void shouldThrowExceptionWhenCreatureIsBeenTryingToBeCatchedWhereFieldIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> board.get(0,0));
    }*/


}
