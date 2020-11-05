package pl.sdk;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Point, Creature> map;

    Board() {
        map = new HashMap<>();
    }

    void add(Point point, Creature creature) {
        throwExceptionWhenFieldIsTaken(point);
        map.put(point, creature);
    }

    private void throwExceptionWhenFieldIsTaken(Point point) {
        if(map.containsKey(point)) {
        throw new IllegalArgumentException();
        }
    }

    Creature get(int x, int y) {
        return map.get(new Point(x,y));
    }


    void move(Point sourcePoint , Point targetPoint) {
        throwExceptionWhenFieldIsTaken(targetPoint);
        throwExceptionWhenThereIsNoChampionToMove(sourcePoint);
        Creature creature = map.get(sourcePoint);
        map.remove(sourcePoint);
        map.put(targetPoint,creature);
    }

    private void throwExceptionWhenThereIsNoChampionToMove(Point sourcePoint) {
        if(!map.containsKey(sourcePoint)){
            throw new IllegalArgumentException();
        }
    }
}
