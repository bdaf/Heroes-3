package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static pl.sdk.GameEngine.BOARD_WIDTH;
import static pl.sdk.creatures.NecropolisFactory.CreateDefaultCreatureForTests;

public class EndOfTurnTest {

    @Test
    void shouldResetCounterAttackFlagAfterEndOfTurn(){
        Creature attacker = NecropolisFactory.CreateDefaultCreatureForTests();
        Creature defender = NecropolisFactory.CreateDefaultCreatureForTests();
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));

        assertEquals(true, defender.canCounterAttack());
        attacker.attack(defender);
        assertEquals(false, defender.canCounterAttack());

        engine.pass();
        engine.pass();
        assertEquals(true, defender.canCounterAttack());
    }

    @Test
    void shouldResetAttackFlagAfterEndOfTurn(){
        Creature attacker = CreateDefaultCreatureForTests(50);
        Creature defender = CreateDefaultCreatureForTests(1);
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));

        assertEquals(true, defender.canCounterAttack()); // attacker
        assertEquals(1, engine.getLeftAttacksInTurn());
        engine.move(new Point(BOARD_WIDTH-2,0));
        assertTrue(engine.canAttack(BOARD_WIDTH-1,0));
        assertEquals(1, engine.getLeftAttacksInTurn());
        engine.attack(BOARD_WIDTH-1,0);

        assertEquals(false, defender.canCounterAttack()); // defender
        engine.pass();
        assertEquals(true, defender.canCounterAttack());
    }

    @Test
    void shouldResetAttackFlagAfterEndOfTurnBySpy(){
        Creature attacker = spy(Creature.class);
        Creature defender = NecropolisFactory.CreateDefaultCreatureForTests();
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        engine.pass();
        engine.pass();
        verify(attacker).propertyChange(any(PropertyChangeEvent.class));
    }
}
