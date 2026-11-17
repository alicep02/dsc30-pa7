/*
 * Name: Alice Pham
 * PID: A18818274
 */

/**
 * A priority queue of Float values where the smallest value is at the head.
 * Adapts a 4-ary min-heap (dHeap) to the priority queue operations.
 *
 * @author Alice Pham
 * @since 2026-11-16
 */
public class MyPriorityQueue {
    private static final int BRANCHING_FACTOR = 4;

    private dHeap pQueue;

    /**
     * Creates an empty priority queue backed by a 4-ary min-heap.
     *
     * @param initialSize the initial capacity of the underlying heap
     */
    public MyPriorityQueue(int initialSize) {
        pQueue = new dHeap(BRANCHING_FACTOR, initialSize, false);
    }

    /**
     * Inserts the specified value into the priority queue.
     *
     * @param entry the value to insert
     * @return true once the value is inserted
     * @throws NullPointerException if entry is null
     */
    public boolean offer(Float entry) {
        if (entry == null) {
            throw new NullPointerException();
        }
        pQueue.add(entry);
        return true;
    }

    /**
     * Removes and returns the entry with the smallest priority.
     *
     * @return the smallest entry, or null if the queue is empty
     */
    public Float poll() {
        if (isEmpty()) {
            return null;
        }
        return pQueue.remove();
    }

    /**
     * Removes all values from the queue.
     */
    public void clear() {
        pQueue.clear();
    }

    /**
     * Returns the entry with the smallest priority without removing it.
     *
     * @return the smallest entry, or null if the queue is empty
     */
    public Float peek() {
        if (isEmpty()) {
            return null;
        }
        return pQueue.element();
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return pQueue.size() == 0;
    }
}
