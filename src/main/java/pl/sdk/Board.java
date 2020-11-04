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


    void move(Point sourcePoint , Point targetPoint) {
        if(!map.containsValue(map.get(sourcePoint)) || map.get(sourcePoint)!=null){
            throw new IllegalArgumentException();
        }
        Creature creature = map.get(sourcePoint);
        map.remove(sourcePoint);
        map.put(targetPoint,creature);
    }
}
