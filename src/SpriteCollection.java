

import java.util.ArrayList;
import java.util.List;

import Gui.DrawSurface;

/**
 * the Collection holds a collection of sprites.
 */
public class SpriteCollection {
   private List<Sprite> listOfSprite = new ArrayList<Sprite>();

   /**
    * the method adds sprite to the SpriteCollection.
    *
    * @param s is reference to sprite to add to the collection.
    */
    public void addSprite(Sprite s) {
        this.listOfSprite.add(s);
    }

    /**
     * the method calls timePassed() on all sprites.
    * @param dt uses to calculate versus the frame rate.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> sprites = new ArrayList<Sprite>(this.listOfSprite);
        for (Sprite sprite: sprites) {
            sprite.timePassed(dt);
        }
    }

    /**
     * the method calls drawOn(d) on all sprites.
     *
     * @param d is reference to the drawing surface.
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> sprites = new ArrayList<Sprite>(this.listOfSprite);
        for (Sprite sprite: sprites) {
            sprite.drawOn(d);
        }
     }
    /**
     * the method remove sprite to the SpriteCollection.
     *
     * @param s is reference to sprite to remove from the collection.
     */
    public void removeSprite(Sprite s) {
        this.listOfSprite.remove(s);
    }
}

