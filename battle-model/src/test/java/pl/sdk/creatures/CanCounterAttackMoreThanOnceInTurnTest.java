package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class CanCounterAttackMoreThanOnceInTurnTest {

    @Test
    void defenderShouldCounterAttackTwiceInOneTurn(){
        Creature defender = CastleFactory.CreateCounterAttackSeveralTimesInTurnCreature(2,50);
        Creature attacker1 = new Creature.Builder().damage(Range.closed(1,1)).moveRange(2).build();
        Creature attacker2 = new Creature.Builder().damage(Range.closed(1,1)).moveRange(1).build();
        GameEngine engine = new GameEngine(List.of(defender),List.of(attacker1,attacker2));

        engine.move(new Point(BOARD_WIDTH -1,1)); // defender
        engine.pass();

        assertTrue(defender.canCounterAttack());

        assertTrue(engine.canAttack(BOARD_WIDTH -1,1)); //attacker1
        engine.attack(BOARD_WIDTH-1,1);
        engine.pass();

        assertTrue(defender.canCounterAttack());

        assertTrue(engine.canAttack(BOARD_WIDTH -1,1)); //attacker2
        engine.attack(BOARD_WIDTH-1,1);

        assertFalse(defender.canCounterAttack());
    }

    @Test
    void defenderShouldCounterAttackInfinityTimesInOneTurn(){
        Creature defender = CastleFactory.CreateCounterAttackSeveralTimesInTurnCreature(Integer.MAX_VALUE,50);
        Creature attacker1 = new Creature.Builder().damage(Range.closed(1,1)).moveRange(2).build();
        Creature attacker2 = new Creature.Builder().damage(Range.closed(1,1)).moveRange(1).build();
        GameEngine engine = new GameEngine(List.of(defender),List.of(attacker1,attacker2));

        engine.move(new Point(BOARD_WIDTH -1,1)); // defender
        engine.pass();

        assertTrue(defender.canCounterAttack());

        assertTrue(engine.canAttack(BOARD_WIDTH -1,1)); //attacker1
        engine.attack(BOARD_WIDTH-1,1);
        engine.pass();

        assertTrue(defender.canCounterAttack());

        assertTrue(engine.canAttack(BOARD_WIDTH -1,1)); //attacker2
        engine.attack(BOARD_WIDTH-1,1);

        assertTrue(defender.canCounterAttack());
    }
}
