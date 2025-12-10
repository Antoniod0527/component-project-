import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import components.taskqueue.TaskQueue;
import components.taskqueue.TaskQueue1L;

/**
 * JUnit tests for the TaskQueue1L implementation.
 */
public class TaskQueue1LTest {

    /**
     * Test that a new TaskQueue instance is empty.
     */
    @Test
    public void testNewInstanceIsEmpty() {
        TaskQueue q = new TaskQueue1L();
        assertTrue(q.isEmpty());
    /**
     * Test adding and removing a single task.
     */
    @Test
    public void testAddAndRemoveSingleTask() {

    @Test
    public void testAddAndRemoveSingleTask() {
        TaskQueue q = new TaskQueue1L();
        q.addTask("Write report", 5);
    /**
     * Test that tasks are removed in priority order (highest first).
     */
    @Test
    public void testPriorityOrdering() {
        assertTrue(removed.contains("Write report"));
        assertTrue(q.isEmpty());
    }

    @Test
    public void testPriorityOrdering() {
        TaskQueue q = new TaskQueue1L();
        q.addTask("Low", 1);
        q.addTask("Medium", 5);
        q.addTask("High", 10);
    /**
     * Test that adding a task with null description throws AssertionError.
     */
    @Test(expected = AssertionError.class)
    public void testAddNullDescriptionAssert() {
        String second = q.removeTask();
        assertTrue(second.contains("Medium"));
    /**
     * Test that adding a task with negative priority throws AssertionError.
     */
    @Test(expected = AssertionError.class)
    public void testAddNegativePriorityAssert() {
        assertTrue(q.isEmpty());
    /**
     * Test transferring tasks from one queue to another.
     */
    @Test
    public void testTransferFrom() {
    @Test(expected = AssertionError.class)
    public void testAddNullDescriptionAssert() {
        TaskQueue q = new TaskQueue1L();
        // Kernel uses assert; to trigger assertion at runtime, run JVM with -ea for tests
        q.addTask(null, 1);
    }

    @Test(expected = AssertionError.class)
    /**
     * Test that equals and hashCode are consistent for equal TaskQueues.
     */
    @Test
    public void testEqualsAndHashCodeConsistency() {
        q.addTask("Bad", -1);
    }

    @Test
    public void testTransferFrom() {
        TaskQueue a = new TaskQueue1L();
        TaskQueue b = new TaskQueue1L();
        a.addTask("A1", 2);
        a.addTask("A2", 1);
        b.addTask("B1", 5);
        b.transferFrom(a);
        assertTrue(a.isEmpty());
        String first = b.removeTask();
        assertTrue(first.contains("B1"));
    }

    @Test
    public void testEqualsAndHashCodeConsistency() {
        TaskQueue a = new TaskQueue1L();
        TaskQueue b = new TaskQueue1L();
        a.addTask("X", 3);
        a.addTask("Y", 2);
        b.addTask("X", 3);
        b.addTask("Y", 2);
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }
}
