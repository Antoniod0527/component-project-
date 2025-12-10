import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import components.taskqueue.TaskQueue;
import components.taskqueue.TaskQueue1L;

/**
 * Secondary tests for TaskQueue implementations.
 */
public class TaskQueueSecondaryTest {

    /**
     * Tests size, toString, and list methods of TaskQueue.
     */
    @Test
    public void testSizeAndToStringAndList() {
        TaskQueue q = new TaskQueue1L();
        q.addTask("Alpha", 1);
        q.addTask("Beta", 2);
        assertEquals(2, q.size());
        String s = q.toString();
        assertTrue(s.startsWith("TaskQueue["));
        assertTrue(s.contains("Alpha"));
    /**
     * Tests filterByKeyword, contains, and peek methods of TaskQueue.
     */
    @Test
    public void testFilterByKeywordAndContainsPeek() {

    @Test
    public void testFilterByKeywordAndContainsPeek() {
        TaskQueue q = new TaskQueue1L();
        q.addTask("Write code", 3);
        q.addTask("Review code", 2);
        assertTrue(q.contains("write"));
        assertTrue(q.contains("code"));
        TaskQueue filtered = q.filterByKeyword("review");
        assertEquals(1, filtered.size());
        String p = q.peek();
        assertTrue(p.contains("Write") || p.contains("Review"));
    }
}

