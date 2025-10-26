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
     * Returns a new TaskQueue containing only tasks with a given keyword.
     *
     * @param keyword
     *            the keyword to filter by
     * @return a new TaskQueue containing matching tasks
     * @ensures The result contains only tasks whose descriptions include the
     *          keyword
     */
    TaskQueue filterByKeyword(String keyword);

    /**
     * Displays all tasks in this queue.
     *
     * @ensures All tasks are printed in order of priority
     */
    void listTasks();
}

}
