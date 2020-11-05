package pl.sdk;

import java.util.HashMap;
import java.util.Map;

public class Board {
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



    private void throwExceptionWhenFieldIsTakenOrOutsideTheMap(Point point) {
        if(map.containsKey(point) || point.getX()<0 || point.getX()>WIDTH || point.getY()<0 || point.getY()> HEIGHT) {
        throw new IllegalArgumentException();
        }
    }

    Creature get(int x, int y) {
        return map.get(new Point(x,y));
    }


    void move(Point sourcePoint , Point targetPoint) {
        throwExceptionWhenFieldIsTakenOrOutsideTheMap(targetPoint);
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
