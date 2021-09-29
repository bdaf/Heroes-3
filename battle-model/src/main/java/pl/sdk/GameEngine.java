package pl.sdk;

import pl.sdk.creatures.Creature;
import pl.sdk.creatures.DecreaserShotsOfCreature;
import pl.sdk.creatures.TeamSetterForCreature;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final String VERSION = "1.0.2";
    public static int BOARD_WIDTH = 20;
    public static int BOARD_HEIGHT = 15;
    public static final String CURRENT_CREATURE_CHANGED = "CURRENT_CREATURE_CHANGED";
    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    public static final String UPDATE_AFTER_EVERY_TURN = "UPDATE_AFTER_EVERY_TURN";
    public static final String CURRENT_CREATURE_ATTACKED = "CURRENT_CREATURE_ATTACKED";
    private final Board board;
    private final CreatureTurnQueue queue;
    private PropertyChangeSupport observerSupport;

    public GameEngine(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        this(creaturesOnLeftSide, creaturesOnRightSide, new Board(), new Random());
    }

    GameEngine(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide, Board aBoard, Random randomizer) {
        board = aBoard;
        putCreaturesToBoard(creaturesOnLeftSide, creaturesOnRightSide);
        List<Creature> creaturesOnBothSides = new ArrayList<>();
        creaturesOnBothSides.addAll(creaturesOnLeftSide);
        creaturesOnBothSides.addAll(creaturesOnRightSide);
        sortBasedOnMovementSpeedAndThenRandomly(creaturesOnBothSides, randomizer);
        queue = new CreatureTurnQueue(creaturesOnBothSides);

        observerSupport = new PropertyChangeSupport(this);
        queue.addObserver(this);
        creaturesOnBothSides.forEach(c -> {
            addObserver(UPDATE_AFTER_EVERY_TURN, c);
            addObserver(CURRENT_CREATURE_CHANGED, c);
            addObserver(CREATURE_MOVED, c);
        });
    }

    private void sortBasedOnMovementSpeedAndThenRandomly(List<Creature> aCreaturesOnBothSides, Random aRandomizer) {
        aCreaturesOnBothSides.sort((aC1, aC2) -> {
            int result = Double.compare(aC2.getMoveRange(), aC1.getMoveRange());
            if (result != 0) return result;
            if (aRandomizer.nextBoolean()) return 1;
            else return -1;
        });
    }

    public void addObserver(String aEventType, PropertyChangeListener aObs) {
        observerSupport.addPropertyChangeListener(aEventType, aObs);
    }

    public void removeObserver(PropertyChangeListener aObs) {
        observerSupport.removePropertyChangeListener(aObs);
    }

    public void notifyObserver(PropertyChangeEvent aEvent) {
        observerSupport.firePropertyChange(aEvent);
    }

    private void putCreaturesToBoard(List<Creature> creaturesOnLeftSide, List<Creature> creaturesOnRightSide) {
        putCreatureFromOneSideToBoard(creaturesOnLeftSide, 0, Creature.Team.LEFT_TEAM);
        putCreatureFromOneSideToBoard(creaturesOnRightSide, BOARD_WIDTH - 1, Creature.Team.RIGHT_TEAM);
    }

    private void putCreatureFromOneSideToBoard(List<Creature> creatures, int x, Creature.Team aTeam) {
        for (int i = 0; i < creatures.size(); i++) {
            TeamSetterForCreature.setTeam(creatures.get(i), aTeam);
            board.add(new Point(x, (i * 2)), creatures.get(i));
        }
    }

    public void move(Point targetPoint) {
        Point oldPosition = board.get(getActiveCreature());
        double distance = board.moveAndReturnDistance(queue.getActiveCreature(), targetPoint);
        queue.setMovePointsOfActiveCreature(queue.getMovePointsOfActiveCreature() - distance);
        notifyObserver(new PropertyChangeEvent(this, CREATURE_MOVED, oldPosition, targetPoint));
        if (!canActiveCreatureDoAnyAction()) pass();
    }

    public void move(int aX, int aY){
        move(new Point(aX,aY));
    }

    public boolean canActiveCreatureDoAnyAction() {
        return getLeftMovePointsOfActiveCreature() >= 1 ||
                (getLeftAttacksInTurn() > 0 && (
                        getActiveCreature().getShots() > 0) ||
                        canAttackSomebodyAround()
                );
    }

    private boolean canAttackSomebodyAround() {
        Point point = board.get(getActiveCreature());
        int x = point.getX();
        int y = point.getY();
        return canAttack(x - 1, y)
                || canAttack(x - 1, y + 1)
                || canAttack(x, y + 1)
                || canAttack(x + 1, y + 1)
                || canAttack(x + 1, y)
                || canAttack(x + 1, y - 1)
                || canAttack(x, y - 1)
                || canAttack(x - 1, y - 1);
    }

    public void pass() {
        Creature oldCreature = queue.getActiveCreature();
        queue.next();
        Creature newCreature = queue.getActiveCreature();
        notifyObserver(new PropertyChangeEvent(this, CURRENT_CREATURE_CHANGED, oldCreature, newCreature));
    }

    public double getLeftMovePointsOfActiveCreature() {
        return queue.getMovePointsOfActiveCreature();
    }

    public void attack(int aX, int aY) {
        attackOnSplashedPoints(aX, aY);
        queue.setAttacksOfActiveCreature(queue.getAttacksOfActiveCreature() - 1);
        if (getLeftAttacksInTurn() <= 0)
            queue.setMovePointsOfActiveCreature(0);
        notifyObserver(new PropertyChangeEvent(this, CURRENT_CREATURE_ATTACKED, null, null));
        if (!canActiveCreatureDoAnyAction()) pass();
    }

    private void attackOnSplashedPoints(int aX, int aY) {
        if (board.get(getActiveCreature()).distance(new Point(aX, aY)) <= 1.5)
            getActiveCreature().meleeAttack(board.get(aX, aY));
        else {
            boolean damageToSplash[][] = getActiveCreature().getSplashDamage();
            for (int x = 0; x < damageToSplash.length; x++) {
                for (int y = 0; y < damageToSplash.length; y++) {
                    if (damageToSplash[x][y] && !ifGoesBehindBoard(aX + x - 1, aY + y - 1)) {
                        Creature potentialCreatureToAttack = board.get(aX + x - 1, aY + y - 1);
                        if (potentialCreatureToAttack != null && potentialCreatureToAttack.getTeam() != getActiveCreature().getTeam())
                            getActiveCreature().attack(board.get(aX + x - 1, aY + y - 1));
                    }
                }
            }
            DecreaserShotsOfCreature.decrease(getActiveCreature(), 1);
        }
    }

    private boolean ifGoesBehindBoard(int aX, int aY) {
        if (aX < 0 || aY < 0 || aX > BOARD_WIDTH - 1 || aY > BOARD_HEIGHT - 1)
            return true;
        return false;
    }

    public void attack(Point aPoint) {
        attack(aPoint.getX(), aPoint.getY());
    }

    public Creature get(int x, int y) {
        return board.get(x, y);
    }

    public Creature getActiveCreature() {
        return queue.getActiveCreature();
    }

    public boolean canMove(int aX, int aY) {
        double distance = board.countDistance(getActiveCreature(), aX, aY);
        return board.isFieldFreeToTake(aX, aY) && distance <= queue.getMovePointsOfActiveCreature();
    }

    public boolean canAttack(int aX, int aY) {
        if (get(aX, aY) == null)
            return false;
        boolean ifTheSameTeam = getActiveCreature().getTeam() == get(aX, aY).getTeam();
        return board.canAttack(getActiveCreature(), aX, aY) && queue.getAttacksOfActiveCreature() > 0 && !ifTheSameTeam;
    }

    void makeChangesOfNewTurn() {
        notifyObserver(new PropertyChangeEvent(this, UPDATE_AFTER_EVERY_TURN, null, null));
    }

    int getLeftAttacksInTurn() {
        return queue.getAttacksOfActiveCreature();
    }

    public boolean anyTeamWon() {
        return queue.ifAnyTeamWon();
    }

    public Creature.Team getWinnerTeam() {
        if(!anyTeamWon()) throw new IllegalStateException("Neither of teams won yet!");
        return queue.getAliveCreature().getTeam();
    }
}
