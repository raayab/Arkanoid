import java.util.List;
import java.util.Random;

/**
 * Line is class of line object.
 * A line contains start and end points (sometimes represented
 * by x1 and y1 (for the start point) and x2 and y2 (for the end point).
 */
public class Line {
    private double a1, a2, b1, b2, c1, c2, det;
    private double x1, y1, x2, y2;
    private double x, y;
    private Point start, end;
    private Point middle;

    /**
     *  Constructor of Line object.
     *  This constructor receives start and end points.
     *
     * @param start is the start point.
     * @param end is the end point.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
        this.x1 = start.getX();
        this.y1 = start.getY();
        this.x2 = end.getX();
        this.y2 = end.getY();
    }
    /**
     *  Constructor of Line object.
     *  This constructor receives x1 and y1 (the start point properties
     *  and x2 and y2 (the end point properties).
     *
     * @param x1 is the x property of the start point.
     * @param y1 is the y property of the start point.
     * @param x2 is the x property of the end point.
     * @param y2 is the y property of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.start = new Point(this.x1, this.y1);
        this.end = new Point(this.x2, this.y2);
    }

    /**
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * @return the middle point of the line.
     */
    public Point middle() {
        this.middle = new Point(((this.x1 + this.x2) / 2),
                        ((this.y1 + this.y2) / 2));
        return this.middle;
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

   /**
    * Assistant calculation for the intersection check
    * and for the intersection point.
    *
    * @param other is the other line to calculate his
    * parameters with this line.
    */
    public void calculateAssist(Line other) {
        this.a1 = this.y2 - this.y1;
        this.b1 = this.x1 - this.x2;
        double endY = other.end.getY();
        double startY = other.start.getY();
        double startX = other.start.getX();
        double endX = other.end.getX();
        this.a2 = endY - startY;
        this.b2 = startX - endX;
    }

    /**
     * Calculate the X and Y properties of the intersection point.
     */
    public void calculateIntersectionPoint() {
        this.x = (int) (((this.b2 * this.c1) - (this.b1 * this.c2))
                                / this.det);
        this.y = (int) (((this.a1 * this.c2) - (this.a2 * this.c1))
                                / this.det);
    }

    /**
     * Check if this Line and other Line which (received from
     * the function call), and check if they're intersecting.
     *
     * @param other is the other Line for comparing.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
       calculateAssist(other);
       this.det = (this.a1 * this.b2) - (this.a2 * this.b1);
       if (this.det != 0) {
           Point check = intersectionWith(other);
           return (check != null);
       } else { // If lines are parallel
           return false;
       }
    }

    /**
     * Calculate the intersection point if the lines intersect,
     * and null otherwise.
     *
     * @param other is the other Line for comparing.
     * @return the intersection point if the lines intersect,
     * and null otherwise.
     */
    public Point intersectionWith(Line other) {
        Line check = new Line(other.x1, other.y1, other.x2, other.y2);
        calculateAssist(other);
        this.c1 = (this.a1 * this.x1) + (this.b1 * this.y1);
        this.c2 = (this.a2 * check.x1) + (this.b2 * check.y1);
        this.det = (this.a1 * this.b2) - (this.a2 * this.b1);
        calculateIntersectionPoint();
        if (((this.x >= Math.min(this.x1, this.x2)
                        && this.x <= Math.max(this.x1, this.x2))
                && ((this.y >= Math.min(this.y1, this.y2)
                        && this.y <= Math.max(this.y1, this.y2))
                && (this.x >= Math.min(check.x1, check.x2)
                        && this.x <= Math.max(check.x1, check.x2)))
                && (this.y >= Math.min(check.y1, check.y2)
                        && this.y <= Math.max(check.y1, check.y2)))) {
            Point intersect = new Point((int) this.x, (int) this.y);
            return intersect;
        } else {
            return null;
        }
    }

    /**
     * Check if two lines are equals.
     *
     * @param other is the other line to compare with.
     * @return true is the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return (((this.start.equals(other.start))
                && (this.end.equals(other.end)))
                || ((this.start.equals(other.end))
                && (this.end.equals(other.start))));
    }

    /**
     * Generate new random line.
     *
     * @return a line which construct with random parameters.
     */
    public static Line generateRandomLine() {
        Random rand = new Random();
        double x1 = rand.nextInt(400) + 1; // get integer in range 1-400
        double y1 = rand.nextInt(300) + 1; // get integer in range 1-300
        double x2 = rand.nextInt(400) + 1; // get integer in range 1-400
        double y2 = rand.nextInt(300) + 1; // get integer in range 1-300
        Line randoomLine = new Line(x1, y1, x2, y2);

        return randoomLine;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rect is the rectangle with it we check a collide.
     * @return the closest intersection point to the
     *         start of the line, or null id there is'nt one.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interList = rect.intersectionPoints(this);
        if (interList.isEmpty()) {
            return null;
        }
        double minDistance = interList.get(0).distance(this.start);
        int minIndex = 0;
        for (int i = 1; i < interList.size(); i++) {
            if (minDistance > interList.get(i).distance(this.start)) {
                minDistance = interList.get(i).distance(this.start);
                minIndex = i;
            }
        }
        Point closest = new Point((int) interList.get(minIndex).getX(),
                (int) interList.get(minIndex).getY());
        return closest;
    }
}
