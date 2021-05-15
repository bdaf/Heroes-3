package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.NecropolisFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {

    @Test
    void creatureCannotAttackHisAlly(){
        NecropolisFactory factory = new NecropolisFactory();

        List<Creature> l1 = List.of(factory.Create(true,5,1),NecropolisFactory.CreateDefaultCreatureForTests(1));
        List<Creature> l2 = List.of(NecropolisFactory.CreateDefaultCreatureForTests(1));

        GameEngine engine = new GameEngine(l1,l2);

        boolean yes = engine.canAttack(GameEngine.BOARD_WIDTH-1,0);
        assertTrue(yes);
        assertFalse(engine.canAttack(0,0));
        assertFalse(engine.canAttack(0,2));
    }
}
