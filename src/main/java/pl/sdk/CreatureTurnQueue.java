package pl.sdk;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class CreatureTurnQueue {

    Collection<Creature> creatures;
    Queue<Creature> creatureQuene;
    Creature activeCreature;

    public CreatureTurnQueue(Collection<Creature> creatures) {
        this.creatures = creatures;
        creatureQuene = new LinkedList<>();
        initQueue();
    }

    private void initQueue() {
        this.creatures.stream().forEach(x -> x.setCounterAttackInThisTurn(false));
        creatureQuene.addAll(this.creatures);
        next();
    }

    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {
        if(creatureQuene.isEmpty()) initQueue();
        else activeCreature = creatureQuene.poll();
    }
}
