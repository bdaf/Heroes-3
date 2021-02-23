package pl.sdk;

import java.util.*;

public class CreatureTurnQueue {

    private final Collection<Creature> creatures;
    private final Collection<Creature> creaturesToDelete;
    private final Queue<Creature> creatureQuene;
    private Creature activeCreature;
    private Set<GameEngine> observers;

    public CreatureTurnQueue(Collection<Creature> aCreatures) {
        creatures = aCreatures;
        creaturesToDelete = new LinkedList<>();
        creatureQuene = new LinkedList<>();
        observers = new HashSet<GameEngine>();
        initQueue();

    }

    private void initQueue() {
        removingDeadCreatures();
        creatureQuene.addAll(this.creatures);
        notifyObservers();
        next();
    }

    private void removingDeadCreatures() {
        for (Creature c : creatures)
            if(!c.isAlive())
                creaturesToDelete.add(c);
        for (Creature c : creaturesToDelete)
            creatures.remove(c);
        creaturesToDelete.clear();
    }

    void addObserver(GameEngine aObserver){
        observers.add(aObserver);
    }
    void removeObserver(GameEngine aObserver){ observers.remove((aObserver)); }
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
