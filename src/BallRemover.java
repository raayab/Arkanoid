

/**
 * BallRemover is in charge of removing balls from the game,
 * as well as keeping count of the number of balls that were removed.
 *
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter availableBalls;

    /**
     * BallRemover constructor.
     *
     * @param game the GameLevel to count the removed blocks.
     * @param availableBalls the number of available Balls in the game.
     */
    public BallRemover(GameLevel game, Counter availableBalls) {
        this.game = game;
        this.availableBalls = availableBalls;
    }

    /**
     *  remove from the game balls reaches the bottom of the screen.
     *
     * @param beingHit is the block that was hit.
     * @param hitter is the ball that hit.
     *
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getBlockRect().getUpperLeft().getY() == 599) {
            hitter.removeFromGame(this.game);
            this.availableBalls.decrease(1);
        }
    }
}
