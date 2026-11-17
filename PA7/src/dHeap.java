/*
 * Name: Alice Pham
 * PID: A18818274
 */

import java.util.NoSuchElementException;

/**
 * An array-based d-ary heap of Float values that can act as either a
 * max-heap or a min-heap.
 *
 * @author Alice Pham
 * @since 2026-11-16
 */
public class dHeap {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int BINARY_HEAP = 2;
    private static final int RESIZE_FACTOR = 2;

    private Float[] heap;
    private int d;
    private int nelems;
    private boolean isMaxHeap;

    /**
     * Creates a binary max-heap with an initial capacity of 10.
     */
    public dHeap() {
        this(BINARY_HEAP, DEFAULT_CAPACITY, true);
    }

    /**
     * Creates a binary max-heap with the given initial capacity.
     *
     * @param heapSize the initial capacity of the heap
     */
    public dHeap(int heapSize) {
        this(BINARY_HEAP, heapSize, true);
    }

    /**
     * Creates a heap with branching factor d and the given initial capacity.
     *
     * @param d         the branching factor of the heap
     * @param heapSize  the initial capacity of the heap
     * @param isMaxHeap true for a max-heap, false for a min-heap
     * @throws IllegalArgumentException if d is less than 1
     */
    public dHeap(int d, int heapSize, boolean isMaxHeap) {
        if (d < 1) {
            throw new IllegalArgumentException();
        }
        this.d = d;
        this.heap = new Float[heapSize];
        this.nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    /**
     * Returns the number of entries stored in the heap.
     *
     * @return the number of entries in the heap
     */
    public int size() {
        return nelems;
    }

    /**
     * Adds the given entry to the heap.
     *
     * @param value the entry to add
     * @throws NullPointerException if value is null
     */
    public void add(Float value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (nelems == heap.length) {
            resize();
        }
        // place the new value at the end, then move it up into position
        heap[nelems] = value;
        bubbleUp(nelems);
        nelems++;
    }

    /**
     * Removes and returns the root entry.
     *
     * @return the root entry of the heap
     * @throws NoSuchElementException if the heap is empty
     */
    public Float remove() {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        Float root = heap[0];
        // move the last entry to the root, then move it down into position
        nelems--;
        heap[0] = heap[nelems];
        heap[nelems] = null;
        if (nelems > 0) {
            trickleDown(0);
        }
        return root;
    }

    /**
     * Removes all entries from the heap.
     */
    public void clear() {
        heap = new Float[heap.length];
        nelems = 0;
    }

    /**
     * Returns the root entry without removing it.
     *
     * @return the root entry of the heap
     * @throws NoSuchElementException if the heap is empty
     */
    public Float element() {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    /**
     * Returns the index of the parent of the node at the given index.
     *
     * @param index the index of a non-root node
     * @return the index of its parent
     */
    private int parent(int index) {
        return (index - 1) / d;
    }

    /**
     * Checks whether the first value belongs above the second value.
     * In a max-heap the larger value has higher priority; in a min-heap
     * the smaller value does.
     *
     * @param first  the first value
     * @param second the second value
     * @return true if first has strictly higher priority than second
     */
    private boolean hasHigherPriority(Float first, Float second) {
        int comparison = first.compareTo(second);
        return isMaxHeap ? comparison > 0 : comparison < 0;
    }

    /**
     * Swaps the entries at the two given indices.
     *
     * @param i the first index
     * @param j the second index
     */
    private void swap(int i, int j) {
        Float temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Moves the entry at the given index up until its parent has equal
     * or higher priority.
     *
     * @param index the index of the entry to move up
     */
    private void bubbleUp(int index) {
        int current = index;
        while (current > 0 && hasHigherPriority(heap[current], heap[parent(current)])) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /**
     * Moves the entry at the given index down until none of its children
     * has higher priority.
     *
     * @param index the index of the entry to move down
     */
    private void trickleDown(int index) {
        int current = index;
        while (true) {
            int firstChild = d * current + 1;
            if (firstChild >= nelems) {
                return;
            }
            // find the highest-priority child among up to d children
            int best = firstChild;
            int lastChild = Math.min(d * current + d, nelems - 1);
            for (int child = firstChild + 1; child <= lastChild; child++) {
                if (hasHigherPriority(heap[child], heap[best])) {
                    best = child;
                }
            }
            if (!hasHigherPriority(heap[best], heap[current])) {
                return;
            }
            swap(current, best);
            current = best;
        }
    }

    /**
     * Doubles the capacity of the backing array, keeping all entries.
     */
    private void resize() {
        Float[] larger = new Float[heap.length * RESIZE_FACTOR];
        for (int i = 0; i < nelems; i++) {
            larger[i] = heap[i];
        }
        heap = larger;
    }
}
