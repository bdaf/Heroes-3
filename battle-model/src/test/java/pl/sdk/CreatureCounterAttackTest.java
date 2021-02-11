package pl.sdk;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.sdk.AttackCreatureTest.NOT_IMPORTANT_RANGE;

public class CreatureCounterAttackTest {

    @Test
    void defenderShouldCounterAttack(){
        Creature attacker = new Creature.Builder()
                .armor(2)
                .maxHp(100)
                .damageCalculator(new DamageCalculator())
                .build();
        Creature defender = new Creature.Builder()
                .attack(5)
                .damageCalculator(new DamageCalculator())
                .build();

        attacker.attack(defender);

        assertEquals(97,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldNotCounterAttackWhenHeSupposedToBeDead(){
        Creature attacker = new Creature.Builder()
                .attack(100)
                .maxHp(100)
                .damageCalculator(new DamageCalculator())
                .build();
        Creature defender = new Creature.Builder()
                .armor(20)
                .maxHp(80)
                .damageCalculator(new DamageCalculator())
                .build();

        attacker.attack(defender);
        assertEquals(100,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldCounterAttackOnlyOnes(){
        Creature attacker = new Creature.Builder()
                .attack(5)
                .armor(1)
                .maxHp(100)
                .damageCalculator(new DamageCalculator())
                .build();
        Creature defender = new Creature.Builder()
                .attack(10)
                .maxHp(80)
                .damageCalculator(new DamageCalculator())
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
                .damageCalculator(new DamageCalculator())
                .build();
        creaturesLeft.add(attacker);
        GameEngine game = new GameEngine(creaturesLeft,creaturesRight);
        assertThrows(IllegalArgumentException.class, () -> game.attack(0,0));
    }
}
