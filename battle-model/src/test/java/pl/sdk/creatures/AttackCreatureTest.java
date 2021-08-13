package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttackCreatureTest {

    public static final int NOT_IMPORTANT = 2;
    public static final Range<Integer> NOT_IMPORTANT_RANGE = Range.closed(5,5);
    private Random randomizer;

    @BeforeEach
    void init(){
        randomizer = mock(Random.class);
        when(randomizer.nextInt(anyInt())).thenReturn(5);
    }

    @Test
    void defenderShouldHaveLost11HPWhenAttackerHas2AttackMoreThanDefendersArmorAndItIs10PercentMoreToDealThanAttackersDamage(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(3)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .damage(Range.closed(5,15))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .armor(1)
                .maxHp(100)
                .damageCalculator(new DefaultDamageCalculator())
                .build();
        attacker.attack(defender);
        assertEquals(89,defender.getCurrentHp());
    }



    //////////////////////////// AFTER NEW DMG CALCULATOR //////////////////////////////


    @Test
    void defenderShouldHLostMaximum_400PercentOfAttackersDamageAndAttackerShouldLostMinimum_30PercentOfDefendersDamage(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(100)
                .armor(100)
                .maxHp(1000)
                .damage(Range.closed(95,100))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(1)
                .armor(1)
                .maxHp(1000)
                .damage(Range.closed(95,100))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();

        attacker.attack(defender);
        assertEquals(600, defender.getCurrentHp());
        assertEquals(970,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldHaveLost200PercentOfAttackersDamageAndAttackerShouldHaveLost75PercentOfDefendersDamage(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(100)
                .armor(60)
                .maxHp(1000)
                .damage(Range.closed(50,60))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(50)
                .armor(80)
                .maxHp(1000)
                .damage(Range.closed(100,110))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();

        attacker.attack(defender);
        assertEquals(890,defender.getCurrentHp());
        assertEquals(922,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldNotCounterAttack(){
        Creature attacker = new Creature.BuilderForTesting()
                .attack(100)
                .armor(60)
                .maxHp(1000)
                .damage(Range.closed(50,60))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(50)
                .armor(80)
                .maxHp(1000)
                .damage(Range.closed(100,110))
                .damageCalculator(new DefaultDamageCalculator(randomizer))
                .build();

        attacker.attack(defender);
        assertEquals(890,defender.getCurrentHp());
        assertEquals(922,attacker.getCurrentHp());
    }
}
