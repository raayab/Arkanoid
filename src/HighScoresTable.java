

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HighScoresTable holding the whole game high scores.
 */
public class HighScoresTable {
    private ArrayList<ScoreInfo> scoresTable;
    private int size;

   /**
    *  Create an empty high-scores table with the specified size.
    * @param size - the table holds up to size top scores.
    */
   public HighScoresTable(int size) {
       this.size = size;
       this.scoresTable = new ArrayList<ScoreInfo>(size);
   }

   /**
    *  Add a high-score.
    * @param score the score to add.
    */
   public void add(ScoreInfo score) {
       if (this.getRank(score.getScore()) <= this.size) {
           this.scoresTable.add((this.getRank(score.getScore()) - 1), score);
           if (this.scoresTable.size() > this.size) {
               this.scoresTable.remove(this.size);
           }
       }
   }

   /**
    * @return table size.
    */
   public int size() {
       return this.size;
   }

   /**The list is sorted such that the highest
    *scores come first.
    * @return the current high scores.
    */
   public List<ScoreInfo> getHighScores() {
       return this.scoresTable;
   }

   /**
    *  return the rank of the current score: where will it
    *   be on the list if added?
    * @param score the score for the check.
    * @return Rank 1 means the score will be highest on the list.
    * Rank `size` means the score will be lowest.
    * Rank > `size` means the score is too low and will not
    *      be added to the list.
    */
   public int getRank(int score) {
       int rank = this.scoresTable.size() + 1;
       if (rank == 1) {
           return rank;
       }
       for (ScoreInfo scoreInfo: this.scoresTable) {
           if (score > scoreInfo.getScore()) {
               return (this.scoresTable.indexOf(scoreInfo) + 1);
           }
       }
       return rank;
   }

    /**
    * Clears the table.
    */
   public void clear() {
       this.scoresTable.clear();
   }

   /**
    * Load table data from file.
    * @param filename the high scores file path.
    * @throws IOException error loading the file.
    */
   public void load(File filename) throws IOException {
       ObjectInputStream is = null;
       is = new ObjectInputStream(new FileInputStream(filename));
       try {
           Object object = is.readObject();
           if (object instanceof ArrayList) {
               for (Object obj: (ArrayList<?>) object) {
                   if (obj instanceof ScoreInfo) {
                       this.scoresTable.add((ScoreInfo) obj);
                   }
               }
           }
       } catch (ClassNotFoundException e) {
           System.out.println("CLASS ERROR");
       } finally {
           if (is != null) {
               is.close();
           }
       }
   }

   /**
    *  Save table data to the specified file.
    * @param filename filename the high scores file path.
    * @throws IOException error saving the file.
    */
   public void save(File filename) throws IOException {
       ObjectOutputStream os = null;
       os = new ObjectOutputStream(new FileOutputStream(filename));
       os.writeObject(this.scoresTable);
       if (os != null) {
           os.close();
       }
   }

   /**
    *  Read a table from file and return it.
    * @param filename the file path.
    * @return a table form file,
    * If the file does not exist, or there is a problem with
    * reading it, an empty table is returned.
    */
   public static HighScoresTable loadFromFile(File filename) {
       HighScoresTable table = new HighScoresTable(5);
       try {
           table.load(filename);
       } catch (IOException e) {
           System.out.println("ERROR SAVING FILE");
       }
       return table;
   }
}