package pl.sdk.creatures;

import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoubleAttackTest {

    @Test
    void ShootingCreatureShouldAttackTwiceAndDefenderShouldNotCounterAttack(){
        Creature attacker = CastleFactory.CreateDoubleAttackCreatureDefaultForTests(5);
        Creature defender = spy(Creature.class);
        GameEngine engine = new GameEngine(List.of(attacker),List.of(defender));

        engine.canAttack(GameEngine.BOARD_WIDTH-1,0);
        engine.attack(GameEngine.BOARD_WIDTH-1,0);

        assertEquals(100,attacker.getCurrentHp());
        verify(defender,never()).countDamage(any(),any());
        verify(defender,atLeast(2)).applyDamage(anyInt());
        verify(defender,atMost(2)).applyDamage(anyInt());
    }

    @Test
    void NotShootingCreatureShouldAttackTwiceAndDefenderShouldCounterAttackOnce(){
        Creature attacker = CastleFactory.CreateDoubleAttackCreatureDefaultForTests(5);
        Creature defender = spy(Creature.class);

        attacker.attack(defender);

        assertEquals(100,attacker.getCurrentHp());
        verify(defender,never()).countDamage(any(),any());
        verify(defender,atLeast(2)).applyDamage(anyInt());
        verify(defender,atMost(2)).applyDamage(anyInt());
    }
}
