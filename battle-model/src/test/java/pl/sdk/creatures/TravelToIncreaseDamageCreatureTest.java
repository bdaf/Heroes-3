package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;

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

        attacker = new BlockingCounterAttackCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .attack(5)
                .armor(5)
                .moveRange(50)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(20, 25))
                .amount(1)
                .build());
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
    void attackersDamageShouldGetHigherAbout5PercentBecauseOfTravel1Square(){
        engine.move(0,2);
        engine.pass(); // defender

        engine.move(0,1);
        assertTrue(engine.canAttack(0, 2));
        engine.attack(0,2); // attacker

        assertEquals(79, defender.getCurrentHp());
    }

    @Test
    void attackersDamageShouldGetHigherAbout40PercentBecauseOfTravel8Square(){
        engine.move(0,9);
        engine.pass(); // defender

        engine.move(0,8);
        assertTrue(engine.canAttack(0, 9));
        engine.attack(0,9); // attacker

        assertEquals(72, defender.getCurrentHp());
    }
}
