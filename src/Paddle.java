

import java.awt.Color;

import Gui.DrawSurface;
import Gui.KeyboardSensor;

/**
 * The Paddle is the player in the game.
 * It is a rectangle that is controlled by the arrow keys, and moves according
 * to the player key presses.
 *  It implement the Sprite and the Collidable interfaces.
 *
 */
public class Paddle implements Sprite, Collidable {
   private final KeyboardSensor keyboard;
   private Rectangle paddleRectangle;
   private Line region1, region2, region3, region4, region5;
   private int speed;

   /**
    * the constructor of the Paddle.
    *
    * @param paddleRectangle is reference to the rectangle of the Paddle.
    * @param k is reference to the KeyboardSensor.
    */
   public Paddle(Rectangle paddleRectangle, KeyboardSensor k) {
       this.paddleRectangle = paddleRectangle;
       this.keyboard = k;
   }

   /**
    * Constructor receives the following parameters.
    * @param paddleRectangle the rectangle of the paddle
    * @param k the KeyboardSensor.
    * @param speed the moving speed of the paddle.
    */
   public Paddle(Rectangle paddleRectangle, KeyboardSensor k, int speed) {
       this.paddleRectangle = paddleRectangle;
       this.keyboard = k;
       this.speed = speed;
   }
   /**
    * move the paddle to the left,without hitting the border.
    * @param dt uses to calculate versus the frame rate.
    */
   public void moveLeft(double dt) {
       // if the paddle will not hit the left border
       if (this.paddleRectangle.getUpperLeft().getX() > 20) {
           Point newUpperLeft = new  Point(this.paddleRectangle.getUpperLeft()
               .getX() - (this.speed * dt),
               this.paddleRectangle.getUpperLeft().getY());
           this.paddleRectangle = new Rectangle(newUpperLeft,
               this.paddleRectangle.getWidth(),
               this.paddleRectangle.getHeight());
       } else {
           Point newUpperLeft = new  Point(20,
                   this.paddleRectangle.getUpperLeft().getY());
           this.paddleRectangle = new Rectangle(newUpperLeft,
                   this.paddleRectangle.getWidth(),
                   this.paddleRectangle.getHeight());
       }
   }
   /**
    * move the paddle to the right,without hitting the border.
    * @param dt uses to calculate versus the frame rate.
    */
   public void moveRight(double dt) {
       // if the paddle will hit the right border
       if ((this.paddleRectangle.getUpperLeft().getX()
               + this.paddleRectangle.getWidth()) < 780) {
           Point newUpperLeft = new  Point(this.paddleRectangle.getUpperLeft()
                   .getX() + (this.speed * dt),
                      this.paddleRectangle.getUpperLeft().getY());
           this.paddleRectangle = new Rectangle(newUpperLeft,
                   this.paddleRectangle.getWidth(),
                   this.paddleRectangle.getHeight());
       } else {
           Point newUpperLeft = new Point(780 - this.paddleRectangle.getWidth(),
                      this.paddleRectangle.getUpperLeft().getY());
           this.paddleRectangle = new Rectangle(newUpperLeft,
                   this.paddleRectangle.getWidth(),
                   this.paddleRectangle.getHeight());
       }
   }

    /**
     * implements Sprite.
     *
     * check if the "left" or "right" keys are pressed,
     * and if so move it accordingly.
     * @param dt uses to calculate versus the frame rate.
     */
   @Override
   public void timePassed(double dt) {
       if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
           this.moveLeft(dt);
       } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
           this.moveRight(dt);
       }

   }
   /**
    * implements Sprite.
    *
    * @param d is reference to the drawing surface.
    */
   @Override
   public void drawOn(DrawSurface d) {
       d.setColor(Color.ORANGE);
       d.fillRectangle((int) this.paddleRectangle.getUpperLeft().getX(),
               (int) this.paddleRectangle.getUpperLeft().getY(),
               (int) this.paddleRectangle.getWidth(),
               (int) this.paddleRectangle.getHeight());
   }


   /**
    *Implements Collidable.
    *
    * @return the "collision shape" of the object.
    */
   @Override
public Rectangle getCollisionRectangle() {
       return this.paddleRectangle;
   }

   /**
    *private method that divide the paddle to 5 equally-spaced regions.
    */
   private void setRegions() {
       double regionWidth = this.paddleRectangle.getWidth() / 5;
       this.region1 = new Line(this.paddleRectangle.getUpperLeft(),
               new Point(this.paddleRectangle.getUpperLeft().getX()
                       + regionWidth,
                       this.paddleRectangle.getUpperLeft().getY()));

       this.region2 = new Line(new Point(this.paddleRectangle.getUpperLeft().
               getX() + regionWidth,
               this.paddleRectangle.getUpperLeft().getY()),
               new Point(this.paddleRectangle.getUpperLeft().getX()
                       + (2 * regionWidth),
                       this.paddleRectangle.getUpperLeft().getY()));
       this.region3 = new Line(new Point(this.paddleRectangle.getUpperLeft().
               getX() + (2 * regionWidth),
               this.paddleRectangle.getUpperLeft().getY()),
               new Point(this.paddleRectangle.getUpperLeft().getX()
                       + (3 * regionWidth),
                       this.paddleRectangle.getUpperLeft().getY()));
       this.region4 = new Line(new Point(this.paddleRectangle.getUpperLeft().
               getX() + (3 * regionWidth),
               this.paddleRectangle.getUpperLeft().getY()),
               new Point(this.paddleRectangle.getUpperLeft().getX()
                       + (4 * regionWidth),
                       this.paddleRectangle.getUpperLeft().getY()));
       this.region5 = new Line(new Point(this.paddleRectangle.getUpperLeft().
           getX() + (4 * regionWidth),
           this.paddleRectangle.getUpperLeft().getY()),
           new Point(this.paddleRectangle.getUpperLeft().getX()
                   + (5 * regionWidth),
                   this.paddleRectangle.getUpperLeft().getY()));

   }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint is reference to the collision Point.
     * @param currentVelocity is reference to the velocity of the collision.
     * @param hitter the ball.
     *
     * @return the new velocity expected after the hit
     */
   @Override
    public Velocity hit(Ball hitter, Point collisionPoint,
           Velocity currentVelocity) {
        double ballSpeed = currentVelocity.speedFromDxDy();
       this.setRegions();
       //if the ball hits the left-most region - region1
       if (collisionPoint.isPointOnLine(this.region1)) {
           return Velocity.fromAngleAndSpeed(65, ballSpeed);
       }
       // if the ball hits region2
       if (collisionPoint.isPointOnLine(this.region2)) {
           return Velocity.fromAngleAndSpeed(45, ballSpeed);
       }
       // if the ball hits region3
       if (collisionPoint.isPointOnLine(this.region3)) {
           return new Velocity(currentVelocity.getDx(),
                   -currentVelocity.getDy());
       }
       // if the ball hits region4
        if (collisionPoint.isPointOnLine(this.region4)) {
            return Velocity.fromAngleAndSpeed(315, ballSpeed);
        }
        // if the ball hits region5
        if (collisionPoint.isPointOnLine(this.region5)) {
            return Velocity.fromAngleAndSpeed(295, ballSpeed);
        }
        // take care of side hits.
        if (collisionPoint.isPointOnLine(this.paddleRectangle.getLeft())) {
            return new Velocity(-(currentVelocity.getDx()),
                    currentVelocity.getDy());
        }
        if (collisionPoint.isPointOnLine(this.paddleRectangle.getRight())) {
            return new Velocity(-(currentVelocity.getDx()),
                    currentVelocity.getDy());
        }
        return currentVelocity;
   }


   /**
    * Add this paddle to the game.
    *
    * @param g is the game.
    */
   public void addToGame(GameLevel g) {
       g.addCollidable(this);
       g.addSprite(this);
   }
   /**
    * Removing this paddle from game.
    * @param g the game to be removed from.
    */
   public void removeFromGame(GameLevel g) {
       g.removeCollidable(this);
       g.removeSprite(this);
   }
}

