import components.TaskQueue.TaskQueue;
import components.TaskQueue.TaskQueue1L;

/**
 * Demonstration of batch append and equality for TaskQueue.
 */
public final class BatchAppendAndEquality {

    /**
     * Priority for high-level tasks.
     */
    private static final int HIGH_PRIORITY = 10;

    /**
     * Priority for medium-level tasks.
     */
    private static final int MEDIUM_PRIORITY = 5;

    /**
     * Priority for low-level tasks.
     */
    private static final int LOW_PRIORITY = 1;

    /**
     * Priority used when re-adding tasks during demo.
     */
    private static final int DEFAULT_READD_PRIORITY = 0;

    /**
     * Private constructor to prevent instantiation.
     */
    private BatchAppendAndEquality() {
        throw new AssertionError("No instances.");
    }

    /**
     * Demonstrates batch append and equality operations.
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        TaskQueue a = new TaskQueue1L();
        TaskQueue b = new TaskQueue1L();

        a.addTask("A-high", HIGH_PRIORITY);
        a.addTask("A-low", LOW_PRIORITY);
        b.addTask("B-medium", MEDIUM_PRIORITY);

        TaskQueue temp = a.newInstance();
        while (!a.isEmpty()) {
            String t = a.removeTask();
            temp.addTask(t, DEFAULT_READD_PRIORITY);
            b.addTask(t, DEFAULT_READD_PRIORITY);
        }
        a.transferFrom(temp);

        System.out.println("After append, a empty? " + a.isEmpty());
        System.out.println("b contents: " + b.toString());

        TaskQueue c = new TaskQueue1L();
        c.addTask("B-medium", DEFAULT_READD_PRIORITY);
        c.addTask("A-high", DEFAULT_READD_PRIORITY);
        c.addTask("A-low", DEFAULT_READD_PRIORITY);

        System.out.println("b equals c? " + b.equals(c));
    }

}
