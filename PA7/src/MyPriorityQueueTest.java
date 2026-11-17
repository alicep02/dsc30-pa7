/*
 * Name: Alice Pham
 * PID: A18818274
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MyPriorityQueue (a 4-ary min-heap priority queue).
 */
public class MyPriorityQueueTest {
    private MyPriorityQueue queue;
    private MyPriorityQueue small;

    @BeforeEach
    public void setUp() {
        queue = new MyPriorityQueue(10);
        small = new MyPriorityQueue(1);
    }

    @Test
    public void testConstructorStartsEmpty() {
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.poll());
    }

    @Test
    public void testConstructorCapacityOne() {
        assertTrue(small.isEmpty());
        assertNull(small.peek());
        assertTrue(small.offer(4.0f));
        assertEquals(4.0f, small.peek());
    }

    @Test
    public void testConstructorLargeCapacity() {
        MyPriorityQueue large = new MyPriorityQueue(100);
        assertTrue(large.isEmpty());
        assertNull(large.poll());
        large.offer(1.5f);
        assertFalse(large.isEmpty());
    }

    @Test
    public void testOfferReturnsTrue() {
        assertTrue(queue.offer(5.0f));
        assertTrue(queue.offer(3.0f));
        assertTrue(queue.offer(5.0f));
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testOfferUpdatesHeadToSmallest() {
        queue.offer(5.0f);
        assertEquals(5.0f, queue.peek());
        queue.offer(7.0f);
        assertEquals(5.0f, queue.peek());
        queue.offer(-2.5f);
        assertEquals(-2.5f, queue.peek());
    }

    @Test
    public void testOfferPastCapacityResizes() {
        for (int i = 20; i >= 1; i--) {
            assertTrue(small.offer((float) i));
        }
        assertEquals(1.0f, small.peek());
        assertEquals(1.0f, small.poll());
        assertEquals(2.0f, small.poll());
        assertEquals(3.0f, small.peek());
    }

    @Test
    public void testOfferNullThrows() {
        assertThrows(NullPointerException.class, () -> queue.offer(null));
        queue.offer(1.0f);
        assertThrows(NullPointerException.class, () -> queue.offer(null));
        assertEquals(1.0f, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPollEmptyReturnsNull() {
        assertNull(queue.poll());
        queue.offer(2.0f);
        assertEquals(2.0f, queue.poll());
        assertNull(queue.poll());
    }

    @Test
    public void testPollReturnsInAscendingOrder() {
        float[] values = {9.0f, 4.0f, 7.5f, -1.0f, 12.0f, 0.0f, 3.25f, 8.0f, 6.0f};
        for (float v : values) {
            queue.offer(v);
        }
        float[] expected = {-1.0f, 0.0f, 3.25f, 4.0f, 6.0f, 7.5f, 8.0f, 9.0f, 12.0f};
        for (float e : expected) {
            assertEquals(e, queue.poll());
        }
        assertTrue(queue.isEmpty());
        assertNull(queue.poll());
    }

    @Test
    public void testPollWithDuplicates() {
        queue.offer(3.0f);
        queue.offer(1.0f);
        queue.offer(3.0f);
        queue.offer(1.0f);
        assertEquals(1.0f, queue.poll());
        assertEquals(1.0f, queue.poll());
        assertEquals(3.0f, queue.poll());
        assertEquals(3.0f, queue.poll());
        assertNull(queue.poll());
    }

    @Test
    public void testPollInterleavedWithOffer() {
        queue.offer(10.0f);
        queue.offer(5.0f);
        assertEquals(5.0f, queue.poll());
        queue.offer(2.0f);
        queue.offer(15.0f);
        assertEquals(2.0f, queue.poll());
        assertEquals(10.0f, queue.poll());
        assertEquals(15.0f, queue.poll());
    }

    @Test
    public void testClearEmptiesQueue() {
        queue.offer(1.0f);
        queue.offer(2.0f);
        queue.offer(3.0f);
        queue.clear();
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.poll());
    }

    @Test
    public void testClearOnEmptyQueue() {
        queue.clear();
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.poll());
    }

    @Test
    public void testQueueUsableAfterClear() {
        for (int i = 0; i < 15; i++) {
            small.offer((float) i);
        }
        small.clear();
        assertTrue(small.isEmpty());
        small.offer(8.0f);
        small.offer(6.0f);
        assertEquals(6.0f, small.poll());
        assertEquals(8.0f, small.poll());
    }

    @Test
    public void testPeekEmptyReturnsNull() {
        assertNull(queue.peek());
        queue.offer(1.0f);
        queue.poll();
        assertNull(queue.peek());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPeekDoesNotRemove() {
        queue.offer(4.0f);
        queue.offer(2.0f);
        assertEquals(2.0f, queue.peek());
        assertEquals(2.0f, queue.peek());
        assertEquals(2.0f, queue.poll());
        assertEquals(4.0f, queue.peek());
    }

    @Test
    public void testPeekReturnsSmallest() {
        queue.offer(50.0f);
        queue.offer(-50.0f);
        queue.offer(0.0f);
        assertEquals(-50.0f, queue.peek());
        queue.poll();
        assertEquals(0.0f, queue.peek());
        queue.poll();
        assertEquals(50.0f, queue.peek());
    }

    @Test
    public void testIsEmptyNewQueue() {
        assertTrue(queue.isEmpty());
        assertTrue(small.isEmpty());
        assertTrue(new MyPriorityQueue(5).isEmpty());
    }

    @Test
    public void testIsEmptyAfterOffer() {
        queue.offer(1.0f);
        assertFalse(queue.isEmpty());
        queue.offer(2.0f);
        assertFalse(queue.isEmpty());
        queue.poll();
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testIsEmptyAfterRemovingAll() {
        queue.offer(1.0f);
        queue.offer(2.0f);
        queue.poll();
        queue.poll();
        assertTrue(queue.isEmpty());
        queue.offer(3.0f);
        queue.clear();
        assertTrue(queue.isEmpty());
    }
}