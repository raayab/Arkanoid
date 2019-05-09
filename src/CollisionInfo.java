

/**
 *
 * This holds the information about a collide that happened:
 * the point where the collide occurred and the object the ball
 * collide with.
 *
 */
public class CollisionInfo {
    private Point collision;
    private Collidable obj;

    /**
     * Constructor based on the object the ball collide with
     * and the point which the collision occurred at.
     *
     * @param obj is the object the ball collide with
     * @param collision is the point which the collision occurred at.
     */
    public CollisionInfo(Collidable obj, Point collision) {
        this.obj = obj;
        this.collision = collision;
    }



    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collision;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.obj;
    }

}
