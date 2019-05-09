

import java.util.List;

/**
 * LevelInformation interface of class
 * containing information about a level.
 */
public interface LevelInformation {

    /**
     * return number of ball in this level.
     *
     * @return number of balls
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return a list of The initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     *
     * @return speed of the paddle.
     */
    int paddleSpeed();

    /**
     *
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return the level name as a string.
     */
    String levelName();

    /**
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return blocks list.
     */
    List<Block> blocks();

    /**
     * Number of levels that should be removed
     * before the level is considered to be "cleared".
     *
     * this number should be <= blocks.size();
     *
     * @return Number of levels that should be removed
     */
    int numberOfBlocksToRemove();
}
