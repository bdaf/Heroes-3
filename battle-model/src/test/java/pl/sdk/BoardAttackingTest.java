package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.sdk.AttackCreatureTest.NOT_IMPORTANT;

public class BoardAttackingTest {
    private Board board;
    private Creature creature;

    @BeforeEach
    void init(){
        board = new Board();
        creature = new Creature();
        board.add(new Point(0,0), creature);
    }


    @Test
    void shouldBeAbleToAttackWhenHisNearToEnemyOn1Unit(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 1, new DamageCalculator(), NOT_IMPORTANT);
        board.add(new Point(1,0), creature1);

        assertTrue(board.canAttack(creature1,0,0));
    }

    @Test
    void shouldBeAbleToAttackWhenHisNearToEnemyOnAbove1_5Unit(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 1, new DamageCalculator(), NOT_IMPORTANT);
        board.add(new Point(1,2), creature1);

        assertFalse(board.canAttack(creature1,0,0));
    }

    @Test
    void cannotAttackWhenFieldIsEmpty(){
        Creature creature1 = new Creature("DefName", 1, 1, 1, 10, new DamageCalculator(), NOT_IMPORTANT);
        board.add(new Point(1,0),creature1);

      assertFalse(board.canAttack(creature1, 1, 1));
    }
}
