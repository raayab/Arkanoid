

import java.awt.Color;

import Gui.DrawSurface;
import Gui.KeyboardSensor;

/**
 * class that will hold the sprites and the collidables,
 * and in charge of the animation.
 *
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private Counter removedBlocks = new Counter();
    private BlockRemover blockRemover = new BlockRemover(this, removedBlocks);
    private ScoreTrackingListener scoreTrackingListener;
    private Counter availableBalls = new Counter();
    private BallRemover ballRemover = new BallRemover(this, availableBalls);
    private boolean running;
    private LevelInformation levelInformation;
    private AnimationRunner runner;
    private LivesIndicator liveIndicator;
    private Paddle paddle;
    private KeyboardSensor keyboard;
    private ScoreIndicator scoreIndicator;
    private LevelNameIndicator levelName;

    /**
     * Constructor to a Game Level.
     * @param levelInformation the current level information.
     * @param ks the KeyboardSensor for this game level.
     * @param runner the Animation Runner.
     * @param d the Draw Surface.
     * @param liveIndicator the Lives indicator.
     * @param scoreIndicator the Score indicator.
     * @param scoreTrackingListener the Score Listener.
     */
    public GameLevel(LevelInformation levelInformation,
                    KeyboardSensor ks,
                    AnimationRunner runner,
                    DrawSurface d,
                    LivesIndicator liveIndicator,
                    ScoreIndicator scoreIndicator,
                    ScoreTrackingListener scoreTrackingListener) {
        this.levelInformation = levelInformation;
        this.keyboard = ks;
        this.runner = runner;
        this.liveIndicator = liveIndicator;
        this.scoreIndicator = scoreIndicator;
        this.scoreTrackingListener = scoreTrackingListener;
        this.levelName = new LevelNameIndicator(this.levelInformation);
    }

   /**
     * add the given collidable to the environment.
     *
     * @param c is the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add the given Sprite to the SpriteCollection.
     * @param s is the Sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * private method that initialize the blocks in the borders of the screen,
     * and add those blocks to the game.
     */
    private void initializeBorders() {
        Block up = new Block(new Point(1, 20),
                800, 21, Color.DARK_GRAY, 1);
        up.addToGame(this);
        Block down = new Block(new Point(1, 599),
                800, 20, Color.DARK_GRAY, 1);
        down.addToGame(this);
        down.addHitListener(this.ballRemover);
        Block right = new Block(new Point(1, 20), 20,
                600, Color.DARK_GRAY, 1);
        right.addToGame(this);
        Block left = new Block(new Point(780, 20), 20,
                600, Color.DARK_GRAY, 1);
        left.addToGame(this);
    }

    /**
     * private method that initialize the paddle and add it to the game.
     */
    private void initializePaddle() {
        int paddleWidth = this.levelInformation.paddleWidth();
        this.paddle = new Paddle(new Rectangle(
                                       new Point(400 - (paddleWidth / 2), 565),
                                       paddleWidth, 15), this.keyboard,
                                       this.levelInformation.paddleSpeed());
       this.paddle.addToGame(this);
    }
    /**
     * private method that initialize the balls and add them to the game.
     */
    private void initializeBalls() {
        for (int i = 0; i < this.levelInformation.numberOfBalls(); i++) {
            Ball ball = new Ball(400, 550, 5, Color.WHITE, this.environment);
            ball.addToGame(this);
            ball.setVelocity(
                    this.levelInformation.initialBallVelocities().get(i));
            this.availableBalls.increase(1);
            this.availableBalls.increase(1);

            ball.addHitListener(this.ballRemover);
        }
    }
    /**
     * private method that initialize the blocks and add them to the game.
     */
    private void initializeBlocks() {
        for (Block block: this.levelInformation.blocks()) {
            block.addHitListener(this.blockRemover);
            block.addHitListener(this.scoreTrackingListener);
            block.addToGame(this);
        }
    }
    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.addSprite(this.levelInformation.getBackground());
        this.initializeBalls();
        this.initializePaddle();
        this.initializeBlocks();
        this.initializeBorders();
        this.scoreIndicator.addToGame(this);
        this.liveIndicator.addToGame(this);
        this.levelName.addToGame(this);
     }
    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        this.running = true;
        this.runner.run(this);
    }
    /**
     * remove Collidable.
     *
     * @param c the Collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * remove Sprite.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.levelInformation.getBackground().drawOn(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.availableBalls.getValue() == 0
                && this.liveIndicator.getLives().getValue() > 0) {
            this.liveIndicator.getLives().decrease(1);
            this.paddle.removeFromGame(this);
            this.initializeBalls();
            this.initializePaddle();
            this.initializeBorders();
            if (this.liveIndicator.getLives().getValue() != 0) {
            Animation countDown = new CountdownAnimation(
                    2, 3, this.getSprites());
            this.runner.run(countDown);
            }
        }
        if ((this.removedBlocks.getValue()
                == this.levelInformation.numberOfBlocksToRemove())
                || (this.liveIndicator.getLives().getValue() == 0)) {
            this.running = false;
            }
        if (this.keyboard.isPressed("p")) {
            PauseScreen pauseScreen = new PauseScreen(this.keyboard);
            KeyPressStoppableAnimation pauseScreenK =
                    new KeyPressStoppableAnimation(this.keyboard,
                                                     KeyboardSensor.SPACE_KEY,
                                                     pauseScreen);
            this.runner.run(pauseScreenK);
        }
    }

    @Override
    public boolean shouldStop() {
        return (!this.running);
    }
    /**
     * @return a Counter of removed blocks.
     */
    public Counter getRemovedBlocks() {
        return this.removedBlocks;
    }
    /**
     * @return the game level sprites collection.
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }
}
