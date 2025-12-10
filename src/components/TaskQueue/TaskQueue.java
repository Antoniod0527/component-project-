package components.taskqueue;

/**
 * TaskQueue enhanced component that extends TaskQueueKernel with additional
 * secondary methods.
 *
 * @author Antonio D'Avanzo
 */
public interface TaskQueue extends TaskQueueKernel {

    /**
     * Reports the number of tasks in this queue.
     *
     * @return the number of tasks
     * @ensures size = |this|
     */
    int size();

    /**
     * Returns a new TaskQueue containing only tasks whose descriptions contain
     * the given keyword (case-insensitive).
     *
     * @param keyword
     *            the keyword to filter by
     * @return a new TaskQueue containing matching tasks
     * @requires keyword is not null
     * @ensures <pre>
     * filterByKeyword contains all and only tasks from this whose
     * descriptions contain keyword (case-insensitive) and
     * this = #this
     * </pre>
     */
    TaskQueue filterByKeyword(String keyword);

    /**
     * Displays all tasks in this queue to standard output in priority order.
     *
     * @ensures this = #this
     */
    void listTasks();

    /**
     * Returns the next (highest priority) task without removing it.
     *
     * @return the task string, or a message if queue is empty
     * @ensures <pre>
     * if |this| > 0 then peek = string representation of highest priority task
     * else peek indicates queue is empty
     * and this = #this
     * </pre>
     */
    String peek();

    /**
     * Reports whether any task in this queue contains the given keyword
     * (case-insensitive).
     *
     * @param keyword
     *            the keyword to search for
     * @return true if any task contains the keyword, false otherwise
     * @requires keyword is not null
     * @ensures <pre>
     * contains = (there exists a task in this whose description
     * contains keyword as a substring) and
     * this = #this
     * </pre>
     */
    boolean contains(String keyword);

}

