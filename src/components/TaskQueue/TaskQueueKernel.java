package components.taskqueue;

import components.standard.Standard;

/**
 * TaskQueue kernel component with primary methods.
 *
 * @mathdefinitions <pre>
 * TASK is (
 *   description: string of character,
 *   priority: integer
 *  )
 * </pre>
 *
 * @author Antonio D'Avanzo
 */
public interface TaskQueueKernel extends Standard<TaskQueue> {

    /**
     * Adds a new task to this queue in order of priority (highest priority
     * first).
     *
     * @param description
     *            the description of the task
     * @param priority
     *            the priority of the task (higher = more important)
     * @requires description is not null and priority >= 0
     * @updates this
     * @ensures <pre>
     * this = #this * <(description, priority)> and
     * tasks are maintained in non-increasing priority order
     * </pre>
     */
    void addTask(String description, int priority);

    /**
     * Removes and returns the highest priority task from this queue.
     *
     * @return the removed task as a String
     * @updates this
     * @requires |this| > 0
     * @ensures <pre>
     * removeTask = string representation of the highest priority task in #this and
     * this = #this without that task
     * </pre>
     */
    String removeTask();

    /**
     * Reports whether this queue is empty.
     *
     * @return true if this queue has no tasks, false otherwise
     * @ensures isEmpty = (|this| = 0)
     */
    boolean isEmpty();

}
