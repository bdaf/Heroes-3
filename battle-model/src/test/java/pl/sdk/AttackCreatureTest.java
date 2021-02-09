package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackCreatureTest {

    public static final int NOT_IMPORTANT = 2;
    public static final Range<Integer> NOT_IMPORTANT_RANGE = Range.closed(5,5);

    @Test
    void defenderShouldHaveLost2HPWhenAttackerHas3AttackAndDefenderHas1Armor(){
        Creature attacker = new Creature("c1",3,NOT_IMPORTANT,10, NOT_IMPORTANT, new DamageCalculator(), NOT_IMPORTANT_RANGE);
        Creature defender = new Creature("c2", NOT_IMPORTANT,1,10,NOT_IMPORTANT, new DamageCalculator(), NOT_IMPORTANT_RANGE);

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
        Creature attacker = new Creature("c1",100,100,1000, NOT_IMPORTANT, new NewDamageCalculator(), Range.closed(100,100));
        Creature defender = new Creature("c2", 1,1,1000, NOT_IMPORTANT, new NewDamageCalculator(), Range.closed(100,100));

        attacker.attack(defender);

        assertEquals(600, defender.getCurrentHp());
        assertEquals(970,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldHaveLost200PercenOfAttackersAttackAndAttackerShouldHaveLost75PecentOfDefendersAttack(){
        Creature attacker = new Creature("c1",100,60,1000, NOT_IMPORTANT, new NewDamageCalculator(), Range.closed(50,50));
        Creature defender = new Creature("c2", 50,80,1000, NOT_IMPORTANT, new NewDamageCalculator(), Range.closed(100,100));

        attacker.attack(defender);

        assertEquals(900, defender.getCurrentHp());
        assertEquals(925,attacker.getCurrentHp());
    }
}
