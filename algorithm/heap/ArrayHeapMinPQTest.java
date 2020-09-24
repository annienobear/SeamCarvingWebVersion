package heap;

import org.junit.Test;

import java.util.PriorityQueue;


import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    private ArrayHeapMinPQ<Integer> temp;
    private PriorityQueue<Integer> target;

    public ArrayHeapMinPQTest() {
        temp = new ArrayHeapMinPQ<>();
        target = new PriorityQueue<>();
    }

    @Test
    public void testAdd(){
        for (int i = 2; i >= 0; i--) {
            target.add(i);
            temp.add(i, i);
        }
        for (int i = 0; i <= 2; i++) {
            assertTrue(target.poll() == temp.removeSmallest());
        }
    }

    @Test
    public void testInterleavedEnqueueDequeue(){
        temp.add(0, 0);
        temp.add(4, 4);
        temp.add(3, 3);
        assertTrue(0 == temp.removeSmallest());
        temp.add(13, 13);
        temp.add(7, 7);
        assertTrue(3 == temp.removeSmallest());
        temp.add(0, 0);
        temp.add(1, 1);
        assertTrue(0 == temp.removeSmallest());
        temp.add(12, 12);
        temp.add(10, 10);
        temp.add(6, 6);
        temp.add(8, 8);
        assertTrue(1 == temp.removeSmallest());
        assertTrue(4 == temp.removeSmallest());
        assertTrue(6 == temp.removeSmallest());
        temp.add(6, 6);
        temp.add(11, 11);
        assertTrue(6 == temp.removeSmallest());
        assertTrue(7 == temp.removeSmallest());
        temp.add(0, 0);
        temp.add(3, 3);
        temp.add(6, 6);
        temp.add(4, 4);
        temp.add(7, 7);
        temp.add(5, 5);
        temp.add(16, 16);
        temp.add(14, 14);
        temp.changePriority(0, 108.6);
        temp.changePriority(3, 103.6);
        assertTrue(4 == temp.removeSmallest());
        temp.changePriority(0, 0);
        assertTrue(0 == temp.removeSmallest());
    }

    @Test
    public void testChangePriority(){
        for (int i = 100; i >= 0; i--) {
            target.add(i);
            temp.add(i, i);
        }
        temp.changePriority(0, 108.6);
        assertTrue(1 == temp.removeSmallest());
        temp.changePriority(100, 0.0);
        assertTrue(100 == temp.removeSmallest());
        assertTrue(2 == temp.removeSmallest());
        temp.changePriority(50, 0.0);
        assertTrue(50 == temp.removeSmallest());
        assertTrue(3 == temp.removeSmallest());
    }

    @Test
    public void add() {
        temp.add(12, 1.2);
        temp.add(13, 1.3);
        temp.add(8, 0.8);
        temp.add(1, 0.1);
        temp.add(2, 0.2);
        temp.add(3, 0.3);
        temp.add(9, 0.9);
        temp.add(5, 0.5);
        temp.add(6, 0.6);
        temp.add(7, 0.7);
        temp.add(15, 1.5);
        temp.getSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.removeSmallest();
        temp.changePriority(15, 0.2);
    }

    @Test
    public void contains() {
    }

    @Test
    public void getSmallest() {
    }

    @Test
    public void removeSmallest() {
    }


    @Test
    public void size() {
    }
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */


}
