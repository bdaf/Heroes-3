package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoubleAttackTest {

    @Test
    void ShootingCreatureShouldAttackTwiceAndDefenderShouldNotCounterAttack(){
        Creature attacker = new ShootingCreatureDecorator(new Creature.BuilderForTesting()
                .attacksInTurn(2)
                .damage(Range.closed(1,1)).build());
        Creature defender = new Creature.BuilderForTesting().maxHp(100).build();
        GameEngine engine = new GameEngine(List.of(attacker),List.of(defender));

        assertTrue(engine.canAttack(GameEngine.BOARD_WIDTH-1,0));
        engine.attack(GameEngine.BOARD_WIDTH-1,0);
        assertTrue(engine.canAttack(GameEngine.BOARD_WIDTH-1,0));
        engine.attack(GameEngine.BOARD_WIDTH-1,0);

        assertEquals(98,defender.getCurrentHp());
        assertEquals(100,attacker.getCurrentHp());
    }

    @Test
    void NotShootingCreatureShouldAttackTwiceThroughDoubleAttackDecoratorAndDefenderShouldCounterAttackOnce(){
        Creature attacker = new Creature.BuilderForTesting()
                .attacksInTurn(2)
                .damage(Range.closed(1,1)).build();
        Creature defender = new Creature.BuilderForTesting()
                .damage(Range.closed(1,1))
                .maxHp(100)
                .moveRange(50).build();
        GameEngine engine = new GameEngine(List.of(defender),List.of(attacker));

        assertTrue(engine.canMove(GameEngine.BOARD_WIDTH-1,1)); // defender
        engine.move(new Point(GameEngine.BOARD_WIDTH-1,1));
        engine.pass();

        assertTrue(engine.canAttack(GameEngine.BOARD_WIDTH-1,1));
        engine.attack(GameEngine.BOARD_WIDTH-1,1);
        assertTrue(engine.canAttack(GameEngine.BOARD_WIDTH-1,1));
        engine.attack(GameEngine.BOARD_WIDTH-1,1);

        assertEquals(98,defender.getCurrentHp());
        assertEquals(99,attacker.getCurrentHp());
    }
}
