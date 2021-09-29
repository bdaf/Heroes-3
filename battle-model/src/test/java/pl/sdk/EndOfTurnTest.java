package pl.sdk;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.sdk.GameEngine.*;
import static pl.sdk.creatures.NecropolisFactory.createDefaultCreatureForTests;

public class EndOfTurnTest {

    @Test
    void shouldResetCounterAttackFlagAfterEndOfTurn(){
        Creature attacker = NecropolisFactory.createDefaultCreatureForTests();
        Creature defender = NecropolisFactory.createDefaultCreatureForTests();
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
        Creature attacker = NecropolisFactory.createDefaultCreatureForTests(50);
        Creature defender = NecropolisFactory.createDefaultCreatureForTests(1);
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
        Creature defender = NecropolisFactory.createDefaultCreatureForTests();
        ArgumentCaptor<PropertyChangeEvent> argument = ArgumentCaptor.forClass(PropertyChangeEvent.class);

        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        engine.pass();
        engine.pass();

        verify(attacker, times(4)).propertyChange(argument.capture());
        assertEquals(BEGINNING_OF_GAME, argument.getAllValues().get(0).getPropertyName());
        assertEquals(CURRENT_CREATURE_CHANGED, argument.getAllValues().get(1).getPropertyName());
        assertEquals(UPDATE_AFTER_EVERY_TURN, argument.getAllValues().get(2).getPropertyName());
        assertEquals(CURRENT_CREATURE_CHANGED, argument.getAllValues().get(3).getPropertyName());
    }
}
