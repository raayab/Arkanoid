

/**
 * A menu animation.
 * @param <T> the type of the menu.
 */
public interface Menu<T> extends Animation {

    /**
     * add selection to the menu.
     *
     * @param key to wait for.
     * @param message to print.
     * @param returnVal to return in case the key was pressed.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return menu status.
     */
    T getStatus();

    /**
     * reset the menu.
     */
    void resetStop();

    /**
     * add sub menu to the current menu.
     *
     * @param key to wait for.
     * @param message to print.
     * @param subMenu the sub menu to add.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
