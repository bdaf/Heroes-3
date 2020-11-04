package pl.sdk;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Point, Creature> map;

    Board() {
        map = new HashMap<>();
    }

    void add(int x, int y, Creature creature) {
        if(map.containsKey(new Point(x,y))) {
        throw new IllegalArgumentException();
        }
        map.put(new Point(x,y),creature);
    }

    Creature get(int x, int y) {
        return map.get(new Point(x,y));
    }
}
