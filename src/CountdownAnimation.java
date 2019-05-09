

import java.awt.Color;

import Gui.DrawSurface;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 *
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Counter appearOnScreen;

    /**
     * CountdownAnimation constructor.
     *
     * @param numOfSeconds the number of seconds to display the given
     *          gameScreen.
     * @param countFrom start the count down from this number.
     * @param gameScreen the current game Screen.
     *
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                                SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.appearOnScreen = new Counter(countFrom);
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        long startTime = System.currentTimeMillis(); // timing
        if (this.appearOnScreen.getValue() >= 0) {
            d.setColor(Color.WHITE);
            d.drawText(399, 305,
                    Integer.toString(this.appearOnScreen.getValue()), 75);
            d.drawText(401, 305,
                    Integer.toString(this.appearOnScreen.getValue()), 75);
            d.drawText(400, 304,
                    Integer.toString(this.appearOnScreen.getValue()), 75);
            d.drawText(400, 306,
                    Integer.toString(this.appearOnScreen.getValue()), 75);
            d.setColor(Color.BLACK);
            d.drawText(400, 305,
                    Integer.toString(this.appearOnScreen.getValue()), 75);
            long usedTime = System.currentTimeMillis() - startTime;
            while (((this.numOfSeconds / this.countFrom) * 1000)
                                                            >= (usedTime)) {
                usedTime = (System.currentTimeMillis() - startTime);
            }
            this.appearOnScreen.decrease(1);
        } else {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
