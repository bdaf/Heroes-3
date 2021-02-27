package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdk.creatures.Creature;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTurnQueueTest {

    Creature a,b,c;
    CreatureTurnQueue creatureTurnQueue;
    Collection<Creature> creatureList;

    @BeforeEach
    void init(){
    a = new Creature.Builder().build();
    c = new Creature.Builder().build();
    b = new Creature.Builder().build();
    creatureList = new ArrayList<>();

    creatureList.add(a);
    creatureList.add(b);
    creatureList.add(c);
    }

    @Test
    void shouldCreatureABeInQueneAndAfterThatCreatureBThenC(){

        creatureTurnQueue = new CreatureTurnQueue(creatureList);

        assertEquals(a, creatureTurnQueue.getActiveCreature());
        creatureTurnQueue.next();
        assertEquals(b, creatureTurnQueue.getActiveCreature());
        creatureTurnQueue.next();
        assertEquals(c, creatureTurnQueue.getActiveCreature());
        creatureTurnQueue.next();
        assertEquals(a, creatureTurnQueue.getActiveCreature());



    }
}
