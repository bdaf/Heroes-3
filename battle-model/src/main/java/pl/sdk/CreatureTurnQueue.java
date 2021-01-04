package pl.sdk;

import java.util.*;

public class CreatureTurnQueue {

    private final Collection<Creature> creatures;
    private final Queue<Creature> creatureQuene;
    private Creature activeCreature;
    private Set<Creature> observers;

    public CreatureTurnQueue(Collection<Creature> aCreatures) {
        creatures = aCreatures;
        creatureQuene = new LinkedList<>();
        observers = new HashSet<Creature>();
        initQueue();

    }

    private void initQueue() {
        creatureQuene.addAll(this.creatures);
        notifyObservers();
        next();
    }

    void addObserver(Creature aObserver){
        observers.add(aObserver);
    }
    void removeObserver(Creature aObserver){
        observers.remove((aObserver));
    }
    void notifyObservers(){
        observers.forEach(o -> o.update());
    }


    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {
        if(creatureQuene.isEmpty()) initQueue();
        else activeCreature = creatureQuene.poll();
    }
}
