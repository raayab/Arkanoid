

/**
 * ScoreTrackingListener is in charge of updating
 * the score according the current hit.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor receiving a Counter.
     * @param scoreCounter the counter of the game's score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
       this.currentScore = scoreCounter;
    }
    /**
     * Updating the score after current hit.
     * @param beingHit the block that have been hit.
     * @param hitter the ball that hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
    }
    /**
     * @return the current score of the game.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }
}