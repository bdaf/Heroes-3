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
        Creature attacker = new ShootingCreatureDecoratorW(new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .shots(12)
                .build());
        Creature defender = new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        GameEngine gameEngine = new GameEngine(List.of(attacker), List.of(defender));
        assertTrue(gameEngine.canAttack(BOARD_WIDTH-1,0));
        gameEngine.pass();
        assertFalse(gameEngine.canAttack(0,0));
    }


    @Test
    void NormalCreatureCanNotCounterAttackToShootingCreature(){
        Creature attacker = new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .amount(1)
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        attacker = new ShootingCreatureDecoratorW(attacker);
        attacker.attack(defender);
        assertEquals(1000,attacker.getCurrentHp());
        assertEquals(1,attacker.getAmount());
    }


    @Test
    void NormalCreatureCanNotCounterAttackToBlockingCounterAttackCreature(){
        Creature attacker = new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .amount(1)
                .build();
        Creature defender = new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build();
        attacker = new BlockingCounterAttackCreatureDecoratorW(attacker);
        attacker.attack(defender);
        assertEquals(1000,attacker.getCurrentHp());
        assertEquals(1,attacker.getAmount());
    }
}
