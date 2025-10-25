import java.util.ArrayList;
import java.util.List;

public class TaskQueue {

    private static class Task {
        String description;
        int priority;

        Task(String description, int priority) {
            this.description = description;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return this.description + " (Priority: " + this.priority + ")";
        }
    }

    private final List<Task> tasks = new ArrayList<>();

    // Kernel Methods
    public void addTask(String description, int priority) {
        Task t = new Task(description, priority);
        int i = 0;
        while (i < this.tasks.size() && this.tasks.get(i).priority >= priority)
            i++;
        this.tasks.add(i, t);
    }

    public String removeTask() {
        return this.tasks.isEmpty() ? "No tasks remaining."
                : this.tasks.remove(0).toString();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    // Secondary Methods
    public int size() {
        return this.tasks.size();
    }

    public TaskQueue filterByKeyword(String keyword) {
        TaskQueue filtered = new TaskQueue();
        for (Task t : this.tasks)
            if (t.description.toLowerCase().contains(keyword.toLowerCase()))
                filtered.addTask(t.description, t.priority);
        return filtered;
    }

    public void listTasks() {
        if (this.tasks.isEmpty())
            System.out.println("No tasks in queue.");
        else
            this.tasks.forEach(System.out::println);
    }

    // Demonstration
    public static void main(String[] args) {
        TaskQueue queue = new TaskQueue();

        queue.addTask("Finish Portfolio Part 2", 5);
        queue.addTask("Review for CSE 2221 Exam", 4);
        queue.addTask("Clean Workspace", 1);
        queue.addTask("Submit ENGR 1181 Assignment", 3);

        System.out.println("All Tasks (by Priority):");
        queue.listTasks();

        System.out.println("\nRemoved Task:");
        System.out.println(queue.removeTask());

        System.out.println("\nRemaining Tasks:");
        queue.listTasks();

        System.out.println("\nFiltered (Exam):");
        queue.filterByKeyword("Exam").listTasks();

        System.out.println("\nQueue Size: " + queue.size());
    }
}/**
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
