package pl.sdk.creatures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaknessesTest {

    @Test
    void defenderShouldHave2PointsLessAttackAndDefenseFor3Rounds(){
        Creature attacker = new Creature.BuilderForTesting()
                .maxHp(100).build();
        Creature defender = new Creature.BuilderForTesting()
                .attack(5)
                .armor(5)
                .maxHp(100).build();

        attacker.attack(defender);

        assertEquals(3,defender.getAttack());
        assertEquals(3,defender.getDefense());
    }
}
