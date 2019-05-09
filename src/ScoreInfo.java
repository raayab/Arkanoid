import java.io.Serializable;

/**
 * A single score info.
 */
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * Creating new Score Info.
     * @param name the player name.
     * @param score the player score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * @return the name of the player of this score.
     */
    public String getName() {
        return this.name;
    }
    /**
     * @return the score of the player of this score.
     */
    public int getScore() {
        return this.score;
    }
}
