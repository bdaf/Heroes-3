package pl.sdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class CreatureTurnQuene {

    Collection<Creature> creatures;
    Queue<Creature> creatureQuene;
    Creature activeCreature;

    public CreatureTurnQuene(Collection<Creature> creatures) {
        this.creatures = creatures;
        creatureQuene = new LinkedList<>();
        creatureQuene.addAll(creatures);
        next();
    }

    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {
        if(creatureQuene.isEmpty()){
            creatureQuene.addAll(creatures);
        }
        activeCreature = creatureQuene.poll();
    }
}
