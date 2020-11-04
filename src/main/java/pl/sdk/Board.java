package pl.sdk;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Point, Creature> map;

    Board() {
        map = new HashMap<>();
    }

    void add(Point point, Creature creature) {
        if(map.containsKey(point)) {
        throw new IllegalArgumentException();
        }
        map.put(point, creature);
    }

    Creature get(int x, int y) {
        return map.get(new Point(x,y));
    }

    void move(Creature creature, int x, int y) {
        if(!map.containsValue(creature)){
            throw new IllegalArgumentException();
        }
        map.remove(creature);
        map.put(new Point(x,y),creature);
    }

    void move(int xFrom, int yFrom, int xTo, int yTo) {
        if(!map.containsKey(new Point(xFrom,yFrom))){
            throw new IllegalArgumentException();
        }
        Creature creature = map.get(new Point(xFrom,yFrom));
        map.remove(new Point(xFrom,yFrom),creature);
        map.put(new Point(xTo,yTo),creature);
    }
}
