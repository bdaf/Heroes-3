package pl.sdk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameEngine {


    public static final String CURRENT_CREATURE_CHANGED = "CURRENT_CREATURE_CHANGED";
    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    public static final String UPDATE_AFTER_EVERY_TURN = "UPDATE_AFTER_EVERY_TURN";
    public static final String CURRENT_CREATURE_ATTACKED = "CURRENT_CREATURE_ATTACKED";
    private final Board board;
    private final CreatureTurnQueue queue;
    private PropertyChangeSupport observerSupport;

    public GameEngine(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        this.board = new Board();
        putCreaturesToBoard(creaturesOnLeftSide, creaturesOnRightSide);
        List<Creature> creaturesOnBothSides = new ArrayList<>();
        creaturesOnBothSides.addAll(creaturesOnLeftSide);
        creaturesOnBothSides.addAll(creaturesOnRightSide);
        queue = new CreatureTurnQueue(creaturesOnBothSides);

        observerSupport = new PropertyChangeSupport(this);
        queue.addObserver(this);
        creaturesOnBothSides.forEach(c -> addObserver(UPDATE_AFTER_EVERY_TURN,c));

    }

    public void addObserver(String aEventType, PropertyChangeListener aObs){
        observerSupport.addPropertyChangeListener(aEventType, aObs);
    }

    public void removeObserver(PropertyChangeListener aObs){
        observerSupport.removePropertyChangeListener(aObs);
    }

    public void notifyObserver(PropertyChangeEvent aEvent){
        observerSupport.firePropertyChange(aEvent);
    }

    private void putCreaturesToBoard(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        putCreatureFromOneSideToBoard(creaturesOnLeftSide, 0);
        putCreatureFromOneSideToBoard(creaturesOnRightSide, Board.WIDTH-1);
    }

    private void putCreatureFromOneSideToBoard(List<Creature> creaturesOnRightSide, int x) {
        for (int i = 0; i < creaturesOnRightSide.size(); i++) {
            board.add(new Point(x, i * 2), creaturesOnRightSide.get(i));
        }
    }

    public void move(Point targetPoint){
        Point oldPosition = board.get(getActiveCreature());
        board.move(queue.getActiveCreature(),targetPoint);
        notifyObserver(new PropertyChangeEvent(this, CREATURE_MOVED, oldPosition,targetPoint));
        //pass();
    }

    public void pass(){
        Creature oldCreature = queue.getActiveCreature();
        queue.next();
        Creature newCreature = queue.getActiveCreature();
        notifyObserver(new PropertyChangeEvent(this, CURRENT_CREATURE_CHANGED, oldCreature, newCreature));

    }

    public void attack(int x, int y){
        Creature oldCreature = queue.getActiveCreature();
        queue.getActiveCreature().attack(board.get(x,y));
        Creature newCreature = queue.getActiveCreature();
        notifyObserver(new PropertyChangeEvent(this, CURRENT_CREATURE_ATTACKED, oldCreature, newCreature));
    }

    public void attack(Point aPoint) {
        attack(aPoint.getX(),aPoint.getY());
    }

    public Creature get(int x, int y) {
        return board.get(x,y);
    }

    public Creature getActiveCreature() {
        return queue.getActiveCreature();
    }

    public boolean canMove(int aX, int aY) {
        return board.canMove(getActiveCreature(), aX, aY);
    }

    public boolean canAttack(int aX, int aY) {
        return board.canAttack(getActiveCreature(), aX, aY);
    }

    void makeChangesOfNewTurn() {
        notifyObserver(new PropertyChangeEvent(this, UPDATE_AFTER_EVERY_TURN, null, null));
    }



}
