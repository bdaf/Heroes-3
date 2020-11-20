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

    void add(Point point, Creature creature) {
        throwExceptionWhenFieldIsTakenOrOutsideTheMap(point);
        map.put(point, creature);
    }

    private void throwExceptionWhenFieldIsTakenOrOutsideTheMap(Point targetPoint) {
        if (map.containsKey(targetPoint) || targetPoint.getX() < 0 || targetPoint.getX() > WIDTH || targetPoint.getY() < 0 || targetPoint.getY() > HEIGHT) {
            throw new IllegalArgumentException();
        }
    }

    Creature get(int x, int y) {
        if(map.get(new Point(x, y)) == null) throw new IllegalArgumentException();
        return map.get(new Point(x, y));
    }

    Point get(Creature creature){
        return map.keySet().stream().filter(p -> map.get(p).equals(creature)).findAny().get();
    }

    void move(Creature creature, Point targetPoint){
        move(get(creature),targetPoint);
    }

    void move(Point sourcePoint, Point targetPoint) {
        throwExceptionWhenFieldIsTakenOrOutsideTheMap(targetPoint);
        throwExceptionWhenThereIsNoChampionToMove(sourcePoint);
        Creature creature = map.get(sourcePoint);
        map.remove(sourcePoint);
        map.put(targetPoint, creature);
    }

    private void throwExceptionWhenThereIsNoChampionToMove(Point sourcePoint) {
        if (!map.containsKey(sourcePoint)) {
            throw new IllegalArgumentException();
        }
    }


}
