package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.BlockingCounterAttackCreatureDecorator;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.ShootingCreatureDecorator;

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

    @Test
    void shouldBeAbleToAttackWheneverDefenderIsButDefenderCanNotCounterAttack(){
        Creature creature1 = new ShootingCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                .maxHp(100)
                .build()));
        board.add(new Point(10,10),creature1);
        boolean result = board.canAttack(creature1, 0, 0);
        assertTrue(result);
        creature1.attack(creature);
        assertEquals(100, creature1.getCurrentHp());
    }

    @Test
    void defenderCanNotCounterAttack(){
        Creature creature1 = new BlockingCounterAttackCreatureDecorator(new Creature.Builder()
                .maxHp(100)
                .build());
        board.add(new Point(1,0),creature1);
        boolean result = board.canAttack(creature1, 0, 0);
        assertTrue(result);
        creature1.attack(creature);
        assertEquals(100, creature1.getCurrentHp());
    }
}
