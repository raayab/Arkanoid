import java.awt.Color;

import Gui.KeyboardSensor;
import Gui.DrawSurface;

/**
 * In charge on the PauseScrean when 'p' is pressed.
 */
public class PauseScreen implements Animation {
   private KeyboardSensor keyboard;
   private boolean stop;

   /**
    * Constructor of PauseScreen.
    * @param k the KeyboardSensor.
    */
   public PauseScreen(KeyboardSensor k) {
      this.keyboard = k;
      this.stop = false;
   }

   /**
    * Drawing one frame of the game if the Pause Screen is needed.
    * Also in charge to check if the Space key pressed and game
    * should continue.
    * @param d is the link DrawSurface to draw on.
    * @param dt uses to calculate versus the frame rate.
    */
   public void doOneFrame(DrawSurface d, double dt) {
       d.setColor(Color.WHITE);
       d.fillRectangle(1, 1, 800, 600);
       d.setColor(new Color(128, 0, 0));
       d.fillCircle(d.getWidth() / 2, d.getHeight() / 2, 110);
       d.setColor(new Color(255, 128, 64));
       d.fillCircle(d.getWidth() / 2, d.getHeight() / 2, 100);
       d.setColor(new Color(128, 0, 0));
       d.fillRectangle(350, 250, 30, 100);
       d.fillRectangle(425, 250, 30, 100);
       d.drawText(200, 500, "paused -- press space to continue", 32);

       if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
          this.stop = true;
          }
   }
   /**
    * Is this Pause screen should continue.
    * @return True for return to the level
    * and False for keep pausing the game.
    */
   public boolean shouldStop() {
       return this.stop;
       }
}