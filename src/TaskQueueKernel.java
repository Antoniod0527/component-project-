/**
 * TaskQueue kernel component with primary methods.
 *
 * @author Antonio D'Avanzo
 */
public interface TaskQueueKernel {

    /**
     * Adds a new task to this queue in order of priority.
     *
     * @param description
     *            the description of the task
     * @param priority
     *            the priority of the task
     * @updates this
     * @ensures The new task is added based on priority order
     */
    void addTask(String description, int priority);

    /**
     * Removes and returns the next task from this queue.
     *
     * @return the removed task as a String
     * @updates this
     * @requires this is not empty
     * @ensures Removes and returns the task at the front of the queue
     */
    String removeTask();

    /**
     * Reports whether this queue is empty.
     *
     * @return true if this queue has no tasks, false otherwise
     * @ensures isEmpty = (this = <>)
     */
    boolean isEmpty();
}
