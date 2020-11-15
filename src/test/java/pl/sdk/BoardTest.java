package pl.sdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
         board = new Board();
         creature = new Creature();
    }

    @Test
    void shouldAddCreature(){
        board.add(new Point(0,0),creature);
        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldReturnNullWhenFieldIsEmpty(){
        creature = board.get(0,0);
        assertNull(creature);
    }

    @Test
    void shouldBeNotOverWrittenWhenyouTryToPutCreatureOnFieldWhenFieldIsTaken(){
        board.add(new Point(0,0),creature);
        Creature creature2 = new Creature();

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(0,0),creature2));
        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldReturnCorrectLocationForGetWithArgumentCreature(){
        Creature creature1 = new Creature();
        Creature creature2 = new Creature();
        board.add(new Point(2,1),creature1);
        board.add(new Point(1,1),creature);
        board.add(new Point(1,3),creature2);
        Point result = board.get(creature);
        assertEquals(new Point(1,1),result);
    }

}
