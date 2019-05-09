

import java.awt.Color;

import Gui.DrawSurface;
import Gui.KeyboardSensor;

/**
 * Showing the High Scores as animation.
 */
public class HighScoresAnimation implements Animation {
    private String endKey;
    private HighScoresTable scores;

    /**
     * Constructor for new  HighScoresAnimation.
     * @param scores the score table of all previous scores.
     * @param endKey the key to move to next screen.
     * @param k the KeyboardSensor.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey,
                KeyboardSensor k) {
        this.scores = scores;
        this.endKey = endKey;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.lightGray);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.GREEN);
        d.drawText(60, 50, "High Scores", 50);
        d.setColor(Color.BLUE);
        d.drawText(90, 100, "Player Name", 20);
        d.drawText(290, 100, "Score", 20);
        d.drawLine(90, 110, 350, 110);
        int i = 0;
        for (ScoreInfo score: this.scores.getHighScores()) {
            d.drawText(100, 150 + i, score.getName(), 20);
            d.drawText(300, 150 + i, "" + score.getScore(), 20);
            i += 30;
        }

        d.setColor(Color.BLUE);
        d.drawText(99, 200 + i, "press " + this.endKey + " to Main Menu", 20);
        d.setColor(Color.BLUE);
        d.drawText(101, 200 + i, "press " + this.endKey + " to Main Menu", 20);
        d.setColor(Color.BLUE);
        d.drawText(100, 199 + i, "press " + this.endKey + " to Main Menu", 20);
        d.setColor(Color.BLUE);
        d.drawText(100, 201 + i, "press " + this.endKey + " to Main Menu", 20);
        d.setColor(Color.GREEN);
        d.drawText(100, 200 + i, "press " + this.endKey + " to Main Menu", 20);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
