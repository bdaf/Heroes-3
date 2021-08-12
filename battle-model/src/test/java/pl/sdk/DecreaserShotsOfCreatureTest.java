package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;

import static org.junit.jupiter.api.Assertions.*;

class DecreaserShotsOfCreatureTest {
    @Test
    void shouldDecreaseShotsOfCreatureAbout1(){
        Creature creature = Factory.CreateShootingCreatureForTests();
        assertEquals(12,creature.getShots());
        DecreaserShotsOfCreature.decrease(creature,1);
        assertEquals(11,creature.getShots());
        DecreaserShotsOfCreature.decrease(creature,5);
        assertEquals(6,creature.getShots());
    }

}