package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardAttackingTest {
    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = new Creature.Builder().build();
        board.add(new Point(0,0), creature);
    }


    @Test
    void shouldBeAbleToAttackWhenHisNearToEnemyOn1Unit(){
        Creature creature1 = new Creature.Builder().build();
        board.add(new Point(1,0), creature1);

        assertTrue(board.canAttack(creature1,0,0));
    }

    @Test
    void shouldBeAbleToAttackWhenHisNearToEnemyOnAbove1_5Unit(){
        Creature creature1 = new Creature.Builder().build();
        board.add(new Point(1,2), creature1);

        assertFalse(board.canAttack(creature1,0,0));
    }

    @Test
    void cannotAttackWhenFieldIsEmpty(){
        Creature creature1 = new Creature.Builder().build();
        board.add(new Point(1,0),creature1);

      assertFalse(board.canAttack(creature1, 1, 1));
    }
}
