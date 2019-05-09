
/**
 * selection for the menu. holds the key,message and return value.
 *
 * @param <T> selection type.
 */
public class Selection<T> {

    private String key;
    private String message;
    private T returnVal;

    /**
     * construct Selection.
     *
     * @param key to wait for
     * @param message to print
     * @param returnVal to return in case the key was pressed
     */
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * @return the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the return value for this key.
     */
    public T getReturnVal() {
        return this.returnVal;
    }
}
