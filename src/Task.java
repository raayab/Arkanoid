/**
*  A task is something that needs to happen, or something that we can run()
* and return a value.
* @param <T> for generic use
*/
public interface Task<T> {

    /**
     * run a task.
     *
     * @return value from the run
     */
    T run();
}
