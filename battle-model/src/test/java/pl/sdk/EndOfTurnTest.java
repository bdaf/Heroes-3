package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static pl.sdk.creatures.NecropolisFactory.CreateDefaultForTests;

public class EndOfTurnTest {

    @Test
    void shouldResetCounterAttackFlagAfterEndOfTurn(){
        Creature attacker = CreateDefaultForTests();
        Creature defender = CreateDefaultForTests();
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
        Creature attacker = CreateDefaultForTests(5);
        Creature defender = CreateDefaultForTests(1);
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        assertEquals(true, defender.canCounterAttack());
        assertEquals(1, engine.getAttacksInTurn());
        engine.attack(19,0);
        assertEquals(0, engine.getAttacksInTurn());
        assertEquals(false, defender.canCounterAttack());
        engine.pass();
        engine.pass();
        assertEquals(true, defender.canCounterAttack());
    }

    @Test
    void shouldResetAttackFlagAfterEndOfTurnBySpy(){
        Creature attacker = spy(Creature.class);
        Creature defender = CreateDefaultForTests();
        GameEngine engine = new GameEngine(List.of(attacker), List.of(defender));
        engine.pass();
        engine.pass();
        verify(attacker).propertyChange(any(PropertyChangeEvent.class));
    }
}
