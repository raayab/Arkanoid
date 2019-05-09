
/**
 * interface HitListener.
 *
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that being hit.
     * @param hitter the that doing the hit.
     */
   void hitEvent(Block beingHit, Ball hitter);
}