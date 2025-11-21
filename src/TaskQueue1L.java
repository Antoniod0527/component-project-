import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Kernel implementation of TaskQueue.
 *
 * <p>
 * Convention: rep != null. Each Task has non-null description and priority >=
 * 0. rep is sorted in non-increasing priority order (index 0 is highest).
 *
 * <p>
 * Correspondence: this = &lt; rep.entry(0), rep.entry(1), ...,
 * rep.entry(rep.length()-1) &gt;
 */
public final class TaskQueue1L extends TaskQueueSecondary {

    /**
     * Small immutable task record.
     */
    private static final class Task {

        /**
         * Hash multiplier.
         */
        private static final int HASH_MULTIPLIER = 31;

        /**
         * Task description.
         */
        private final String description;

        /**
         * Task priority.
         */
        private final int priority;

        /**
         * Creates a task.
         *
         * @param d
         *            description
         * @param p
         *            priority
         */
        Task(String d, int p) {
            this.description = d;
            this.priority = p;
        }

        /**
         * Description accessor.
         *
         * @return description
         */
        String getDescription() {
            return this.description;
        }

        /**
         * Priority accessor.
         *
         * @return priority
         */
        int getPriority() {
            return this.priority;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Task)) {
                return false;
            }
            Task t = (Task) o;
            return this.priority == t.priority
                    && this.description.equals(t.description);
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
     * Representation sequence.
     */
    private Sequence<Task> rep;

    /**
     * Create a fresh representation.
     */
    private void createNewRep() {
        this.rep = new Sequence1L<>();
    }

    /**
     * Check representation invariant.
     */
    private void checkRep() {
        assert this.rep != null;
        for (int i = 0; i < this.rep.length(); i++) {
            Task t = this.rep.entry(i);
            assert t != null;
            assert t.getDescription() != null;
            assert t.getPriority() >= 0;
            if (i + 1 < this.rep.length()) {
                Task next = this.rep.entry(i + 1);
                assert t.getPriority() >= next.getPriority();
            }
        }
    }

    /**
     * No-argument constructor.
     */
    public TaskQueue1L() {
        this.createNewRep();
        this.checkRep();
    }

    /*
     * ---------- Kernel / Standard methods required by TaskQueueSecondary
     * ----------
     */

    /**
     * New instance factory.
     *
     * @return new empty TaskQueue
     */
    @Override
    public TaskQueue newInstance() {
        return new TaskQueue1L();
    }

    /**
     * Length of queue.
     *
     * @return number of tasks
     */
    @Override
    public int length() {
        this.checkRep();
        return this.rep.length();
    }

    /**
     * Enqueue a task (insert preserving priority order).
     *
     * @param description
     *            task description
     */
    @Override
    public void enqueue(String description) {
        // treat enqueue as priority-aware insertion using default priority 0
        // but prefer using addTask(priority-aware) when priority needed
        if (description == null) {
            throw new IllegalArgumentException("description is null");
        }
        this.checkRep();
        // default priority 0 (lowest)
        Task t = new Task(description, 0);
        int i = 0;
        while (i < this.rep.length()
                && this.rep.entry(i).getPriority() >= t.getPriority()) {
            i++;
        }
        this.rep.add(i, t);
        this.checkRep();
    }

    /**
     * Dequeue the front (highest-priority) task.
     *
     * @return removed task as String
     */
    @Override
    public String dequeue() {
        this.checkRep();
        if (this.rep.length() == 0) {
            return "No tasks remaining.";
        }
        Task t = this.rep.remove(0);
        this.checkRep();
        return t.toString();
    }

    /**
     * Transfers from another TaskQueue into this one.
     *
     * @param source
     *            source queue
     */
    @Override
    public void transferFrom(TaskQueue source) {
        if (source == null) {
            throw new IllegalArgumentException("source is null");
        }
        if (!(source instanceof TaskQueue1L)) {
            throw new IllegalArgumentException("incompatible source");
        }
        TaskQueue1L s = (TaskQueue1L) source;
        this.rep = s.rep;
        s.createNewRep();
        this.checkRep();
        s.checkRep();
    }

    /*
     * ---------- Additional kernel names (wrappers to match TaskQueueKernel)
     * ----------
     */

    /**
     * Adds a task with a priority (priority-aware insertion).
     *
     * @param description
     *            task description
     * @param priority
     *            task priority
     */
    @Override
    public void addTask(String description, int priority) {
        if (description == null) {
            throw new IllegalArgumentException("description is null");
        }
        if (priority < 0) {
            throw new IllegalArgumentException("priority < 0");
        }
        this.checkRep();
        Task t = new Task(description, priority);
        int i = 0;
        while (i < this.rep.length()
                && this.rep.entry(i).getPriority() >= priority) {
            i++;
        }
        this.rep.add(i, t);
        this.checkRep();
    }

    /**
     * Removes and returns the next task (priority front).
     *
     * @return removed task string
     */
    @Override
    public String removeTask() {
        return this.dequeue();
    }

    /**
     * Reports whether queue is empty.
     *
     * @return true iff empty
     */
    @Override
    public boolean isEmpty() {
        this.checkRep();
        return this.rep.length() == 0;
    }
}
