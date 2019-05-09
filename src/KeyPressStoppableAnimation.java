import Gui.DrawSurface;
import Gui.KeyboardSensor;

/**
 * Pausing an animation till specific key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructor for KeyPressStoppableAnimation.
     * @param sensor the KeyboardSensor.
     * @param key the string for stopping the animation.
     * @param animation the animation to pause.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor,
                                      String key,
                                      Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(this.key)) {
        if (!this.isAlreadyPressed) {
            this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
