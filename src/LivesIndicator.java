
import java.awt.Color;

import Gui.DrawSurface;

/**
 * In charge of the Lives of the player.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Constructor of the indicator.
     * @param d the DrawSurface.
     * @param lives the remaining lives.
     */
    public LivesIndicator(DrawSurface d, Counter lives) {
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(580, 15, "Lives: "
                   + Integer.toString(this.lives.getValue()), 17);
    }

    @Override
    public void timePassed(double dt) {
    }
    /**
     * Add this paddle to the game.
     *
     * @param g is the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * @return the remaining lives.
     */
    public Counter getLives() {
        return this.lives;
    }
}