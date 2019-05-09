import java.awt.geom.Line2D;


/**
 * Point object properties include x and y.
 *
 */
public class Point {
    private double x;
    private double y;
    /**
     * Pointer constructor.
     * Receives X and Y and insert them into the object's properties.
     * @param x is the x prop.
     * @param y is the y prop.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @return the X of the point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * @return the Y of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Calculating the distance between two points.
     *
     * @param other is the other point to compare with.
     * @return the distance.
     */
    public double distance(Point other) {
        double disX = this.x - other.getX();
        double disY = this.y - other.getY();
        return Math.sqrt((disX * disX) + (disY * disY));
    }

    /**
     * Check if two points are similar.
     *
     * @param other is the other point to compare with.
     * @return true if equals, false otherwise.
     */
    public boolean equals(Point other) {
        Point check = new Point(other.x, other.y);
        return (this.x == check.getX() && this.y == check.getY());
    }
    /**
     * Check if this point is on a line.
     *
     * @param line is the line we check.
     * @return  true id this point is on the line,
     *          false otherwise.
     */
    public boolean isPointOnLine(Line line) {
        if (Line2D.ptSegDist(line.start().getX(), line.start().getY(),
                line.end().getX(), line.end().getY(),
                Math.round(this.getX()), Math.round(this.getY())) < 0.1) {
            return true;
        }
        return false;
    }
}
