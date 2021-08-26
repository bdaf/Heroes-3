package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomaticPassTest {
    @Test
    void shouldPassWhenHeMovedAndCannotAttack() {
        Creature c1 = Factory.CreateDefaultCreatureForTests(2);
        Creature c2 = Factory.CreateDefaultCreatureForTests(1);
        GameEngine gameEngine = new GameEngine(List.of(c1), List.of(c2));
        assertEquals(gameEngine.getActiveCreature(),c1);
        gameEngine.move(new Point(2, 0));
        assertEquals(gameEngine.getActiveCreature(),c2);
    }

    @Test
    void shouldNotPassWhenHeMovedAndCanAttack() {
        Creature c1 = Factory.CreateDefaultCreatureForTests(100);
        Creature c2 = Factory.CreateDefaultCreatureForTests(1);
        GameEngine gameEngine = new GameEngine(List.of(c1), List.of(c2));
        assertEquals(gameEngine.getActiveCreature(),c1);
        gameEngine.move(new Point(GameEngine.BOARD_WIDTH-2, 0));
        assertEquals(gameEngine.getActiveCreature(),c1);
    }
}
