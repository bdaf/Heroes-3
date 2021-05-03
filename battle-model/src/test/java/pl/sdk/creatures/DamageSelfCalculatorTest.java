package pl.sdk.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.sdk.GameEngine;
import pl.sdk.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageSelfCalculatorTest {

    public static final int THE_SAME_ATTACK_AND_ARMOR = 5;

    @Test
    void attackerShouldLose50PercentOfDamageHeDeals(){
        Creature attacker = new ShootingCreatureDecorator(new SelfHealingCreatureDecorator(new Creature.Builder()
                .attack(THE_SAME_ATTACK_AND_ARMOR)
                .armor(THE_SAME_ATTACK_AND_ARMOR)
                .moveRange(54)
                .damage(Range.closed(6,6))
                .maxHp(100)
                .build(),-0.5));
        Creature defender = new Creature.Builder()
                .attack(THE_SAME_ATTACK_AND_ARMOR)
                .armor(THE_SAME_ATTACK_AND_ARMOR)
                .moveRange(55)
                .damage(Range.closed(6,6))
                .maxHp(100)
                .build();
        GameEngine gameEngine = new GameEngine(List.of(defender),List.of(attacker));
        gameEngine.move(new Point(19,1));
        gameEngine.pass();
        if(gameEngine.canAttack(19,1))
            gameEngine.attack(19,1);
        assertEquals(91,attacker.getCurrentHp());
        assertEquals(94,defender.getCurrentHp());

    }
}
