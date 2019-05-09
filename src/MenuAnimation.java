
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Gui.DrawSurface;
import Gui.KeyboardSensor;


/**
 * Menu Animation show list of several options of what to do next.
 * @param <T> the menu type.
 */
public class MenuAnimation<T> implements Menu<T> {

    private List<Selection<T>> menu;
    private List<Selection<Menu<T>>> subMenus;
    private String menuTitle;
    private KeyboardSensor k;
    private AnimationRunner runner;
    private Boolean stop;
    private T pressedKey;

    /**
     * construct MenuAnimation.
     *
     * @param menuTitle the menu title.
     * @param d the Surface.
     * @param k the KeyboardSensor.
     * @param runner the AnimationRunner.
     */
    public MenuAnimation(String menuTitle, DrawSurface d, KeyboardSensor k,
                            AnimationRunner runner) {
        this.menu = new ArrayList<Selection<T>>();
            this.subMenus = new ArrayList<Selection<Menu<T>>>();
        this.menuTitle = menuTitle;
        this.k = k;
            this.runner = runner;
        this.stop = false;
    }
    @Override
    public void addSelection(String key, String message, T returnVal) {
            Selection<T> selection = new Selection<T>(key, message, returnVal);
            this.menu.add(selection);
    }

    @Override
    public T getStatus() {
        return this.pressedKey;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.ORANGE);
        d.fillRectangle(0, 0, Ass6Game.SCREEN_WIDTH, Ass6Game.SCREEN_HEIGHT);
        d.setColor(Color.MAGENTA);
        int y = 100;
        d.drawText(100, y, this.menuTitle, 30);
        d.setColor(Color.BLACK);
        for (Selection<T> selection : this.menu) {
            d.drawText(100, y + 50, "(" + selection.getKey() + ")", 15);
            d.drawText(130, y + 50, selection.getMessage(), 15);
            y = y + 50;
        }
        for (Selection<T> selection : this.menu) {
            if (this.k.isPressed(selection.getKey())) {
                for (Selection<Menu<T>> sub : this.subMenus) {
                    if (this.k.isPressed(sub.getKey())) {
                        sub.getReturnVal().resetStop();
                        this.runner.run(sub.getReturnVal());
                        ((Task<T>) sub.getReturnVal().getStatus()).run();
                        break;
                    }
                }
                this.pressedKey = selection.getReturnVal();
                this.stop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void resetStop() {
        this.stop = false;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        Selection<Menu<T>> selection = new Selection<Menu<T>>(key,
                                                             message,
                                                             subMenu);
        this.subMenus.add(selection);
    }
}