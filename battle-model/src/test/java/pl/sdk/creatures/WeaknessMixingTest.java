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

public class WeaknessMixingTest {

    private Random randomizer;
    private Creature agingAattacker;
    private Creature defender;
    private GameEngine engine;
    private Creature curseAttacker;
    private Creature diseaseAttacker;

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
                        .build(), 0.5);

        agingAattacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(100)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(1, 2))
                .amount(60)
                .name("aging creature")
                .build()), weakness, randomizer);
        agingAattacker.setAmount(10);

        weakness = new ReduceDamageToLessThanMinimumWeaknessDecorator(
                new Weakness.Builder()
                        .percentage(0.2)
                        .duration(Integer.MAX_VALUE)
                        .name("Curse")
                        .build(), 1);

        curseAttacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(99)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(1, 2))
                .amount(60)
                .name("curse creature")
                .build()), weakness, randomizer);
        curseAttacker.setAmount(10);

        weakness = new Weakness.Builder()
                .percentage(0.2)
                .duration(3)
                .attackToDecrease(2)
                .defenseToDecrease(2)
                .name("Disease")
                .build();

        diseaseAttacker = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(98)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(1, 2))
                .amount(60)
                .name("disease creature")
                .build()), weakness, randomizer);
        diseaseAttacker.setAmount(10);

        defender = new Creature.BuilderForTesting()
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(9)
                .attack(5)
                .armor(5)
                .amount(5)
                .maxHp(100).build();
        engine = new GameEngine(List.of(agingAattacker, curseAttacker, diseaseAttacker), List.of(defender));
    }

    @Test
    void defenderShouldGetAgingAndCurseAndDiseaseTogether(){
        engine.move(new Point(BOARD_WIDTH - 2, 0));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // aging attacker

        assertEquals(1, defender.getWeaknesses().size());
        assertEquals(50,defender.getMaxHp());
        assertEquals(40, defender.getCurrentHp());

        engine.move(new Point(BOARD_WIDTH - 2, 1));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // curse attacker

        assertEquals(2, defender.getWeaknesses().size());
        assertEquals(50,defender.getMaxHp());
        assertEquals(30, defender.getCurrentHp());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());

        engine.move(new Point(BOARD_WIDTH - 1, 1));
        assertTrue(engine.canAttack(BOARD_WIDTH - 1, 0));
        engine.attack(BOARD_WIDTH - 1, 0); // disease attacker

        assertEquals(3, defender.getWeaknesses().size());
        assertEquals(50,defender.getMaxHp());
        assertEquals(20, defender.getCurrentHp());
        assertEquals(9, defender.getDamage().lowerEndpoint());
        assertEquals(9, defender.getDamage().upperEndpoint());
        assertEquals(3, defender.getAttack());
        assertEquals(3, defender.getArmor());
    }

}
