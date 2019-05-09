import java.util.ArrayList;
import java.util.List;


/**
 * A rectangle shape.
 */
public class Rectangle {
    private Point upperLeft;
    private double width, height;
    private Line left, right, up, down;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the location of the upper point of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
        this.left = new Line(upperLeft.getX(), upperLeft.getY(),
                            upperLeft.getX(), upperLeft.getY() + height);
        this.right = new Line(upperLeft.getX() + width, upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY() + height);
        this.up = new Line(upperLeft.getX(), upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY());
        this.down = new Line(upperLeft.getX(), upperLeft.getY() + height,
                upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line for intersection check.
     * @return a (possibly empty) List of intersection points
     *         with the line.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interList = new ArrayList<Point>();
        if (this.up.intersectionWith(line) != null) {
            interList.add(this.up.intersectionWith(line));
            }
        if (this.down.intersectionWith(line) != null) {
            interList.add(this.down.intersectionWith(line));
            }
        if (this.left.intersectionWith(line) != null) {
            interList.add(this.left.intersectionWith(line));
            }
         if (this.right.intersectionWith(line) != null) {
            interList.add(this.right.intersectionWith(line));
            }
         return interList;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * @return the height  of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    // my addition: return the rectangle lines.
    /**
     * @return the left line of the rectangle.
     */
    public Line getLeft() {
        return this.left;
    }
    /**
     * @return the right line of the rectangle.
     */
    public Line getRight() {
        return this.right;
    }
    /**
     * @return the upper line of the rectangle.
     */
    public Line getUp() {
        return this.up;
    }
    /**
     * @return the lower line of the rectangle.
     */
    public Line getDown() {
        return this.down;
    }
 }