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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class WeaknessAgingTest {

    private Random randomizer;
    private Creature attacker;
    private Creature defender;
    private GameEngine engine;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextDouble()).thenReturn(0.2);
        when(randomizer.nextInt(anyInt())).thenReturn(0);
        Weakness weakness = new MultiplyMaxHpWeaknessDecorator(
                new Weakness.Builder()
                        .percentage(0.2)
                        .duration(3)
                        .name("Aging")
                        .build(),0.5);

        attacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(100)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(1, 2))
                .amount(10)
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
    void defenderShouldGetAgingAndHisMaxHPShouldBeHalved(){
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(50,defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());

        assertTrue(engine.canAttack(BOARD_WIDTH - 2, 0));
        engine.attack(BOARD_WIDTH - 2, 0); // defender

        assertEquals(30, defender.getCurrentHp());
    }

    @Test
    void defenderShouldGetAgingAndHisMaxHPShouldBeHalvedButOnlyFor3Turns(){
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(50,defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());

        engine.pass(); // defender

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(50, defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());

        engine.pass(); // attacker
        engine.pass(); // defender

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(50, defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());

        engine.pass(); // attacker
        engine.pass(); // defender
        assertEquals(0, defender.getWeaknesses().size());
        assertEquals(100, defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());


    }
}
