

/**
 * Interface for Collidable object.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint the point which the collision occurred at.
     * @param currentVelocity the velocity of the ball
     *        when the collision occurred.
     * @param hitter the ball that hits.
     * @return the new velocity expected after the hit (based on
     *         the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}