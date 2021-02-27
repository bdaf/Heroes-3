package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StackOfCreaturesTest {

    public static final int NOT_IMPORTANT = 5;
    public static final int INF = 999999999;
    private Creature defender;

    @BeforeEach
    void init(){
        defender = new Creature.Builder()
                .armor(NOT_IMPORTANT)
                .attack(NOT_IMPORTANT)
                .amount(10)
                .maxHp(100)
                .build();
    }

    @Test
    void defenderShouldHaveStackEquals9WhenWeKillOneOfThem(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(100,100))
                .build();
        attacker.attack(defender);
        assertEquals(9, defender.getAmount());
        assertEquals(100, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals8WhenWeKillTwoOfThem(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(200,200))
                .build();
        attacker.attack(defender);
        assertEquals(8, defender.getAmount());
        assertEquals(100, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals10WhenWeDoNotKillAnyOfThem(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(99,99))
                .build();
        attacker.attack(defender);
        assertEquals(10, defender.getAmount());
        assertEquals(1, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals9WhenWeAlmostKillTwoOfThem(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(199,199))
                .build();
        attacker.attack(defender);

        assertEquals(9, defender.getAmount());
        assertEquals(1, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals8WhenWeAttackTwiceWitchDamageLittleLowerThanDefendersHp(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(99,99))
                .build();
        attacker.attack(defender);
        attacker.attack(defender);
        assertEquals(9, defender.getAmount());
        assertEquals(2, defender.getCurrentHp());
    }
    @Test
    void defenderShouldHaveHpEquals0WhenDamageIsEqualsStack(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(1000,1000))
                .build();
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }
    @Test
    void defenderShouldHaveStackEquals0AndHpEquals0WhenDamageEqualsAmounts(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(950,950))
                .build();
        attacker.attack(defender);
        assertEquals(1, defender.getAmount());
        assertEquals(50, defender.getCurrentHp());
        attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(50,50))
                .build();
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }
    @Test
    void defenderShouldHaveStackEquals0AndHpEquals0WhenDamageIsGraterThanAmountsAndDefenderHasLessCurrentHp(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(1000,1000))
                .build();
        Creature attacker2 = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(40,40))
                .build();
        attacker2.attack(defender);
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals0AndHpEquals0WhenDamageIsGraterThanAmountsAndDefenderHasLessCurrentHpV2(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(1100,1100))
                .build();
        Creature attacker2 = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(40,40))
                .build();
        attacker2.attack(defender);
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals0AndHpEquals0WhenDamageIsGraterThanAmounts(){
        Creature attacker = new Creature.Builder()
                .attack(NOT_IMPORTANT)
                .armor(NOT_IMPORTANT)
                .maxHp(INF)
                .damage(Range.closed(1001,1001))
                .build();
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }

    @Test
    void defenderShouldHaveStackEquals0WhenAttackerIsMuchGreaterAndAttacksHimAndHpEquals0Too(){
        Creature attacker = new Creature.Builder()
                .maxHp(INF)
                .attack(NOT_IMPORTANT)
                .moveRange(14)
                .damage(Range.closed(350,350))
                .armor(17)
                .build();
        defender = new Creature.Builder()
                .armor(NOT_IMPORTANT)
                .attack(NOT_IMPORTANT)
                .amount(1)
                .maxHp(100)
                .build();
        attacker.attack(defender);
        assertEquals(0, defender.getAmount());
        assertEquals(0, defender.getCurrentHp());
    }



}
