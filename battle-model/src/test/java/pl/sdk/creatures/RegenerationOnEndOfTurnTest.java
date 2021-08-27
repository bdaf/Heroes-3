package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.sdk.GameEngine.BOARD_WIDTH;
import static pl.sdk.creatures.BlockingCreatureAndShootingCreatureAttackTest.BIGGER_THAN_DEFENDERS;
import static pl.sdk.creatures.BlockingCreatureAndShootingCreatureAttackTest.FEWER_THAN_DEFENDERS;

public class RegenerationOnEndOfTurnTest {

    @Test
    void ShootingCreatureCanAttackEvenWithLargeDistanceAndDefenderShouldRegenerateOnEndOfTurn(){
        Creature attacker = new ShootingCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(BIGGER_THAN_DEFENDERS)
                .damage(Range.closed(1,1))
                .shots(12)
                .build());
        Creature defender = new RegenerationOnEndOfTurnCreatureDecorator(new Creature.BuilderForTesting()
                .maxHp(1000)
                .moveRange(FEWER_THAN_DEFENDERS)
                .build());
        GameEngine gameEngine = new GameEngine(List.of(attacker), List.of(defender));
        assertTrue(gameEngine.canAttack(BOARD_WIDTH-1,0));
        gameEngine.attack(BOARD_WIDTH-1,0);
        assertEquals(999,defender.getCurrentHp());
        gameEngine.pass();
        assertEquals(1000,defender.getCurrentHp());
    }
}
