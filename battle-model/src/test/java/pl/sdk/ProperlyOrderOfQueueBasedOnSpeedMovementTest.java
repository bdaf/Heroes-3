package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.CastleFactory;
import pl.sdk.creatures.Creature;
import pl.sdk.creatures.Factory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProperlyOrderOfQueueBasedOnSpeedMovementTest {

    private Factory factory;
    private Creature[] creatures;
    private List leftSideList;
    private List rightSideList;

    @BeforeEach
    void init(){
        factory = new CastleFactory();
        creatures = new Creature[14];
        leftSideList = new ArrayList();
        rightSideList = new ArrayList();
    }
    @Test //It works anyway because I put this in the same order of movement speed like ordinary order
    void orderOfQueueBasedOnSpeedMovementShouldBeProperly(){
        for (int i = 0; i < 14; i++) {
            creatures[i] = factory.CreateDefaultCreatureForTests(15-i);
            if(i<7) leftSideList.add(creatures[i]);
            else rightSideList.add(creatures[i]);
        }
        GameEngine engine = new GameEngine(leftSideList,rightSideList);

        for (int i = 0; i < 14; i++) {
            assertEquals(creatures[i],engine.getActiveCreature());
            engine.pass();
        }
    }

    @Test
    void orderOfQueueBasedOnSpeedMovementWithoutOrdinaryOrderShouldBeProperly(){
        creatures[0] = factory.CreateDefaultCreatureForTests(6);
        creatures[1] = factory.CreateDefaultCreatureForTests(4);
        creatures[2] = factory.CreateDefaultCreatureForTests(12);
        creatures[3] = factory.CreateDefaultCreatureForTests(13);
        creatures[4] = factory.CreateDefaultCreatureForTests(11);
        creatures[5] = factory.CreateDefaultCreatureForTests(9);
        creatures[6] = factory.CreateDefaultCreatureForTests(1);
        creatures[7] = factory.CreateDefaultCreatureForTests(2);
        creatures[8] = factory.CreateDefaultCreatureForTests(10);
        creatures[9] = factory.CreateDefaultCreatureForTests(5);
        creatures[10] = factory.CreateDefaultCreatureForTests(3);
        creatures[11] = factory.CreateDefaultCreatureForTests(14);
        creatures[12] = factory.CreateDefaultCreatureForTests(7);
        creatures[13] = factory.CreateDefaultCreatureForTests(8);

        for (int i = 0; i < 14; i++) {
            if(i<7) leftSideList.add(creatures[i]);
            else rightSideList.add(creatures[i]);
        }

        GameEngine engine = new GameEngine(leftSideList,rightSideList);

        assertEquals(creatures[11],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[3],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[2],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[4],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[8],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[5],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[13],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[12],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[0],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[9],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[1],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[10],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[7],engine.getActiveCreature()); engine.pass();
        assertEquals(creatures[6],engine.getActiveCreature()); engine.pass();

    }
}
