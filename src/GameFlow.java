
import Gui.DrawSurface;
import Gui.KeyboardSensor;


import java.util.List;

/**
 * Game Flow in charge on the correct flowing of the game,
 * such as running the level, pause if needed, etc.
 */
public class GameFlow {
    private AnimationRunner ar;
    private KeyboardSensor keyboard;
    private DrawSurface d;
    private LivesIndicator liveIndicator;
    private ScoreIndicator scoreIndicator;
    private ScoreTrackingListener scoreTrackingListener;

    /**
     * constructor of GameFlow.
     * @param ar the AnimationRunner.
     * @param ks the KeyboardSensor.
     * @param d the DrawSurface.
     * @param liveIndicator is the live indicator.
     * @param scoreIndicator is the score indicator.
     * @param scoreTrackingListener the ScoreTrackingListener.
     */
    public GameFlow(AnimationRunner ar,
                    KeyboardSensor ks,
                    DrawSurface d,
                    LivesIndicator liveIndicator,
                    ScoreIndicator scoreIndicator,
                    ScoreTrackingListener scoreTrackingListener) {
     this.ar = ar;
     this.keyboard = ks;
     this.d = d;
     this.liveIndicator = liveIndicator;
     this.scoreIndicator = scoreIndicator;
     this.scoreTrackingListener = scoreTrackingListener;
    }

    /**
     * Running the levels.
     * @param levels a list of levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
       for (LevelInformation levelInfo : levels) {

          GameLevel level = new GameLevel(levelInfo, this.keyboard, this.ar,
                                          this.d, this.liveIndicator,
                                          this.scoreIndicator,
                                          this.scoreTrackingListener);

          level.initialize();
          // countdown before turn starts.
          Animation countDown = new CountdownAnimation(
                                      2, 3, level.getSprites());
          this.ar.run(countDown);

          while ((this.liveIndicator.getLives().getValue() > 0)
                  && (level.getRemovedBlocks().getValue()
                          < levelInfo.numberOfBlocksToRemove())) {
             level.playOneTurn();
          }
          if (this.liveIndicator.getLives().getValue() == 0) {
              GameOverScreen endScreen = new GameOverScreen(
                                                  this.scoreTrackingListener.
                                                  getCurrentScore().getValue());
              KeyPressStoppableAnimation endScreenK =
                      new KeyPressStoppableAnimation(this.keyboard,
                                                       KeyboardSensor.SPACE_KEY,
                                                       endScreen);
              this.ar.run(endScreenK);
             break;
          }
          if (level.getRemovedBlocks().getValue()
                          == levelInfo.numberOfBlocksToRemove()) {
              this.scoreTrackingListener.getCurrentScore().increase(100);
          }
       }
       if (this.liveIndicator.getLives().getValue() != 0) {
           YouWinScreen endScreen = new YouWinScreen(this.scoreTrackingListener
                                                 .getCurrentScore().getValue());
           KeyPressStoppableAnimation endScreenK =
                   new KeyPressStoppableAnimation(this.keyboard,
                                                  KeyboardSensor.SPACE_KEY,
                                                  endScreen);
           this.ar.run(endScreenK);
       }
    }
}

