package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class WeaknessCurseTest {

    private Random randomizer;
    private Creature attacker;
    private Creature defender;
    private GameEngine engine;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextDouble()).thenReturn(0.1);
        Weakness weakness = new ReduceDamageToLessThanMinimumWeaknessDecorator(
                new Weakness.Builder()
                        .percentage(0.2)
                        .duration(Integer.MAX_VALUE)
                        .name("Curse")
                        .build(),1);

        attacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(100)
                .attack(5)
                .armor(5)
                .moveRange(100)
                .build()), weakness, randomizer);
        defender = new Creature.BuilderForTesting()
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(9)
                .attack(5)
                .armor(5)
                .maxHp(100).build();
        engine = new GameEngine(List.of(attacker), List.of(defender));
    }

    @Test
    void defenderShouldAttackWithMinimumDamageMinusOneAfterHeBecameCursed() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

        assertTrue(engine.canAttack(BOARD_WIDTH - 2, 0));
        engine.attack(BOARD_WIDTH - 2, 0); // defender

        assertEquals(91, attacker.getCurrentHp());
    }

    @Test
    void defenderShouldHaveMinimumDamageMinusOneAfterHeBecameCursedByCounterAttack() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.pass(); // attacker

        assertEquals(0, defender.getWeaknesses().size());
        assertEquals(10, defender.getDamage().lowerEndpoint());
        assertEquals(12, defender.getDamage().upperEndpoint());

        assertTrue(engine.canAttack(BOARD_WIDTH - 2, 0));
        engine.attack(BOARD_WIDTH - 2, 0); // defender

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

    }

    @Test
    void defenderShouldCurseButOnlyOneEvenIfThereWillBeOtherInflicts() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

        assertTrue(engine.canAttack(BOARD_WIDTH - 2, 0));
        engine.attack(BOARD_WIDTH - 2, 0); // defender

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

    }
}
