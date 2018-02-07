import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;

    public RandomizedQueue() {
        array = (Item[])new Object[2];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int n) {
        Item[] newArray = (Item[])new Object[n];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            Item item = array[i];
            if (item != null) {
                newArray[j++] = item;
            }
        }
        array = newArray;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null item!");
        if (size >= array.length) resize(2 * array.length);
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty!");
        Item item = null;
        int rand = 0;
        while (item == null) {
            rand = StdRandom.uniform(size);
            item = array[rand];
        }
        array[rand] = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size > 0 && size <= array.length / 4)
            resize(array.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty!");
        int rand = 0;
        Item item = null;
        while (item == null) {
            rand = StdRandom.uniform(size);
            item = array[rand];
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new NodeIterator();
    }
    // return an iterator over items in order from front to end

    private class NodeIterator implements Iterator<Item> {
        int curr = size;

        public boolean hasNext() {
            return curr != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next item!");
            int rand = 0;
            Item item = null;
            while (item == null) {
                rand = StdRandom.uniform(curr);
                item = array[rand];
            }
            array[rand] = array[--curr];
            return item;
        }
    }

    public static void main(String[] args) {

    }  // unit testing (optional)
}
