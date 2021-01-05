package pl.sdk;

import java.util.*;

public class CreatureTurnQueue {

    private final Collection<Creature> creatures;
    private final Queue<Creature> creatureQuene;
    private Creature activeCreature;
    private Set<GameEngine> observers;

    public CreatureTurnQueue(Collection<Creature> aCreatures) {
        creatures = aCreatures;
        creatureQuene = new LinkedList<>();
        observers = new HashSet<GameEngine>();
        initQueue();

    }

    private void initQueue() {
        creatureQuene.addAll(this.creatures);
        notifyObservers();
        next();
    }

    void addObserver(GameEngine aObserver){
        observers.add(aObserver);
    }
    void removeObserver(GameEngine aObserver){
        observers.remove((aObserver));
    }
    void notifyObservers(){
        observers.forEach(GameEngine::makeChangesOfNewTurn);
    }


    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {
        if(creatureQuene.isEmpty()) initQueue();
        else activeCreature = creatureQuene.poll();
    }
}
