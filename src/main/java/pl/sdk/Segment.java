package pl.sdk;

import java.util.Objects;

public class Segment {
    private Point startPoint;
    private Point endPoint;

    public Segment(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", length="+this.countDistance() +
                '}';
    }

    public int countDistance() {
        int distanceX = startPoint.getX()-endPoint.getX();
        int distanceY = startPoint.getY()-endPoint.getY();

        return (int) Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return this.countDistance() == ((Segment) o).countDistance();
    }
    public boolean equalsLength(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(startPoint, segment.startPoint) &&
                Objects.equals(endPoint, segment.endPoint);
    }
    @Override
    public int hashCode() {
        return Objects.hash(startPoint, endPoint);
    }
}
