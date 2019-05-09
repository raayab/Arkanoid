

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Gui.DrawSurface;

/**
 * A ball object contain the ball center point, his radius and the color.
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    private GameEnvironment enviro;
    private List<HitListener> hitListeners;
    /**
     * Constructing a ball object by his center point, radius and color.
     *
     * @param center is the center point of the ball.
     * @param r is the radius of the ball.
     * @param color the color of the ball.
     * @param envi a game environment of the game.
     */
    public Ball(Point center, int r, Color color,
                                                GameEnvironment envi) {
        this.center = new Point((int) (center.getX()), (int) (center.getY()));
        this.r = r;
        this.color = color;
        this.enviro = new GameEnvironment();
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * Constructing a ball object by his center point's coordinates,
     * radius and color.
     *
     * @param x the X coordinate of the center of the ball.
     * @param y the Y coordinate of the center of the ball.
     * @param r is the radius of the ball.
     * @param color the color of the ball.
     * @param envi a game environment of the game.
     */
    public Ball(int x, int y, int r, Color color,
                                                GameEnvironment envi) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.enviro = envi;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * @return the X of the center point of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the Y of the center point of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * @return the color of the ball.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface is the drawing surface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(),
                                (int) this.center.getY(), r);
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(),
                                (int) this.center.getY(), r);
    }

    /**
     * Setting the velocity of the ball using velocity from the function call.
     *
     * @param v is velocity object/
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Setting the velocity of the ball using the difference of the X and Y
     * of the center point.
     *
     * Constructing new velocity using the dx and dy received.
     *
     * @param dx is the difference of the X of this step and the next.
     * @param dy is the difference of the Y of this step and the next.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moving the ball to the next step by calculating his next spot.
     * @param dt uses to calculate versus the frame rate.
     */
    public void moveOneStep(double dt) {
        Line trajectory = new Line(this.center,
                this.getVelocity().applyToPoint(this.center, dt));
        CollisionInfo collInfo = this.enviro.getClosestCollision(trajectory);
        if (collInfo == null) {
            this.center = this.getVelocity().applyToPoint(this.center, dt);
        } else {
            Point hitPoint = collInfo.collisionPoint();
            if (collInfo.collisionObject() instanceof Block) {
                notifyHit(collInfo);
            }
            if (this.getVelocity().getDx() >= 0) {
                this.center = new Point(hitPoint.getX() - 1,
                        this.center.getY());
            } else {
                this.center = new Point(hitPoint.getX() + 1,
                        this.center.getY());
            }
            if (this.getVelocity().getDy() >= 0) {
                this.center = new Point(this.center.getX(),
                        hitPoint.getY() - 1);
            } else {
                this.center = new Point(this.center.getX(),
                        hitPoint.getY() + 1);
            }
            this.setVelocity(collInfo.collisionObject()
                    .hit(this, hitPoint, this.getVelocity()));
            /* A corner case where the paddle moves over the ball, then the ball
             will start from above the middle of the paddle with velocity of
             the current velocity. */
            collInfo = this.enviro.getClosestCollision(trajectory);
            if (collInfo != null) {
            if ((this.center.getX() > collInfo.collisionObject().
                                getCollisionRectangle().getUpperLeft().getX())
                    && (this.center.getX() < collInfo.collisionObject().
                            getCollisionRectangle().getRight().start().getX())
                    && (this.center.getY() > collInfo.collisionObject().
                            getCollisionRectangle().getUp().start().getY())
                    && (this.center.getY() < collInfo.collisionObject().
                            getCollisionRectangle().getDown().start().getY())) {
                this.center = new Point(collInfo.collisionObject().
                        getCollisionRectangle().getUp().middle().getX(),
                        collInfo.collisionObject().getCollisionRectangle().
                                        getUpperLeft().getY() - this.r - 1);
                this.setVelocity(this.getVelocity());
                }
            }
        }
    }

    /**
     * @return the game environment.
     */
    public GameEnvironment getEnviro() {
        return this.enviro;
    }

    /**
     * Making the ball moving while time is passing.
     * @param dt uses to calculate versus the frame rate.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }
    /**
     * Adding the ball to the game board.
     * @param g is the game main running class.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * Removing this ball from the game.
     * @param game the to remove this ball from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * Notifying all the HitListeners in the game about a collision.
     * @param coll the collision information.
     */
    private void notifyHit(CollisionInfo coll) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners =
                    new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent((Block) coll.collisionObject(), this);
        }
     }
}

