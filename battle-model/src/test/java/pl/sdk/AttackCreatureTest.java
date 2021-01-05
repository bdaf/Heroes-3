package pl.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackCreatureTest {

    public static final int NOT_IMPORTANT = 2;

    @Test
    void defenderShouldHaveLost2HPWhenAttackerHas3AttackAndDefenderHas1Armor(){
        Creature attacker = new Creature("c1",3,NOT_IMPORTANT,10, NOT_IMPORTANT);
        Creature defender = new Creature("c2", NOT_IMPORTANT,1,10,NOT_IMPORTANT);

        attacker.attack(defender);

        assertEquals(8,defender.getCurrentHp());
    }

    @Test
    void defenderShouldNotSelfHealWhenHasMoreArmorThatAttackerHasAttack(){
        Creature attacker = new Creature("c1",10,NOT_IMPORTANT,100, NOT_IMPORTANT);
        Creature defender = new Creature("c2", NOT_IMPORTANT,20,100,NOT_IMPORTANT);

        attacker.attack(defender);

        assertEquals(100,defender.getCurrentHp());
    }




}
