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

public class TravelToIncreaseDamageCreatureTest {
    private Random randomizer;
    private Creature attacker;
    private Creature defender;
    private GameEngine engine;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextInt(anyInt())).thenReturn(0);

        attacker = getTravelToIncreaseDamageCreature(50);
        defender = new Creature.BuilderForTesting()
                .damage(Range.closed(10, 12))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .moveRange(100)
                .attack(5)
                .armor(5)
                .amount(5)
                .maxHp(100).build();
        engine = new GameEngine(List.of(attacker), List.of(defender));
    }

    @Test
    void attackersDamageShouldGetHigherAbout5PercentBecauseOfTravel1SquareButNoLonger() {
        engine.move(0, 2);
        engine.pass(); // defender

        engine.move(0, 1);
        assertTrue(engine.canAttack(0, 2));
        engine.attack(0, 2); // attacker

        assertEquals(79, defender.getCurrentHp());

        engine.pass(); // defender
        assertTrue(engine.canAttack(0, 2));
        engine.attack(0, 2); // attacker

        assertEquals(59, defender.getCurrentHp());
    }

    @Test
    void attackersDamageShouldGetHigherAbout40PercentBecauseOfTravel8SquareButNoLonger() {
        engine.move(0, 9);
        engine.pass(); // defender

        engine.move(0, 8);
        assertTrue(engine.canAttack(0, 9));
        engine.attack(0, 9); // attacker

        assertEquals(72, defender.getCurrentHp());

        engine.pass(); // defender
        assertTrue(engine.canAttack(0, 9));
        engine.attack(0, 9); // attacker

        assertEquals(52, defender.getCurrentHp());
    }

    @Test
    void attackersDamageShouldGetHigherAbout40PercentButHeShouldBeCursedToo(){
        when(randomizer.nextDouble()).thenReturn(0.1);
        Weakness weakness = new ReduceDamageToLessThanMinimumWeaknessDecorator(
                new Weakness.Builder()
                        .percentage(0.2)
                        .duration(Integer.MAX_VALUE)
                        .name("Curse")
                        .build(),1);

        Creature curseDefender = new InfectsWithWeaknessCreatureDecorator(new BlockingCounterAttackCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(100)
                .attack(5)
                .armor(5)
                .moveRange(200)
                .damage(Range.closed(1,2))
                .damageCalculator( new DefaultDamageCalculator(randomizer))
                .build()), weakness, randomizer);

        engine = new GameEngine(List.of(attacker), List.of(defender, curseDefender));

        engine.move(0,1);
        assertTrue(engine.canAttack(0,0));
        engine.attack(0,0); // curseDefender

        assertEquals(999, attacker.getCurrentHp());
        attacker.getDamage();
        assertEquals(Range.closed(19,19), attacker.getDamage());
        assertEquals(1, attacker.getWeaknesses().size());

        engine.move(0,9);
        engine.pass(); // defender

        engine.move(0,8);
        assertEquals(Range.closed(26,26), attacker.getDamage());
        assertTrue(engine.canAttack(0, 9));
        engine.attack(0,9); // attacker

        assertEquals(74, defender.getCurrentHp());

        engine.pass(); // curseDefender
        engine.pass(); // defender
        assertTrue(engine.canAttack(0, 9));
        engine.attack(0,9); // attacker

        assertEquals(55, defender.getCurrentHp());
    }

    @Test
    void attackersDamageShouldGetHigherEvenInFirstMoveInTheGame() {
        attacker = getTravelToIncreaseDamageCreature(101);
        engine = new GameEngine(List.of(attacker), List.of(defender));

        engine.move(new Point(8, 0)); // attacker

        assertEquals(28, attacker.getDamage().lowerEndpoint());
        assertEquals(35, attacker.getDamage().upperEndpoint());

    }

    @Test
    void attackersDamageShouldNotGetHigherIfItIsFirstMoveOfOtherCreature() {
        assertEquals(20, attacker.getDamage().lowerEndpoint());
        assertEquals(25, attacker.getDamage().upperEndpoint());
        engine.move(0, 2);
        assertEquals(20, attacker.getDamage().lowerEndpoint());
        assertEquals(25, attacker.getDamage().upperEndpoint());
        engine.pass(); // defender
        assertEquals(20, attacker.getDamage().lowerEndpoint());
        assertEquals(25, attacker.getDamage().upperEndpoint());

    }

    private TravelToIncreaseDamageCreatureDecoratorW getTravelToIncreaseDamageCreature(int aMoveRange) {
        return new TravelToIncreaseDamageCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(aMoveRange)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(20, 25))
                .amount(1)
                .build(), 0.05);
    }
}
