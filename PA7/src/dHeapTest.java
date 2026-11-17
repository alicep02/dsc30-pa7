/*
 * Name: Alice Pham
 * PID: A18818274
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Starter tests for dHeap.
 * Add tests for every public constructor and method as specified in Part 2.
 * Include both min-heaps and max-heaps, different branching factors,
 * resizing, duplicate values, and all specified exceptions.
 * Style is not graded for this file.
 */
public class dHeapTest {
    private dHeap maxHeap;
    private dHeap minHeap;

    @BeforeEach
    public void setUp() {
        maxHeap = new dHeap();
        minHeap = new dHeap(4, 10, false);
    }

    @Test
    public void testInitialSize() {
        assertEquals(0, maxHeap.size());
        assertEquals(0, minHeap.size());
    }

    @Test
    public void testAddAndElement() {
        Float value = 2.5f;
        maxHeap.add(value);

        assertEquals(1, maxHeap.size());
        assertEquals(value, maxHeap.element());
    }

    @Test
    public void testDefaultConstructorIsBinaryMaxHeap() {
        dHeap heap = new dHeap();
        assertEquals(0, heap.size());
        heap.add(1.0f);
        heap.add(5.0f);
        heap.add(3.0f);
        assertEquals(5.0f, heap.element());
        assertEquals(5.0f, heap.remove());
        assertEquals(3.0f, heap.remove());
    }

    @Test
    public void testDefaultConstructorResizesPastTen() {
        dHeap heap = new dHeap();
        for (int i = 1; i <= 25; i++) {
            heap.add((float) i);
        }
        assertEquals(25, heap.size());
        assertEquals(25.0f, heap.element());
        assertEquals(25.0f, heap.remove());
        assertEquals(24.0f, heap.element());
    }

    @Test
    public void testSizeConstructorIsBinaryMaxHeap() {
        dHeap heap = new dHeap(5);
        assertEquals(0, heap.size());
        heap.add(-4.0f);
        heap.add(8.0f);
        heap.add(2.0f);
        assertEquals(8.0f, heap.element());
        assertEquals(3, heap.size());
    }

    @Test
    public void testSizeConstructorCapacityOneResizes() {
        dHeap heap = new dHeap(1);
        heap.add(3.0f);
        heap.add(9.0f);
        heap.add(6.0f);
        heap.add(1.0f);
        assertEquals(4, heap.size());
        assertEquals(9.0f, heap.remove());
        assertEquals(6.0f, heap.remove());
        assertEquals(3.0f, heap.remove());
    }

    @Test
    public void testFullConstructorMaxHeap() {
        dHeap heap = new dHeap(3, 10, true);
        heap.add(4.0f);
        heap.add(10.0f);
        heap.add(7.0f);
        assertEquals(10.0f, heap.element());
        assertEquals(3, heap.size());
        assertEquals(10.0f, heap.remove());
    }

    @Test
    public void testFullConstructorMinHeap() {
        dHeap heap = new dHeap(3, 10, false);
        heap.add(4.0f);
        heap.add(10.0f);
        heap.add(-7.0f);
        assertEquals(-7.0f, heap.element());
        assertEquals(3, heap.size());
        assertEquals(-7.0f, heap.remove());
    }

    @Test
    public void testFullConstructorDOneIsValid() {
        dHeap heap = new dHeap(1, 3, false);
        heap.add(5.0f);
        heap.add(2.0f);
        heap.add(8.0f);
        heap.add(1.0f);
        assertEquals(1.0f, heap.remove());
        assertEquals(2.0f, heap.remove());
        assertEquals(5.0f, heap.remove());
        assertEquals(8.0f, heap.remove());
    }

    @Test
    public void testFullConstructorInvalidDThrows() {
        assertThrows(IllegalArgumentException.class, () -> new dHeap(0, 10, true));
        assertThrows(IllegalArgumentException.class, () -> new dHeap(-1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> new dHeap(-5, 1, true));
    }

    @Test
    public void testSizeAfterAdds() {
        assertEquals(0, minHeap.size());
        minHeap.add(1.0f);
        assertEquals(1, minHeap.size());
        minHeap.add(2.0f);
        minHeap.add(3.0f);
        assertEquals(3, minHeap.size());
    }

    @Test
    public void testSizeAfterRemoves() {
        maxHeap.add(1.0f);
        maxHeap.add(2.0f);
        maxHeap.add(3.0f);
        maxHeap.remove();
        assertEquals(2, maxHeap.size());
        maxHeap.remove();
        assertEquals(1, maxHeap.size());
        maxHeap.remove();
        assertEquals(0, maxHeap.size());
    }

    @Test
    public void testSizeUnchangedByElement() {
        maxHeap.add(4.0f);
        maxHeap.add(6.0f);
        maxHeap.element();
        assertEquals(2, maxHeap.size());
        maxHeap.element();
        assertEquals(2, maxHeap.size());
        assertEquals(6.0f, maxHeap.element());
    }

    @Test
    public void testAddMaxHeapKeepsLargestAtRoot() {
        maxHeap.add(3.0f);
        assertEquals(3.0f, maxHeap.element());
        maxHeap.add(1.0f);
        assertEquals(3.0f, maxHeap.element());
        maxHeap.add(7.5f);
        assertEquals(7.5f, maxHeap.element());
    }

    @Test
    public void testAddMinHeapKeepsSmallestAtRoot() {
        minHeap.add(3.0f);
        assertEquals(3.0f, minHeap.element());
        minHeap.add(7.0f);
        assertEquals(3.0f, minHeap.element());
        minHeap.add(-0.5f);
        assertEquals(-0.5f, minHeap.element());
    }

    @Test
    public void testAddDuplicates() {
        dHeap heap = new dHeap(3, 4, true);
        heap.add(5.0f);
        heap.add(5.0f);
        heap.add(5.0f);
        assertEquals(3, heap.size());
        assertEquals(5.0f, heap.remove());
        assertEquals(5.0f, heap.remove());
        assertEquals(5.0f, heap.remove());
    }

    @Test
    public void testAddResizesMinHeap() {
        dHeap heap = new dHeap(4, 2, false);
        for (int i = 30; i >= 1; i--) {
            heap.add((float) i);
        }
        assertEquals(30, heap.size());
        assertEquals(1.0f, heap.element());
        assertEquals(1.0f, heap.remove());
        assertEquals(2.0f, heap.element());
    }

    @Test
    public void testAddNullThrows() {
        assertThrows(NullPointerException.class, () -> maxHeap.add(null));
        assertThrows(NullPointerException.class, () -> minHeap.add(null));
        minHeap.add(1.0f);
        assertThrows(NullPointerException.class, () -> minHeap.add(null));
        assertEquals(1, minHeap.size());
    }

    @Test
    public void testRemoveBinaryMaxHeapOrder() {
        float[] values = {25, 2, 15, 30, 68, 5, 59, 31, 75, 70, 84, 1};
        for (float v : values) {
            maxHeap.add(v);
        }
        float[] expected = {84, 75, 70, 68, 59, 31, 30, 25, 15, 5, 2, 1};
        for (float e : expected) {
            assertEquals(e, maxHeap.remove());
        }
        assertEquals(0, maxHeap.size());
    }

    @Test
    public void testRemoveBinaryMinHeapOrder() {
        dHeap heap = new dHeap(2, 5, false);
        float[] values = {-6, 38, 0, 39, 42, 21, 30, 47, 50, 54, 44, 74};
        for (float v : values) {
            heap.add(v);
        }
        float[] expected = {-6, 0, 21, 30, 38, 39, 42, 44, 47, 50, 54, 74};
        for (float e : expected) {
            assertEquals(e, heap.remove());
        }
        assertEquals(0, heap.size());
    }

    @Test
    public void testRemoveThreeAryMaxHeapOrder() {
        dHeap heap = new dHeap(3, 10, true);
        float[] values = {33, 36, 38, 25, 27, 79, 43, 60, 65, 11, 4, 65, 19, 82, 14, 10};
        for (float v : values) {
            heap.add(v);
        }
        float[] expected = {82, 79, 65, 65, 60, 43, 38, 36, 33, 27, 25, 19, 14, 11, 10, 4};
        for (float e : expected) {
            assertEquals(e, heap.remove());
        }
        assertEquals(0, heap.size());
    }

    @Test
    public void testRemoveFourAryMinHeapOrder() {
        float[] values = {33, 36, 38, 25, 27, 79, 43, 60, 65, 11, 4, 65, 19, 82, 14, 10};
        for (float v : values) {
            minHeap.add(v);
        }
        float[] expected = {4, 10, 11, 14, 19, 25, 27, 33, 36, 38, 43, 60, 65, 65, 79, 82};
        for (float e : expected) {
            assertEquals(e, minHeap.remove());
        }
        assertEquals(0, minHeap.size());
    }

    @Test
    public void testRemoveInterleavedWithAdd() {
        dHeap heap = new dHeap(3, 3, true);
        heap.add(78.0f);
        heap.add(52.0f);
        heap.add(55.0f);
        assertEquals(78.0f, heap.remove());
        heap.add(100.0f);
        heap.add(15.0f);
        assertEquals(100.0f, heap.remove());
        assertEquals(55.0f, heap.remove());
        assertEquals(52.0f, heap.remove());
        assertEquals(15.0f, heap.remove());
    }

    @Test
    public void testRemoveEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> maxHeap.remove());
        assertThrows(NoSuchElementException.class, () -> minHeap.remove());
        minHeap.add(1.0f);
        minHeap.remove();
        assertThrows(NoSuchElementException.class, () -> minHeap.remove());
    }

    @Test
    public void testClearEmptiesHeap() {
        maxHeap.add(1.0f);
        maxHeap.add(2.0f);
        maxHeap.add(3.0f);
        maxHeap.clear();
        assertEquals(0, maxHeap.size());
        assertThrows(NoSuchElementException.class, () -> maxHeap.element());
        assertThrows(NoSuchElementException.class, () -> maxHeap.remove());
    }

    @Test
    public void testClearOnEmptyHeap() {
        minHeap.clear();
        assertEquals(0, minHeap.size());
        minHeap.clear();
        assertEquals(0, minHeap.size());
        assertThrows(NoSuchElementException.class, () -> minHeap.element());
    }

    @Test
    public void testHeapUsableAfterClear() {
        dHeap heap = new dHeap(2, 2, false);
        for (int i = 0; i < 12; i++) {
            heap.add((float) i);
        }
        heap.clear();
        heap.add(9.0f);
        heap.add(4.0f);
        assertEquals(2, heap.size());
        assertEquals(4.0f, heap.remove());
        assertEquals(9.0f, heap.remove());
    }

    @Test
    public void testElementDoesNotRemove() {
        minHeap.add(6.0f);
        minHeap.add(2.0f);
        assertEquals(2.0f, minHeap.element());
        assertEquals(2.0f, minHeap.element());
        assertEquals(2, minHeap.size());
    }

    @Test
    public void testElementAfterRemove() {
        dHeap heap = new dHeap(3, 10, true);
        heap.add(10.0f);
        heap.add(30.0f);
        heap.add(20.0f);
        assertEquals(30.0f, heap.element());
        heap.remove();
        assertEquals(20.0f, heap.element());
        heap.remove();
        assertEquals(10.0f, heap.element());
    }

    @Test
    public void testElementEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> maxHeap.element());
        assertThrows(NoSuchElementException.class, () -> minHeap.element());
        maxHeap.add(1.0f);
        maxHeap.remove();
        assertThrows(NoSuchElementException.class, () -> maxHeap.element());
    }
}
