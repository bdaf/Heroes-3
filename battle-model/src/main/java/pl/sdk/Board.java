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
        throwExceptionWhenOutsideTheMap(targetPoint);
        throwExceptionWhenThereIsNoChampionToMove(aCreature);
        move(get(aCreature),targetPoint);
    }

    void move(Point sourcePoint, Point targetPoint) {
        throwExceptionWhenThereIsNoChampionToMove(sourcePoint);
        throwExceptionWhenTitleIsTaken(targetPoint);
        Creature creature = map.get(sourcePoint);
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

     private void throwExceptionWhenThereIsNoChampionToMove(Creature aCreature) {
        if (!map.containsValue(aCreature)) {
            throw new IllegalArgumentException("There is no champion to move!");
        }
    }
    private void throwExceptionWhenThereIsNoChampionToMove(Point aPoint) {
        if (!map.containsValue(get(aPoint.getX(),aPoint.getY()))) {
            throw new IllegalArgumentException("There is no champion to move!");
        }
    }

     boolean canMove(Creature aCreature, int aX, int aY) {
        throwExceptionWhenOutsideTheMap(new Point(aX,aY));
        throwExceptionWhenThereIsNoChampionToMove(aCreature);
        Point currentPosition = get(aCreature);
        double distance = currentPosition.distance(new Point(aX,aY));
        return distance <= aCreature.getStats().getMoveRange() && !isTitleTaken(new Point(aX,aY));
     }
 }
