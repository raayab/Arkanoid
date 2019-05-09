

/**
 *
 * BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that were removed.
 *
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter removedBlocks;

    /**
     * BlockRemover constructor.
     *
     * @param game the game to remove blocks from.
     * @param removedBlocks counts the removed blocks.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.removedBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game.
     *
     * @param beingHit is the block that was hit.
     * @param hitter is the ball that hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.decreaseHit();
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            this.removedBlocks.increase(1);
            beingHit.removeHitListener(this);
        }
    }
}
