import java.util.Objects;

/**
 * Layered implementation of secondary methods for {@code TaskQueue}.
 *
 * @author Antonio D’Avanzo
 * @version 2025-11-17
 */
public abstract class TaskQueueSecondary implements TaskQueue {

    /**
     * Creates and returns a copy of this TaskQueue.
     *
     * @return a copy of this TaskQueue
     */
    private TaskQueue copy() {
        TaskQueue temp = this.newInstance();
        TaskQueue result = this.newInstance();
        while (this.length() > 0) {
            String task = this.dequeue();
            temp.enqueue(task);
            result.enqueue(task);
        }
        this.transferFrom(temp);
        return result;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        TaskQueue temp = this.copy();
        for (int i = 0; i < temp.length(); i++) {
            String t = temp.dequeue();
            sb.append(t);
            if (i < temp.length()) {
                sb.append(", ");
            }
        }
        sb.append(">");
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
        boolean result = true;

        TaskQueue thisCopy = this.copy();
        TaskQueue otherCopy = other.newInstance();

        TaskQueue temp = other.newInstance();
        while (other.length() > 0) {
            String task = other.dequeue();
            temp.enqueue(task);
            otherCopy.enqueue(task);
        }
        other.transferFrom(temp);

        if (thisCopy.length() != otherCopy.length()) {
            return false;
        }

        while (result && thisCopy.length() > 0) {
            String t1 = thisCopy.dequeue();
            String t2 = otherCopy.dequeue();
            if (!Objects.equals(t1, t2)) {
                result = false;
            }
        }

        return result;
    }

    @Override
    public final int hashCode() {
        int hash = 7;
        TaskQueue temp = this.copy();
        while (temp.length() > 0) {
            String task = temp.dequeue();
            hash = 31 * hash + Objects.hashCode(task);
        }
        return hash;
    }

    /**
     * Transfers all tasks from this queue to another queue, preserving order.
     *
     * @param target
     *            the queue to receive all tasks
     * @requires target is not null and target != this
     * @ensures this is empty and target = #target * #this
     */
    public void appendTo(TaskQueue target) {
        assert target != null : "Violation of: target is not null";
        assert target != this : "Violation of: target != this";

        TaskQueue temp = this.newInstance();
        while (this.length() > 0) {
            String task = this.dequeue();
            temp.enqueue(task);
            target.enqueue(task);
        }
        this.transferFrom(temp);
    }
}
