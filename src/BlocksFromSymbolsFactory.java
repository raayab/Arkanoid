

import java.util.Map;

/**
 * Making a block or a space.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor for new BlocksFromSymbolsFactory.
     * @param spacerWidths the information for the spaces.
     * @param blockCreators the BlockCreators of the level.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths,
                                    Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }
   /**
    * returns true if 's' is a valid space symbol.
    * @param s the key to check.
    * @return 'true' if 's' is space symbol, 'false' otherwise.
    */
   public boolean isSpaceSymbol(String s) {
       return (this.spacerWidths.containsKey(s));
   }
   /**
    * returns true if 's' is a valid block symbol.
    * @param s the key to check.
    * @return 'true' if 's' is block symbol, 'false' otherwise.
    */
   public boolean isBlockSymbol(String s) {
       return (this.blockCreators.containsKey(s));
   }

   /**
    *  Return a block according to the definitions associated
    *  with symbol s. The block will be located at position (xpos, ypos).
    * @param s the symbol of the block.
    * @param xpos the x coordinate.
    * @param ypos the y coordinate.
    * @return new Block with those parameters.
    */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
   }

   /**
    * Returns the width in pixels associated with the given spacer-symbol.
    * @param s the space symbol.
    * @return the width in pixels associated with the given spacer-symbol.
    */
   public int getSpaceWidth(String s) {
       return this.spacerWidths.get(s);
   }
}
