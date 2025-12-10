import components.TaskQueue.TaskQueue;
import components.TaskQueue.TaskQueue1L;

/**
 * Demonstration utility for TaskQueue usage.
 */
public final class TaskManagerSimple {

    /**
     * Default priority used for the "Update README" task.
     */
    private static final int README_PRIORITY = 3;

    /**
     * Priority used for the "Write unit tests" task.
     */
    private static final int TESTS_PRIORITY = 9;

    /**
     * Priority used for the "Fix bug #123" task.
     */
    private static final int BUG_PRIORITY = 7;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TaskManagerSimple() {
        throw new AssertionError("utility class");
    }

    /**
     * Main entry point that demonstrates adding and removing tasks from a
     * TaskQueue.
     *
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        TaskQueue q = new TaskQueue1L();

        q.addTask("Fix bug #123", BUG_PRIORITY);
        q.addTask("Write unit tests", TESTS_PRIORITY);
        q.addTask("Update README", README_PRIORITY);

        q.dequeue();

        while (!q.isEmpty()) {
            System.out.println(q.removeTask());
        }
    }

}
