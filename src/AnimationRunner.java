import Gui.DrawSurface;
import Gui.GuiManager;
import Gui.Sleeper;

/**
 * runs an Animation object.
 *
 */
public class AnimationRunner {
    private GuiManager guiManager;
    private int framesPerSecond;
    /**
     * AnimationRunner constructor.
     *
     * @param guiManager the GuiManager.
     * @param framesPerSecond the number of frames per second of guiManager window.
     */
    public AnimationRunner(GuiManager guiManager, int framesPerSecond) {
        this.guiManager = guiManager;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * runs the animation in a loop.
     *
     * @param animation the animation to run.
     */
    public void run(Animation animation) {
        Sleeper sleeper = new Sleeper();
        while (!animation.shouldStop()) {
           long startTime = System.currentTimeMillis(); // timing
           DrawSurface d = this.guiManager.getDrawSurface();

           animation.doOneFrame(d, (1.0 / this.framesPerSecond));

           this.guiManager.show(d);
           long usedTime = System.currentTimeMillis() - startTime;
           long milliSecondLeftToSleep =
                               (1000 / this.framesPerSecond) - usedTime;
           if (milliSecondLeftToSleep > 0) {
               sleeper.sleepFor(milliSecondLeftToSleep);
           }
        }
     }
}
