import org.junit.jupiter.api.Test;
import pl.sdk.Point;
import pl.sdk.Segment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTest {
    @Test
    void segmentsShouldBeEquals(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(3,1);
        Point p3 = new Point(1,3);

        Segment s1 = new Segment(p1,p2);
        Segment s2 = new Segment(p1,p3);

        int result1 = s1.countDistance();
        int result2 = s2.countDistance();
        assertEquals(result1,result2);
       // p1.setX(5);
        // result1 = s1.countDistance();
         //result2 = s2.countDistance();
        assertEquals(s1,s2);

    }

}
