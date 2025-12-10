package components.taskqueue;

import java.util.Objects;

/**
 * Layered implementations of secondary methods for {@code TaskQueue}.
 *
 * <p>
 * All secondary methods are implemented using only kernel methods (addTask,
 * removeTask, isEmpty) and Standard methods (newInstance, transferFrom).
 *
 * @author Antonio D'Avanzo
 */
public abstract class TaskQueueSecondary implements TaskQueue {

    /*
     * Private helper methods
     */

    private static final int HASH_SEED = 7;

    /**
     * Extracts priority from a formatted task string. Assumes format
     * "description (Priority: N)". If parsing fails, returns 0.
     *
     * @param taskString
     *            the formatted task string
     * @return the priority value
     */
    private static int extractPriority(String taskString) {
        if (taskString == null) {
            return 0;
        }
        int start = taskString.lastIndexOf("Priority: ");
        if (start == -1) {
            return 0;
        }
        int end = taskString.indexOf(")", start);
        if (end == -1) {
            return 0;
        }
        try {
            final int prefixLen = "Priority: ".length();
            String priorityStr = taskString.substring(start + prefixLen, end)
                    .trim();
            return Integer.parseInt(priorityStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Extracts description from a formatted task string. Assumes format
     * "description (Priority: N)". If unavailable returns the original string.
     *
     * @param taskString
     *            the formatted task string
     * @return the description part
     */
    private static String extractDescription(String taskString) {
        if (taskString == null) {
            return null;
        }
        int priorityStart = taskString.lastIndexOf(" (Priority: ");
        if (priorityStart == -1) {
            return taskString;
        }
        return taskString.substring(0, priorityStart);
    }

    /*
     * Common methods (toString, equals, hashCode)
     */

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TaskQueue[");

        TaskQueue temp = this.newInstance();
        boolean first = true;

        // Move everything into temp while building string
        while (!this.isEmpty()) {
            String task = this.removeTask();
            int priority = extractPriority(task);
            if (!first) {
                sb.append(", ");
            }
            sb.append(task);
            temp.addTask(extractDescription(task), priority);
            first = false;
        }

        // Restore original queue
        this.transferFrom(temp);

        sb.append("]");
        return sb.toString();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof TaskQueue)) {
            return false;
        }

        TaskQueue other = (TaskQueue) obj;

        // Build copies of both queues without mutating their final contents
        TaskQueue thisCopy = this.newInstance();
        TaskQueue tempThis = this.newInstance();

        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            String d = extractDescription(t);
            tempThis.addTask(d, p);
            thisCopy.addTask(d, p);
        }
        this.transferFrom(tempThis);

        TaskQueue otherCopy = other.newInstance();
        TaskQueue tempOther = other.newInstance();

        while (!other.isEmpty()) {
            String t = other.removeTask();
            int p = extractPriority(t);
            String d = extractDescription(t);
            tempOther.addTask(d, p);
            otherCopy.addTask(d, p);
        }
        other.transferFrom(tempOther);

        if (thisCopy.size() != otherCopy.size()) {
            return false;
        }

        while (!thisCopy.isEmpty()) {
            String t1 = thisCopy.removeTask();
            String t2 = otherCopy.removeTask();
            if (!Objects.equals(t1, t2)) {
                return false;
            }
        }

        return true;
    }

    final int prime = 31;
    int hash = HASH_SEED;
    final int prime = 31;
    int hash = 7;

    TaskQueue temp = this.newInstance();while(!this.isEmpty())
    {
        String t = this.removeTask();
        int p = extractPriority(t);
        String d = extractDescription(t);
        hash = prime * hash + Objects.hashCode(t);
        temp.addTask(d, p);
    }this.transferFrom(temp);return hash;
    }

    /*
     * Secondary methods - implemented using only kernel methods
     */

    @Override
    public final int size() {
        int count = 0;
        TaskQueue temp = this.newInstance();
        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            String d = extractDescription(t);
            temp.addTask(d, p);
            count++;
        }
        this.transferFrom(temp);
        return count;
    }

    @Override
    public final TaskQueue filterByKeyword(String keyword) {
        assert keyword != null : "Violation of: keyword is not null";

        TaskQueue result = this.newInstance();
        TaskQueue temp = this.newInstance();
        String lowerKeyword = keyword.toLowerCase();

        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            String d = extractDescription(t);
            temp.addTask(d, p);
            if (t.toLowerCase().contains(lowerKeyword)) {
                result.addTask(d, p);
            }
        }

        this.transferFrom(temp);
        return result;
    }

    @Override
    public final void listTasks() {
        if (this.isEmpty()) {
            System.out.println("No tasks in queue.");
            return;
        }
        TaskQueue temp = this.newInstance();
        int index = 1;
        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            System.out.println(index + ". " + t);
            temp.addTask(extractDescription(t), p);
            index++;
        }
        this.transferFrom(temp);
    }

    @Override
    public final String peek() {
        String result = "No tasks in queue.";
        TaskQueue temp = this.newInstance();

        if (this.isEmpty()) {
            return result;
        }

        // Remove front, remember it, then restore everything
        String front = this.removeTask();
        int pFront = extractPriority(front);
        temp.addTask(extractDescription(front), pFront);

        // Move remaining tasks to temp
        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            temp.addTask(extractDescription(t), p);
        }

        // Restore original queue
        this.transferFrom(temp);

        return front;
    }

    @Override
    public final boolean contains(String keyword) {
        assert keyword != null : "Violation of: keyword is not null";

        boolean found = false;
        TaskQueue temp = this.newInstance();
        String lowerKeyword = keyword.toLowerCase();

        while (!this.isEmpty()) {
            String t = this.removeTask();
            int p = extractPriority(t);
            String d = extractDescription(t);
            temp.addTask(d, p);
            if (!found && t.toLowerCase().contains(lowerKeyword)) {
                found = true;
            }
        }

        this.transferFrom(temp);
        return found;
    }

}
