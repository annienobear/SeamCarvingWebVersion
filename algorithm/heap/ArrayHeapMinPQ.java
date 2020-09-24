package heap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<HeapNode> heap;
    private int size;
    private Hashtable<T, Integer> indexMap;

    private class HeapNode {
        double priority;
        T item;
        HeapNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ() {
        size = 0;
        heap = new ArrayList<>();
        indexMap = new Hashtable<>();
    }

    /*
    Here's a helper method and a method stub that may be useful. Feel free to change or remove
    them, if you wish.
     */

    /**
     * A helper method to create arrays of T, in case you're using an array to represent your heap.
     * You shouldn't need this if you're using an ArrayList instead.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArray(int newCapacity) {
        return (T[]) new Object[newCapacity];
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        HeapNode temp = heap.get(a);
        heap.set(a, heap.get(b));
        indexMap.put(heap.get(b).item, a);
        heap.set(b, temp);
        indexMap.put(temp.item, b);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        HeapNode temp = new HeapNode(item, priority);
        if (size < 3) {
            heap.add(temp);
            indexMap.put(temp.item, size);
            size++;
            if (size != 0 && priority < heap.get(0).priority) {
                swap(0, size - 1);
            }
        }  else {
            heap.add(temp);
            indexMap.put(temp.item, size);
            size++;
            moveUp(size - 1);
        }
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return indexMap.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        return heap.get(0).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        HeapNode temp = heap.get(0);
        heap.set(0, heap.get(size - 1));
        size--;
        heap.remove(size);
        moveDown(0);
        indexMap.remove(temp.item);
        return temp.item;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = indexMap.get(item);
        double oldPriority = heap.get(index).priority;
        heap.set(indexMap.get(item), new HeapNode(item, priority));
        if (oldPriority < priority) {
            moveDown(index);
        } else {
            moveUp(index);
        }
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return size;
    }

    private void moveUp(int index) {
        double priority = heap.get(index).priority;
        while (index > 0 && priority < heap.get((index - 1) / 2).priority) {
            if ((index - 1) / 2 == 0) {
                swap(index, 0);
                index = 0;
            } else {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
    }

    private void moveDown(int index) {
        while (index * 2 + 1 < size) {
            if (index * 2 + 2 >= size) {
                if (heap.get(index * 2 + 1).priority < heap.get(index).priority) {
                    swap(index, index * 2 + 1);
                    index = index * 2 + 1;
                } else {
                    return;
                }
            } else if (heap.get(index * 2 + 1).priority < heap.get(index).priority
                    && (heap.get(index * 2 + 1).priority < heap.get(index * 2 + 2).priority)) {
                swap(index, index * 2 + 1);
                index = index * 2 + 1;
            } else if (heap.get(index * 2 + 2).priority < heap.get(index).priority
                    && (heap.get(index * 2 + 1).priority > heap.get(index * 2 + 2).priority)) {
                swap(index, index * 2 + 2);
                index = index * 2 + 2;
            } else {
                return;
            }
        }
    }
}
