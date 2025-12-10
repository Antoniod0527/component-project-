package components.taskqueue;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code TaskQueue} represented as a {@code Sequence} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * $this.rep is not null and
 * for all i: integer where (0 <= i < |$this.rep|)
 *   ($this.rep[i].description is not null and
 *    $this.rep[i].priority >= 0) and
 * for all i, j: integer where (0 <= i < j < |$this.rep|)
 *   ($this.rep[i].priority >= $this.rep[j].priority)
 * </pre>
 *
 * @correspondence <pre>
 * this = $this.rep
 * where each element is a task with description and priority,
 * ordered by non-increasing priority (index 0 has highest priority)
 * </pre>
 *
 * @author Antonio D'Avanzo
 */
public final class TaskQueue1L extends TaskQueueSecondary {

    /**
     * Task record holding description and priority.
     */
    private static final class Task {

        /**
         * Hash multiplier constant.
         */
        private static final int HASH_MULTIPLIER = 31;

        /**
         * Task description.
         */
        private final String description;

        /**
         * Task priority (higher = more important).
         */
        private final int priority;

        /**
         * Constructor.
         *
         * @param description
         *            the task description
         * @param priority
         *            the task priority
         */
        Task(String description, int priority) {
            this.description = description;
            this.priority = priority;
        }

        /**
         * Returns the description of this task.
         *
         * @return description
         */
        String getDescription() {
            return this.description;
        }

        /**
         * Returns the priority of this task.
         *
         * @return priority
         */
        int getPriority() {
            return this.priority;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Task)) {
                return false;
            }
            Task other = (Task) obj;
            return this.priority == other.priority
                    && this.description.equals(other.description);
        }

        @Override
        public int hashCode() {
            return this.description.hashCode() * HASH_MULTIPLIER
                    + this.priority;
        }

        @Override
        public String toString() {
            return this.description + " (Priority: " + this.priority + ")";
        }
    }

    /**
     * Representation of {@code this}.
     */
    private Sequence<Task> rep;

    /**
     * Creates a fresh representation.
     */
    private void createNewRep() {
        this.rep = new Sequence1L<>();
    }

    /**
     * No-argument constructor.
     */
    public TaskQueue1L() {
        this.createNewRep();
    }

    /**
     * Returns a new instance of the same dynamic type as {@code this}.
     *
     * @return a new, empty TaskQueue
     */
    @Override
    public TaskQueue newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    /**
     * Clears this queue to the empty state.
     */
    @Override
    public void clear() {
        this.createNewRep();
    }

    /**
     * Transfers the contents of {@code source} into {@code this}.
     *
     * @param source
     *            the source TaskQueue
     */
    @Override
    public void transferFrom(TaskQueue source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof TaskQueue1L : ""
                + "Violation of: source is of dynamic type TaskQueue1L";

        TaskQueue1L localSource = (TaskQueue1L) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /**
     * Adds a task with the given description and priority in proper order.
     *
     * @param description
     *            the task description
     * @param priority
     *            the task priority (>= 0)
     */
    @Override
    public void addTask(String description, int priority) {
        assert description != null : "Violation of: description is not null";
        assert priority >= 0 : "Violation of: priority >= 0";

        Task newTask = new Task(description, priority);

        int position = 0;
        while (position < this.rep.length()
                && this.rep.entry(position).getPriority() >= priority) {
            position++;
        }

        this.rep.add(position, newTask);
    }

    /**
     * Removes and returns the highest-priority task as a formatted string.
     *
     * @return string representation of removed task
     */
    @Override
    public String removeTask() {
        assert this.rep.length() > 0 : "Violation of: |this| > 0";

        Task removed = this.rep.remove(0);
        return removed.toString();
    }

    /**
     * Reports whether this queue is empty.
     *
     * @return true iff this queue contains no tasks
     */
    @Override
    public boolean isEmpty() {
        return this.rep.length() == 0;
    }

}
