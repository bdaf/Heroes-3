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
import static pl.sdk.creatures.Factory.CHANCE_TO_CRITICAL_ATTACK;
import static pl.sdk.creatures.Factory.INCREASE_FACTOR_OF_CRITICAL_ATTACK;

public class DamageIncreaseAgainstParticularNameTest {
    private Random randomizer;
    private Creature attacker;
    private Creature devil;
    private Creature archDevil;
    private GameEngine engine;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextInt(anyInt())).thenReturn(0);
        when(randomizer.nextDouble()).thenReturn(0.2);

        attacker = new BlockingCounterAttackCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(100)
                .damageCalculator(new DamageIncreaseAgainstParticularCreatureNameCalcDecorator(
                        new DamageIncreaseInRandomChanceCalculator(new DefaultDamageCalculator(randomizer), CHANCE_TO_CRITICAL_ATTACK, INCREASE_FACTOR_OF_CRITICAL_ATTACK),
                        "devil", 1.5))
                .damage(Range.closed(10, 12))
                .amount(10)
                .build());
        devil = new Creature.BuilderForTesting()
                .name("Devil")
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(9)
                .attack(5)
                .armor(5)
                .amount(5)
                .maxHp(100).build();
        engine = new GameEngine(List.of(attacker), List.of(devil));

        archDevil = new Creature.BuilderForTesting()
                .name("Arch Devil")
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(8)
                .attack(5)
                .armor(5)
                .amount(1)
                .maxHp(151).build();
        engine = new GameEngine(List.of(attacker), List.of(devil, archDevil));
    }

    @Test
    void angelShouldAttack150PercentOfHisDamageToDevils() {
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(50, devil.getCurrentHp());
        assertEquals(4, devil.getAmount());

        engine.pass(); // devil
        engine.pass(); // arch devil

        engine.move(new Point(BOARD_WIDTH - 2, 2));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 2));
        engine.attack(BOARD_WIDTH - 1, 2); // attacker

        assertEquals(1, archDevil.getCurrentHp());
        assertEquals(1, archDevil.getAmount());

    }

    @Test
    void angelShouldAttack150PercentOfHisDamageToDevilsAndAgain150PercentOfItBecauseOfCriticalAttack() {
        when(randomizer.nextDouble()).thenReturn(0.1);
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // attacker

        assertEquals(75, devil.getCurrentHp());
        assertEquals(3, devil.getAmount());

        engine.pass(); // devil
        engine.pass(); // arch devil

        engine.move(new Point(BOARD_WIDTH - 2, 2));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 2));
        engine.attack(BOARD_WIDTH - 1, 2); // attacker

        assertEquals(0, archDevil.getCurrentHp());
        assertEquals(0, archDevil.getAmount());

    }
}
