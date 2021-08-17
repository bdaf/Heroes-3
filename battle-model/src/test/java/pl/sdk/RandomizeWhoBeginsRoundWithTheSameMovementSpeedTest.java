package pl.sdk;

import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomizeWhoBeginsRoundWithTheSameMovementSpeedTest {

    @Test
    void shouldRandomizeFromFirstArmyCreatureWhoBeginsRoundWithTheSameMovementSpeed(){
        Random randomizer = mock(Random.class);
        when(randomizer.nextBoolean()).thenReturn(true);

        Creature creatureFromFirstArmy = Factory.CreateDefaultCreatureForTests(5);
        Creature creatureFromSecondArmy = Factory.CreateDefaultCreatureForTests(5);
        GameEngine engine = new GameEngine(List.of(creatureFromFirstArmy),List.of(creatureFromSecondArmy),new Board(),randomizer);

        assertEquals(creatureFromFirstArmy,engine.getActiveCreature());
    }

    @Test
    void shouldRandomizeFromSecondArmyCreatureWhoBeginsRoundWithTheSameMovementSpeed(){
        Random randomizer = mock(Random.class);
        when(randomizer.nextBoolean()).thenReturn(false);

        Creature creatureFromFirstArmy = Factory.CreateDefaultCreatureForTests(5);
        Creature creatureFromSecondArmy = Factory.CreateDefaultCreatureForTests(5);
        GameEngine engine = new GameEngine(List.of(creatureFromFirstArmy),List.of(creatureFromSecondArmy),new Board(),randomizer);

        assertEquals(creatureFromSecondArmy,engine.getActiveCreature());
    }
}
