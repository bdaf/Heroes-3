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
        initQuene();
    }

    private void initQuene() {
        creatureQuene.addAll(this.creatures);
        next();
    }

    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {
        if(creatureQuene.isEmpty()) initQuene();
        else activeCreature = creatureQuene.poll();
    }
}
