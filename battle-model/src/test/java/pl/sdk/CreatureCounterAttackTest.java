package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.DefaultDamageCalculator;

import java.util.LinkedList;
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
        Creature attacker = new Creature.Builder()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damage(Range.closed(80,100))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();
        Creature defender = new Creature.Builder()
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
        Creature attacker = new Creature.Builder()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator())
                .damage(Range.closed(80,100))
                .build();
        Creature defender = new Creature.Builder()
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
        Creature attacker = new Creature.Builder()
                .attack(THE_SAME_FOR_BOTH)
                .armor(THE_SAME_FOR_BOTH)
                .maxHp(100)
                .damage(Range.closed(1,3))
                .damageCalculator(new DefaultDamageCalculator())
                .build();
        Creature defender = new Creature.Builder()
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
        Creature attacker = new Creature.Builder().build();
        Creature defender = new Creature.Builder().build();
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
        List<Creature> creaturesLeft = new LinkedList<Creature>();
        List<Creature> creaturesRight = new LinkedList<Creature>();
        Creature attacker = new Creature.Builder()
                .attack(5)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator())
                .build();
        creaturesLeft.add(attacker);
        GameEngine game = new GameEngine(creaturesLeft,creaturesRight);
        assertThrows(IllegalArgumentException.class, () -> game.attack(0,0));
    }
}
