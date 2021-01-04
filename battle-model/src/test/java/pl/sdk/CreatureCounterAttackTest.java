package pl.sdk;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreatureCounterAttackTest {

    private static final int NOT_IMPORTANT = 2;

    @Test
    void defenderShouldCounterAttack(){
        Creature attacker = new Creature("c1",NOT_IMPORTANT,2,100, NOT_IMPORTANT);
        Creature defender = new Creature("c2", 5,NOT_IMPORTANT,NOT_IMPORTANT,NOT_IMPORTANT);

        attacker.attack(defender);

        assertEquals(97,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldNotCounterAttackWhenHeSupposedToBeDead(){
        Creature attacker = new Creature("c1",100,NOT_IMPORTANT,100, NOT_IMPORTANT);
        Creature defender = new Creature("c2", NOT_IMPORTANT,20,80,NOT_IMPORTANT);

        attacker.attack(defender);

        assertEquals(100,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldCounterAttackOnlyOnes(){
        Creature attacker = new Creature("c1",5,1,100, NOT_IMPORTANT);
        Creature defender = new Creature("c2", 10,NOT_IMPORTANT,80,NOT_IMPORTANT);

        attacker.attack(defender);
        assertEquals(91,attacker.getCurrentHp());
        attacker.attack(defender);
        assertEquals(91,attacker.getCurrentHp());
    }

    @Test
    void defenderShouldCounterAttackOnesInTourNotOnesInGame(){

        Creature attacker = new Creature();
        Creature defender = new Creature();

        GameEngine game = new GameEngine(List.of(attacker),List.of(defender));
        assertEquals(true,defender.canCounterAttack());
        attacker.attack(defender);
        assertEquals(false,defender.canCounterAttack());

        game.pass();
        game.pass();
        assertEquals(true,defender.canCounterAttack());


    }

    @Test
    void shouldThrowExceptionWhenattackerAttackHimself(){
        List<Creature> creaturesLeft = new LinkedList<Creature>();
        List<Creature> creaturesRight = new LinkedList<Creature>();
        Creature attacker = new Creature("c1",5,NOT_IMPORTANT,100, NOT_IMPORTANT);
        creaturesLeft.add(attacker);
        GameEngine game = new GameEngine(creaturesLeft,creaturesRight);
        assertThrows(IllegalArgumentException.class, () -> game.attack(0,0));


    }
}
