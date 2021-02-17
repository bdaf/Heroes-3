package pl.sdk;

import java.util.HashMap;
import java.util.Map;

 class Board {
    public static int WIDTH = 20;
    public static int HEIGHT = 15;

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
        if (targetPoint.getX() < 0 || targetPoint.getX() > WIDTH || targetPoint.getY() < 0 || targetPoint.getY() > HEIGHT) {
            throw new IllegalArgumentException("You are trying to go outside the map!");
        }
    }

    Creature get(int x, int y) {
        //if(map.get(new Point(x, y)) == null) throw new IllegalArgumentException();
        return map.get(new Point(x, y));
    }

    Point get(Creature aCreature){
        return map.keySet().stream().filter(p -> map.get(p).equals(aCreature)).findAny().get();
    }

    void move(Creature aCreature, Point targetPoint){
        //throwExceptionWhenThereIsNoChampionToAct(aCreature, "There is no champion to move!");
        move(get(aCreature),targetPoint);


    }

    void move(Point sourcePoint, Point targetPoint) {
        throwExceptionWhenOutsideTheMap(targetPoint);
        throwExceptionWhenThereIsNoChampionToAct(sourcePoint, "Creature isn't on board!");
        throwExceptionWhenTitleIsTaken(targetPoint);
        Creature creature = map.get(sourcePoint);

        double distance = sourcePoint.distance(targetPoint);
        creature.setCurrentMovePoints(creature.getCurrentMovePoints() - distance);

        map.remove(sourcePoint);
        map.put(targetPoint, creature);

    }

     private void throwExceptionWhenTitleIsTaken(Point aPoint) {
         if(isTitleTaken(aPoint))
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
        if (!map.containsValue(get(aPoint.getX(),aPoint.getY()))) {
            throw new IllegalArgumentException(msg);
        }
    }

     boolean canMove(Creature aCreature, int aX, int aY) {
        throwExceptionWhenOutsideTheMap(new Point(aX,aY));
        throwExceptionWhenThereIsNoChampionToAct(aCreature, "Creature isn't on board!");
        Point currentPosition = get(aCreature);
        double distance = currentPosition.distance(new Point(aX,aY));
        return distance <= aCreature.getCurrentMovePoints() && !isTitleTaken(new Point(aX,aY));
     }

     boolean canAttack(Creature aCreature, int aX, int aY) {
         throwExceptionWhenThereIsNoChampionToAct(aCreature, "There is no champion to make attack!");
         Point currentPosition = get(aCreature);
         Creature potentiallyDefender = get(aX,aY);
         double distance = currentPosition.distance(new Point(aX,aY));
         return distance <= 1.5 && isTitleTaken(new Point(aX,aY)) && potentiallyDefender.isAlive();

     }
 }
