

import java.awt.Color;

import Gui.DrawSurface;

/**
 * The ScoreIndicator in charge of printing the score.
 */
public class ScoreIndicator implements Sprite {
    private Counter currentScore;

    /**
     * Constructor of the indicator.
     * @param currentScore the current score.
     */
    public ScoreIndicator(Counter currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(255, 128, 64));
        d.fillRectangle(1, 1, 800, 20);
        d.setColor(Color.WHITE);
        d.drawText(370, 15, "Score:"
        + Integer.toString(this.currentScore.getValue()), 17);
    }

    @Override
    public void timePassed(double dt) {
    }
    /**
     * adding this sprite to the game.
     * @param g the Game Level to be added to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * @return the current score.
     */
    public int getScore() {
        return this.currentScore.getValue();
    }

}
