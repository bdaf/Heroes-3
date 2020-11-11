package pl.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
