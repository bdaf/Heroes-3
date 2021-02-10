package pl.sdk;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class EndOfTurnTest {

    @Test
    void shouldResetCounterAttackFlagAfterEndOfTurn(){
        Creature attacker = new Creature();
        Creature defender = new Creature();
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
        Creature attacker = new Creature();
        Creature defender = new Creature();
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        assertEquals(true, defender.canCounterAttack());
        assertEquals(1, attacker.getAttacksInTurn());
        engine.attack(19,0);
        assertEquals(0, attacker.getAttacksInTurn());
        assertEquals(false, defender.canCounterAttack());
        engine.pass();
        engine.pass();
        assertEquals(true, defender.canCounterAttack());
    }

    @Test
    void shouldResetAttackFlagAfterEndOfTurnBySpy(){
        Creature attacker = spy(Creature.class);
        Creature defender = new Creature();
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        engine.pass();
        engine.pass();
        verify(attacker).propertyChange(any(PropertyChangeEvent.class));
    }
}
