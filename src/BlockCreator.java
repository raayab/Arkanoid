
/**
 *
 */
public interface BlockCreator {

   /**
    * Create a block at the specified location.
    * @param xpos the x of the upper left corner of the block.
    * @param ypos the y of the upper left corner of the block.
    * @return new Block on those coordinate.
    */
   Block create(int xpos, int ypos);
}
