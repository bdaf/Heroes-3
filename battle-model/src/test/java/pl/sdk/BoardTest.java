package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.creatures.NecropolisFactory.*;

public class BoardTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init() {
        board = new Board();
        creature = CreateDefaultForTests();
    }

    @Test
    void shouldAddCreature() {
        board.add(new Point(0, 0), creature);
        Creature creatureFromBoard = board.get(0, 0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldBeNotOverWrittenWhenyouTryToPutCreatureOnFieldWhenFieldIsTaken() {
        board.add(new Point(0, 0), creature);
        Creature creature2 = CreateDefaultForTests();
        ;

        assertThrows(IllegalArgumentException.class, () -> board.add(new Point(0, 0), creature2));
        Creature creatureFromBoard = board.get(0, 0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldReturnCorrectLocationForGetWithArgumentCreature() {
        Creature creature1 = CreateDefaultForTests();
        Creature creature2 = CreateDefaultForTests();
        board.add(new Point(2, 1), creature1);
        board.add(new Point(1, 1), creature);
        board.add(new Point(1, 3), creature2);
        Point result = board.get(creature);
        assertEquals(new Point(1, 1), result);
    }

    /*    @Test
        void shouldThrowExceptionWhenCreatureIsBeenTryingToBeCatchedWhereFieldIsEmpty(){
            assertThrows(IllegalArgumentException.class, () -> board.get(0,0));
        }*/
    @Test
    void shouldBeAbleToAttackWheneverDefenderIsButDefenderCanNotCounterAttack() {
        Creature attacker = CreateShootingCreatureForTests();

        board.add(new Point(10, 10), attacker);
        board.add(new Point(0, 0), creature);

        assertTrue(board.canAttack(attacker, 0, 0));
        attacker.attack(creature);
        assertEquals(100, attacker.getCurrentHp());
    }

    @Test
    void defenderCannotCounterAttack() {
        Creature creature1 = CreateBlockingCreatureForTests();

        board.add(new Point(1, 0), creature1);
        board.add(new Point(0, 0), creature1);

        assertTrue(board.canAttack(creature1, 0, 0));
        creature1.attack(creature);
        assertEquals(100, creature1.getCurrentHp());
    }

}
