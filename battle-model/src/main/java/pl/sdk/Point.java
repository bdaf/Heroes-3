package pl.sdk;

import java.util.Objects;

 public class Point {

    final private int x;
    final private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

     int getX() {
        return x;
    }

     int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    double countDistanceFromX0andY0() {
        return Math.sqrt(x*x+y*y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

     double distance(Point aPoint) {
        double tmpX = x-aPoint.getX();
        double tmpY = y-aPoint.getY();
         return Math.sqrt(tmpX*tmpX + tmpY*tmpY);
     }
 }
