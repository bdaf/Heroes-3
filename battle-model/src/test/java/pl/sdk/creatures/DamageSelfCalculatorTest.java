package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.sdk.GameEngine.BOARD_WIDTH;

public class DamageSelfCalculatorTest {

    public static final int THE_SAME_ATTACK_AND_ARMOR = 5;

    @Test
    void attackerShouldLose50PercentOfDamageHeDeals(){
        Creature defender = new Creature.BuilderForTesting()
                .attack(THE_SAME_ATTACK_AND_ARMOR)
                .armor(THE_SAME_ATTACK_AND_ARMOR)
                .moveRange(55)
                .damage(Range.closed(6,6))
                .maxHp(100)
                .build();
        Creature attacker = new ShootingCreatureDecorator(new SelfHealingCreatureDecorator(new Creature.BuilderForTesting()
                .attack(THE_SAME_ATTACK_AND_ARMOR)
                .armor(THE_SAME_ATTACK_AND_ARMOR)
                .moveRange(54)
                .damage(Range.closed(6,6))
                .maxHp(100)
                .build(),-0.5));
        GameEngine gameEngine = new GameEngine(List.of(defender),List.of(attacker));
        gameEngine.move(new Point(BOARD_WIDTH-1,1));
        gameEngine.pass();
        assertEquals(true,gameEngine.canAttack(BOARD_WIDTH-1,1));
        gameEngine.attack(BOARD_WIDTH-1,1);
        assertEquals(91,attacker.getCurrentHp());
        assertEquals(94,defender.getCurrentHp());

    }
}
