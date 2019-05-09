

import java.util.ArrayList;

/**
 * The game environment hold and handle details about our game:
 * adding and holding all the collidable objects and
 * getting the closest collision to occur with a line.
 */
public class GameEnvironment {
    private ArrayList<Collidable> collidableList;

    /**
     * Creating a list to hold all the collidable objects of the game.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<Collidable>();
    }
    /**
     * @return the list of the collidable objects in the game.
     */
    public ArrayList<Collidable> collidableList() {
        ArrayList<Collidable> collidables =
                new ArrayList<Collidable>(this.collidableList);
        return collidables;
    }
    /**
     * Add the given collidable to the environment.
     * @param c is a collidable we add to the game environment.
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }
    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the expected movement of the ball.
     * @return the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (this.collidableList.isEmpty()) {
            return null;
        } else {
            CollisionInfo coll = null;
            double dist = 1000;
            for (int i = 0; i < this.collidableList.size(); i++) {
                Point closestColl =
                    trajectory.closestIntersectionToStartOfLine(
                    this.collidableList.get(i).getCollisionRectangle());
                if (closestColl != null) {
                    if (closestColl.distance(trajectory.start()) < dist) {
                        dist = closestColl.distance(trajectory.start());
                        coll = new CollisionInfo(this.collidableList.get(i),
                                        closestColl);
                    }
                }
            }
            return coll;
        }
    }
    /**
     * removing a Collidable from this game environment.
     * @param c the Collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.collidableList.remove(c);
    }
}