


/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 */
public class Velocity {
    private double dx;
    private double dy;
    private Point newPoint;

    /**
     *construct Velocity by two given distances.
     *
     * @param dx is reference to the distance between the x of the current point
     * to the x of the new point.
     * @param dy is reference to the distance between the y of the current point
     * to the y of the new point.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     *
     * @return dx,the distance between the x of the current point
     * to the x of the new point.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     *
     * @return dy,the distance between the y of the current point
     * to the y of the new point.;
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * the function receives a point with position (x,y),
     * and return a new point with position (x+dx, y+dy).
     *
     * @param p is reference to the current point with position (x,y)
     * @return new point with position (x+dx, y+dy)
     * @param dt uses to calculate versus the frame rate.
     */
    public Point applyToPoint(Point p, Double dt) {
        this.newPoint = new Point((int) (p.getX() + (this.dx * dt)),
                                    (int) (p.getY() + (this.dy * dt)));
        return newPoint;
    }

    /**
     * constructor to Velocity, taking an angle an a speed,
     * and return velocity by dx and dy.
     *
     * @param angle is reference to the angle of the velocity;
     * @param speed is reference to the speed of the velocity
     * @return velocity created according to the given speed and angle
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = angle - 180;
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = speed * Math.cos(angle);
        return new Velocity((int) (dx), (int) (dy));
    }
    /**
    * @return the angle according the dy and dx.
    */
    public double angleFromDxDy() {
        return Math.atan(this.dx / this.dy);
    }
    /**
    * @return the speed according the dy and dx.
    */
    public double speedFromDxDy() {
        return Math.round(Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2))
                            + Math.abs(this.angleFromDxDy()));
    }
}

