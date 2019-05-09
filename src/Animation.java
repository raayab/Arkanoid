import Gui.DrawSurface;

/**
 *
 *handle the game-specific logic and stopping conditions.
 *
 */
public interface Animation {

    /**
     * handle the game logic.
     *
     * @param d the surface.
     * @param dt uses to calculate versus the frame rate.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return true if should stop.
     */
    boolean shouldStop();

}
