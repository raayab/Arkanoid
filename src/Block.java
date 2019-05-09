
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Gui.DrawSurface;

import java.awt.Image;

/**
 * A block class, which is implement of collidable and sprite.
 * It's holds his shape, color, hits and the draw surface for visualize.
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private Rectangle shape;
    private DrawSurface surface;
    private int hits;
    private List<HitListener> hitListeners;
    private Object[] fillKlist;
    private Color stroke;

    /**
     * Block constructor receiving the following parameters.
     * @param upperPoint the upper left point of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     * @param stroke the stroke color.
     * @param hits the number of hits.
     * @param fillKlist a list of colors according the current hit status.
     * @param fill the basic fill.
     */
    public Block(Point upperPoint, int width, int height, Object fill,
            Color stroke, int hits, Object[] fillKlist) {
         this.shape = new Rectangle(upperPoint, width, height);
         this.hits = hits;
         this.fillKlist = fillKlist;
         this.hitListeners = new ArrayList<HitListener>();
         this.stroke = stroke;
     }

    /**
     * Block constructor using upperLeft point, width and height of the block.
     *
     * @param upperLeft the upper left point of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     */
    public Block(Point upperLeft, double width, double height) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * Block constructor using upperLeft point, width, height
     * and color of the block.
     *
     * @param upperLeft the upper left point of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     * @param color the color of the block.
     */
    public Block(Point upperLeft, double width, double height,
                                            Color color) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.hitListeners = new ArrayList<HitListener>();
        this.hits = 1; // we don't write on this block
        this.fillKlist[0] = color;
    }
    /**
     * Block constructor using upperLeft point, width, height, color
     * and the number of hits of the block.
     *
     * @param upperLeft the upper left point of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     * @param color the color of the block.
     * @param hits the number of hits in the creation of the ball.
     */
    public Block(Point upperLeft, double width, double height,
                              Color color, int hits) {
        this.shape = new Rectangle(upperLeft, width, height);
        this.hits = hits;
        this.hitListeners = new ArrayList<HitListener>();
        this.fillKlist = new Color[1];
        this.fillKlist[0] = color;
    }

    /**
     * @return the rectangle shape of the block.
     */
    public Rectangle getBlockRect() {
        return this.shape;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint,
                                        Velocity currentVelocity) {
        if (collisionPoint.isPointOnLine(this.shape.getUp())) {
            currentVelocity = new Velocity(currentVelocity.getDx(),
                                            -(currentVelocity.getDy()));
        }
        if (collisionPoint.isPointOnLine(this.shape.getDown())) {
            currentVelocity = new Velocity(currentVelocity.getDx(),
                                            -(currentVelocity.getDy()));
        }
        if (collisionPoint.isPointOnLine(this.shape.getRight())) {
            currentVelocity = new Velocity(-(currentVelocity.getDx()),
                                                currentVelocity.getDy());
        }
        if (collisionPoint.isPointOnLine(this.shape.getLeft())) {
            currentVelocity = new Velocity(-(currentVelocity.getDx()),
                                                currentVelocity.getDy());
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }



    @Override
    public void drawOn(DrawSurface surf) {
        this.surface = surf;

        if (this.fillKlist[this.hits - 1] instanceof Color) {
            this.surface.setColor((Color) this.fillKlist[this.hits - 1]);
            this.surface.fillRectangle((int) this.shape.getUpperLeft().getX(),
                (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(),
                (int) this.shape.getHeight());
        }
        if (this.fillKlist[this.hits - 1] instanceof Image) {
            this.surface.drawImage((int) this.shape.getUpperLeft().getX(),
                    (int) this.shape.getUpperLeft().getY(),
                    (Image) this.fillKlist[this.hits - 1]);
        }
        if (this.stroke != null) {
            this.surface.setColor(this.stroke);
            this.surface.drawRectangle((int) this.shape.getUpperLeft().getX(),
                (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(),
                (int) this.shape.getHeight());
        }
    }

    @Override
    public void timePassed(double dt) {
    }

    /**
     * decrease the remaining number of hits.
     */
    public void decreaseHit() {
        this.hits--;
        //this.color = this.fillKlist[this.hits];
    }
    /**
     * Add this block to the game.
     * @param g is the game main running class.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    /**
     * removing this block from a game.
     * @param game the game to be removed from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
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
     * notifying about hit occurs.
     *
     * @param hitter the ball that hits.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners =
                    new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }
    /**
     * @return the remaining hits of this block.
     */
    public int getHitPoints() {
        return this.hits;
    }


}
