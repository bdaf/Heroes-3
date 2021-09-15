package pl.sdk;

import pl.sdk.creatures.Creature;

import java.util.*;

public class CreatureTurnQueue {

    private final List<Creature> aliveCreatures;
    private final List<Creature> creaturesToDelete;
    private final Queue<Creature> creatureQueue;
    private Creature activeCreature;
    private Set<GameEngine> observers;
    private double movePointsOfActiveCreature;
    private int attacksOfActiveCreature;

    public CreatureTurnQueue(List<Creature> aAliveCreatures) {
        aliveCreatures = aAliveCreatures;
        creaturesToDelete = new LinkedList<>();
        creatureQueue = new LinkedList<>();
        observers = new HashSet<GameEngine>();
        initQueue();

    }

    private void initQueue() {
        removingDeadCreatures();
        creatureQueue.addAll(this.aliveCreatures);
        notifyObservers();
        next();
    }

    private void removingDeadCreatures() {
        for (Creature c : aliveCreatures)
            if(!c.isAlive())
                creaturesToDelete.add(c);
        for (Creature c : creaturesToDelete)
            aliveCreatures.remove(c);
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
        if(creatureQueue.isEmpty())
            initQueue();
        else {
            activeCreature = creatureQueue.poll();
            movePointsOfActiveCreature = activeCreature.getMoveRange();
            attacksOfActiveCreature = activeCreature.getMaxAttacksInTurn();
            if(!activeCreature.isAlive())
                next();
        }
    }

    double getMovePointsOfActiveCreature() {
        return movePointsOfActiveCreature;
    }

    void setMovePointsOfActiveCreature(double aMovePointsOfActiveCreature) {
        movePointsOfActiveCreature = aMovePointsOfActiveCreature;
    }

    int getAttacksOfActiveCreature() {
        return attacksOfActiveCreature;
    }

    void setAttacksOfActiveCreature(int aAttacksOfActiveCreature) {
        attacksOfActiveCreature = aAttacksOfActiveCreature;
    }

    boolean ifAnyTeamWon() {
        removingDeadCreatures();
        if(!aliveCreatures.isEmpty()){
            Creature.Team team = aliveCreatures.get(0).getTeam();
            for (int i = 0; i < aliveCreatures.size(); i++) {
                if(aliveCreatures.get(i).getTeam() != team) return false;
            }
        }
        return true;
    }

    Creature.Team getAliveCreature() {
        removingDeadCreatures();
        if(aliveCreatures.isEmpty()) throw new IllegalStateException("There is no living creatures!");
        return aliveCreatures.get(0).getTeam();
    }
}
