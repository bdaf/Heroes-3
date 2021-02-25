package pl.sdk;

import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.sdk.BlockingCreatureAndShootingCreatureAttackTest.BIGGER_THAN_DEFENDERS;
import static pl.sdk.BlockingCreatureAndShootingCreatureAttackTest.FEWER_THAN_DEFENDERS;
import static pl.sdk.Board.WIDTH;

public class RegenerationOnEndOfTurnTest {

    @BeforeEach
    void init(){

    }

    @Test
    void ShootingCreatureCanAttackEvenWithLargeDistance(){
        Creature attacker = new ShootingCreatureDecorator(new Creature.Builder()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .damage(Range.closed(1,1))
                .build());
        Creature defender = new RegenerationOnEndOfTurnCreatureDecorator(new Creature.Builder()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build());
        GameEngine gameEngine = new GameEngine(List.of(attacker), List.of(defender));
        assertTrue(gameEngine.canAttack(WIDTH-1,0));
        gameEngine.attack(WIDTH-1,0);
        assertEquals(999,defender.getCurrentHp());
        gameEngine.pass();
        gameEngine.pass();
        assertEquals(1000,defender.getCurrentHp());
    }
}
