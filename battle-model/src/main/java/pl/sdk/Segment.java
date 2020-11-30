package pl.sdk;

import java.util.Objects;

 class Segment {
    private Point startPoint;
    private Point endPoint;

     Segment(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

     Point getStartPoint() {
        return startPoint;
    }

     void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

     Point getEndPoint() {
        return endPoint;
    }

     void setEndPoint(Point endPoint) {
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

     int countDistance() {
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
     boolean equalsLength(Object o) {
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
