package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreatureCounterAttackTest {
    public static final int THE_SAME_FOR_BOTH = 10;
    private Random randomizer;

    @BeforeEach
    void init(){
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextInt(anyInt())).thenReturn(0);
    }
    @Test
    void defenderShouldCounterAttack(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damage(Range.closed(80,100))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damage(Range.closed(3,100))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();

        attacker.attack(defender);

        assertEquals(97,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldNotCounterAttackWhenHeSupposedToBeDead(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator())
                .damage(Range.closed(80,100))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(80)
                .damageCalculator(new DefaultDamageCalculator())
                .build();

        attacker.attack(defender);
        assertEquals(100,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldCounterAttackOnlyOnes(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damage(Range.closed(1,3))
                .damageCalculator(new DefaultDamageCalculator())
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(80)
                .damage(Range.closed(9,11))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();

        attacker.attack(defender);
        assertEquals(91,attacker.getCurrentHp());
        attacker.attack(defender);
        assertEquals(91,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldCounterAttackOnesInTourNotOnesInGame(){
        Creature attacker = new Creature.BuilderForTesting().build();
        Creature defender = new Creature.BuilderForTesting().build();
        GameEngine game = new GameEngine(List.of(attacker),List.of(defender));
        assertEquals(true,defender.canCounterAttack());
        attacker.attack(defender);
        assertEquals(false,defender.canCounterAttack());
        game.pass();
        game.pass();
        assertEquals(true,defender.canCounterAttack());
    }

    @Test
    void shouldThrowExceptionWhenAttackerAttackHimself(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(5)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator())
                .build();
        GameEngine game = new GameEngine(List.of(attacker), Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> game.attack(0,0));
    }
}
