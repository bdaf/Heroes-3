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

public class DamageIncreaseAgainstParticularFractionTest {
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

        attacker = new BlockingCounterAttackCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(100)
                .damageCalculator(new DamageIncreaseAgainstParticularCreatureNameCalculator("devil", 1.5, randomizer))
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
}
