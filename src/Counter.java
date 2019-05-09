
/**
 * A Counter class.
 */
public class Counter {
    private int count;
    /**
     * constructor of Counter.
     * creating new counter with 0 counts.
     */
    public Counter() {
        this.count = 0;
    }
    /**
     * constructor of Counter.
     * creating new counter with a specific number of counts.
     * @param number the number of counts.
     */
    public Counter(int number) {
        this.count = number;
    }
    /**
     * add number to current count.
     *
     * @param number is the number to add to count
     */
    public void increase(int number) {
        this.count = this.count + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number is the number to subtract from count.
     */
    public void decrease(int number) {
        this.count = this.count - number;
    }

    /**
     * get current count.
     *
     * @return current count.
     */
    public int getValue() {
        return this.count;
    }
}
