
import java.awt.Color;
import java.util.Random;
import Gui.DrawSurface;
import Gui.KeyboardSensor;

/**
 * Implementing an end screen when game is over.
 */
public class EndScreen implements Animation {
    private int livesLeft;
    private int currentScore;
    private KeyboardSensor keyboard;
    private Boolean stop;
    private Color[] listOfColors = new Color[15];

    /**
     * Constructor of End Screen.
     * @param livesLeft the player's remaining lives.
     * @param currentScore the player's current score.
     * @param ks the KeyboardSensor.
     */
    public EndScreen(int livesLeft, int currentScore, KeyboardSensor ks) {
        this.livesLeft = livesLeft;
        this.currentScore = currentScore;
        this.keyboard = ks;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.createArrayOfColors();
        if (this.livesLeft == 0) {
            d.setColor(Color.BLACK);
            d.fillRectangle(1, 1, 800, 600);
            d.setColor(Color.GREEN);
            d.drawText(250, 200, "GAME OVRE :(", 50);
        } else {
            d.setColor(Color.BLUE);
            d.fillRectangle(1, 1, 800, 600);
            d.setColor(Color.WHITE);
            d.drawText(250, 200, "YOU WIN! :)", 50);
            Random rand = new Random();

            for (int i = 0; i < 15; i++) {
                d.setColor(listOfColors[rand.nextInt(15)]);
                    d.drawCircle(rand.nextInt(800),
                            rand.nextInt(600),
                            rand.nextInt(35));
            }
        }
        d.setColor(Color.PINK);
        d.drawText(315, 350, "-press space to exit-", 20);
        d.setColor(Color.WHITE);
        d.drawText(330, 300, "Your Score is :"
                        + Integer.toString(this.currentScore), 20);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
    /**
     * private method that creates list of Array of colors.
     */
    private void createArrayOfColors() {
        listOfColors[0] = new Color(255, 128, 92);
        listOfColors[1] = new Color(55, 200, 146);
        listOfColors[2] = new Color(213, 43, 115);
        listOfColors[3] = Color.GREEN;
        listOfColors[4] = Color.PINK;
        listOfColors[5] = new Color(90, 228, 173);
        listOfColors[6] = new Color(136, 83, 172);
        listOfColors[7] = Color.CYAN;
        listOfColors[8] = new Color(255, 128, 64);
        listOfColors[9] = Color.YELLOW;
        listOfColors[10] = new Color(225, 237, 82);
        listOfColors[11] = Color.MAGENTA;
        listOfColors[12] = Color.BLUE;
        listOfColors[13] = new Color(255, 0, 128);
        listOfColors[14] = Color.RED;
    }
}
