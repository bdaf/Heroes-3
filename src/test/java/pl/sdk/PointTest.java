package pl.sdk;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PointTest {
    @Test
    void distanceFromPointShouldBeFive() {
        //given
        Point point = new Point(3,4);
        Point point2 = new Point(6,8);
        // when
        double result = point.countDistanceFromXY();
        double result2 = point2.countDistanceFromXY();
        //then
        assertEquals(5.0,result);
        assertEquals(10.0,result2);
    }

    @Test
    void pointsShouldBeEquals(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,1);
        assertEquals(p1,p2);
    }

}
