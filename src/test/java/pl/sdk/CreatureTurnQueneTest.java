package pl.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTurnQueneTest {

    Creature a,b,c;
    CreatureTurnQuene creatureTurnQuene;
    Collection<Creature> creatureList;

    @BeforeEach
    void init(){
    a = new Creature();
    c = new Creature();
    b = new Creature();
    creatureList = new ArrayList<>();

    creatureList.add(a);
    creatureList.add(b);
    creatureList.add(c);
    }

    @Test
    void shouldCreatureABeInQueneAndAfterThatCreatureBThenC(){

        creatureTurnQuene = new CreatureTurnQuene(creatureList);

        assertEquals(a,creatureTurnQuene.getActiveCreature());
        creatureTurnQuene.next();
        assertEquals(b,creatureTurnQuene.getActiveCreature());
        creatureTurnQuene.next();
        assertEquals(c,creatureTurnQuene.getActiveCreature());
        creatureTurnQuene.next();
        assertEquals(a,creatureTurnQuene.getActiveCreature());



    }
}
