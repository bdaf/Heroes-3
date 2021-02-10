package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttackCreatureTest {

    public static final int NOT_IMPORTANT = 2;
    public static final Range<Integer> NOT_IMPORTANT_RANGE = Range.closed(5,5);
    private Random randomizer;

    @BeforeEach
    void init(){
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextInt(anyInt())).thenReturn(5);
    }

    @Test
    void defenderShouldHaveLost2HPWhenAttackerHas3AttackAndDefenderHas1Armor(){
        Creature attacker = new Creature.Builder()
                .name("c1")
                .attack(3)
                .armor(NOT_IMPORTANT)
                .maxHp(10)
                .moveRange(NOT_IMPORTANT)
                .damageCalculator(new DamageCalculator())
                .damage(NOT_IMPORTANT_RANGE)
                .build();
        Creature defender = new Creature.Builder()
                .name("c2")
                .attack(NOT_IMPORTANT)
                .armor(1)
                .maxHp(10)
                .moveRange(NOT_IMPORTANT)
                .damageCalculator(new DamageCalculator())
                .damage(NOT_IMPORTANT_RANGE)
                .build();

        attacker.attack(defender);

        assertEquals(8,defender.getCurrentHp());
    }



    @Test
    void defenderShouldNotSelfHealWhenHasMoreArmorThatAttackerHasAttack(){
        Creature attacker = new Creature("c1",10,NOT_IMPORTANT,100, NOT_IMPORTANT, new DamageCalculator(), NOT_IMPORTANT_RANGE);
        Creature defender = new Creature("c2", NOT_IMPORTANT,20,100,NOT_IMPORTANT, new DamageCalculator(), NOT_IMPORTANT_RANGE);

        attacker.attack(defender);

        assertEquals(100,defender.getCurrentHp());
    }


    //////////////////////////// AFTER NEW DMG CALCULATOR //////////////////////////////


    @Test
    void defenderShouldHaveLostMaximum_400PercentAndAttackerShouldHaveMinimum_30Percent(){
        Creature attacker = new Creature("c1",100,100,1000, NOT_IMPORTANT, new DefaultDamageCalculator(randomizer), Range.closed(95,100));
        Creature defender = new Creature("c2", 1,1,1000, NOT_IMPORTANT, new DefaultDamageCalculator(randomizer), Range.closed(95,100));

        attacker.attack(defender);

        assertEquals(600, defender.getCurrentHp());
        assertEquals(970,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldHaveLost200PercenOfAttackersAttackAndAttackerShouldHaveLost75PecentOfDefendersAttack(){
        Creature attacker = new Creature("c1",100,60,1000, NOT_IMPORTANT, new DefaultDamageCalculator(randomizer), Range.closed(50,60));
        Creature defender = new Creature("c2", 50,80,1000, NOT_IMPORTANT, new DefaultDamageCalculator(randomizer), Range.closed(100,110));

        attacker.attack(defender);
        assertEquals(890,defender.getCurrentHp());
        assertEquals(922,attacker.getCurrentHp());
    }
}
