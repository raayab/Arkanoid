
import java.awt.Color;

import Gui.DrawSurface;

/**
 * In charge of displaying the level name.
 *
 */
public class LevelNameIndicator implements Sprite {
    private LevelInformation levelInfo;

    /**
     * Constructor of the indicator.
     * @param levelInfo the level information of the current level.
     */
    public LevelNameIndicator(LevelInformation levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(50, 15, "Level Name: "
                + this.levelInfo.levelName(), 17);
    }

    @Override
    public void timePassed(double dt) {
    }

    /**
     * adding this sprite to the game.
     * @param g the game level to be added to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
