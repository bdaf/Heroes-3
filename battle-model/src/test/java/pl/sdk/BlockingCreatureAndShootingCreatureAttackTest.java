package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static pl.sdk.Board.WIDTH;

public class BlockingCreatureAndShootingCreatureAttackTest {

    public static final int BIGGER_THAN_DEFENDERS = 10;
    private static final int FEWER_THAN_DEFENDERS = 2;
    private Creature attacker;
    private Creature defender;
    private GameEngine gameEngine;

    @BeforeEach
    void init(){
        Creature attacker = new ShootingCreature.Builder()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .build();
        Creature defender = new Creature.Builder()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        gameEngine = new GameEngine(List.of(attacker), List.of(defender));
    }

    @Test
    void ShootingCreatureCanAttackEvenWithLargeDistance(){
        assertTrue(gameEngine.canAttack(WIDTH-1,0));
        gameEngine.pass();
        assertFalse(gameEngine.canAttack(0,0));
    }

    @Test
    void NormalCreatureCanNotCounterAttackToShootingCreature(){
        attacker = spy(ShootingCreature.class);
        defender = spy(Creature.class);

        attacker.attack(defender);
        verify(defender,atMost(1)).applyDamage(anyInt());
        verify(attacker,atMost(0)).applyDamage(anyInt());

        defender.attack(attacker);
        verify(defender,atMost(2)).applyDamage(anyInt());
        verify(attacker,atMost(1)).applyDamage(anyInt());
    }
    @Test
    void NormalCreatureCanNotCounterAttackToBlockingCounterAttackCreature(){
        defender = spy(Creature.class);
        attacker = spy(BlockingCounterAttackCreature.class);

        attacker.attack(defender);
        verify(defender,atMost(1)).applyDamage(anyInt());
        verify(attacker,atMost(0)).applyDamage(anyInt());

        defender.attack(attacker);
        verify(defender,atMost(2)).applyDamage(anyInt());
        verify(attacker,atMost(1)).applyDamage(anyInt());
    }
}
