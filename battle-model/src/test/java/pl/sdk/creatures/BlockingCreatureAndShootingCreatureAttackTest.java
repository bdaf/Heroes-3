package pl.sdk.creatures;

import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class BlockingCreatureAndShootingCreatureAttackTest {

    public static final int BIGGER_THAN_DEFENDERS = 10;
    public static final int FEWER_THAN_DEFENDERS = 2;




    @Test
    void ShootingCreatureCanAttackEvenWithLargeDistance(){
        Creature attacker = new ShootingCreatureDecorator(new Creature.Builder()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .build());
        Creature defender = new Creature.Builder()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        GameEngine gameEngine = new GameEngine(List.of(attacker), List.of(defender));
        boolean result = gameEngine.canAttack(BOARD_WIDTH-1,0);
        assertTrue(result);
        gameEngine.pass();
        assertFalse(gameEngine.canAttack(0,0));
    }


    @Test
    void NormalCreatureCanNotCounterAttackToShootingCreature(){
        Creature attacker = new Creature.Builder()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .amount(1)
                .build();
        Creature defender = new Creature.Builder()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        attacker = new ShootingCreatureDecorator(attacker);
        attacker.attack(defender);
        assertEquals(1000,attacker.getCurrentHp());
        assertEquals(1,attacker.getAmount());
    }


    @Test
    void NormalCreatureCanNotCounterAttackToBlockingCounterAttackCreature(){
        Creature attacker = new Creature.Builder()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .amount(1)
                .build();
        Creature defender = new Creature.Builder()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        attacker = new BlockingCounterAttackCreatureDecorator(attacker);
        attacker.attack(defender);
        assertEquals(1000,attacker.getCurrentHp());
        assertEquals(1,attacker.getAmount());
    }
}
