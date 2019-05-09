import Gui.DrawSurface;

/**
 * Sprites can be drawn on the screen, and can be notified that time has passed
 * (so that they know to change their position / shape / appearance / etc).
 *
 */
public interface Sprite {
     /**
     * the method draws the sprite to the screen.
     * @param d is reference to the drawing surface.
     */
    void drawOn(DrawSurface d);

    /**
     * the method notify the sprite that time has passed.
     * @param dt TODO
     */
    void timePassed(double dt);
}
