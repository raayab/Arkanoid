import java.awt.Color;

import Gui.DrawSurface;

/**
 * A Game-Over animation.
 */
public class GameOverScreen implements Animation {

    private int currentScore;

    /**
     * Constructor of Game Over.
     * @param currentScore the score when game is over.
     */
    public GameOverScreen(int currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(1, 1, 800, 600);
        d.setColor(Color.GREEN);
        d.drawText(250, 200, "GAME OVRE :(", 50);
        d.setColor(Color.PINK);
        d.drawText(315, 350, "-press space to exit-", 20);
        d.setColor(Color.WHITE);
        d.drawText(330, 300, "Your Score is :"
                        + Integer.toString(this.currentScore), 20);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
