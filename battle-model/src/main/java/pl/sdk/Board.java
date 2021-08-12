package pl.sdk;

import pl.sdk.creatures.Creature;

import java.util.HashMap;
import java.util.Map;

import static pl.sdk.GameEngine.BOARD_HEIGHT;
import static pl.sdk.GameEngine.BOARD_WIDTH;

class Board {


    private Map<Point, Creature> map;

    Board() {
        map = new HashMap<>();
    }

    void add(Point aPoint, Creature creature) {
        throwExceptionWhenOutsideTheMap(aPoint);
        throwExceptionWhenTitleIsTaken(aPoint);
        map.put(aPoint, creature);
    }

    private void throwExceptionWhenOutsideTheMap(Point targetPoint) {
        if (targetPoint.getX() < 0 || targetPoint.getX() > BOARD_WIDTH || targetPoint.getY() < 0 || targetPoint.getY() > BOARD_HEIGHT) {
            throw new IllegalArgumentException("You are trying to go outside the map!");
        }
    }

    Creature get(int x, int y) {
        //if(map.get(new Point(x, y)) == null) throw new IllegalArgumentException();
        return map.get(new Point(x, y));
    }

    Point get(Creature aCreature) {
        return map.keySet().stream().filter(p -> map.get(p).equals(aCreature)).findAny().get();
    }

    double moveAndReturnDistance(Creature aCreature, Point targetPoint) {
        //throwExceptionWhenThereIsNoChampionToAct(aCreature, "There is no champion to move!");
        return moveAndReturnDistance(get(aCreature), targetPoint);
    }

    double moveAndReturnDistance(Point sourcePoint, Point targetPoint) {
        throwExceptionWhenOutsideTheMap(targetPoint);
        throwExceptionWhenThereIsNoChampionToAct(sourcePoint, "Creature isn't on board!");
        throwExceptionWhenTitleIsTaken(targetPoint);
        Creature creature = map.get(sourcePoint);

        double distance = sourcePoint.distance(targetPoint);

        map.remove(sourcePoint);
        map.put(targetPoint, creature);

        return distance;
    }

    private void throwExceptionWhenTitleIsTaken(Point aPoint) {
        if (isTitleTaken(aPoint))
            if (get(aPoint.getX(), aPoint.getY()).isAlive())
                throw new IllegalArgumentException("Title is taken!");
    }

    private boolean isTitleTaken(Point aPoint) {
        return map.containsKey(aPoint);
    }

    private void throwExceptionWhenThereIsNoChampionToAct(Creature aCreature, String msg) {
        if (!map.containsValue(aCreature)) {
            throw new IllegalArgumentException(msg);
        }
    }

    private void throwExceptionWhenThereIsNoChampionToAct(Point aPoint, String msg) {
        if (!map.containsValue(get(aPoint.getX(), aPoint.getY()))) {
            throw new IllegalArgumentException(msg);
        }
    }

    double countDistance(Creature aCreature, int aX, int aY) {
        throwExceptionWhenOutsideTheMap(new Point(aX, aY));
        throwExceptionWhenThereIsNoChampionToAct(aCreature, "Creature isn't on board!");
        Point currentPosition = get(aCreature);
        return currentPosition.distance(new Point(aX, aY));
    }

    boolean isFieldFreeToTake(int aX, int aY) {
        if (!isTitleTaken(new Point(aX, aY))) return true;
        return !get(aX, aY).isAlive();
    }

    boolean canAttack(Creature aCreature, int aX, int aY) {
        throwExceptionWhenThereIsNoChampionToAct(aCreature, "There is no champion to make attack!");
        Point currentPosition = get(aCreature);
        Creature potentiallyDefender = get(aX, aY);
        double distance = currentPosition.distance(new Point(aX, aY));
        return distance <= aCreature.getAttackRange() && isTitleTaken(new Point(aX, aY)) && potentiallyDefender.isAlive();

    }
}
