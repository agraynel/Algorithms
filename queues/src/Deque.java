import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    public Deque() { // construct an empty deque
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() { // is the deque empty?
        return size == 0;
    }

    public int size() { // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) { // add the item to the front
        if (item == null)
            throw new IllegalArgumentException("Null item!");
        Node oldHead = head;
        head = new Node(item, null, oldHead);
        if (oldHead == null) {
            tail = head;
        } else {
            oldHead.prev = head;
        }
        size++;
    }

    public void addLast(Item item) { // add the item to the end
        if (item == null)
            throw new IllegalArgumentException("Null item!");
        Node oldTail = tail;
        tail = new Node(item, tail, null);
        if (oldTail == null) {
            head = tail;
        } else {
            oldTail.next = tail;
        }
        size++;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty!");
        Item first = head.item;
        head = head.next;
        size--;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }

        return first;
    }

    public Item removeLast() { // remove and return the item from the end
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty!");
        Item last = tail.item;
        tail = tail.prev;
        size--;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        return last;
    }

    public Iterator<Item> iterator() {
        return new NodeIterator();
    }
    // return an iterator over items in order from front to end

    private class NodeIterator implements Iterator<Item> {
        private Node curr = head;

        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next item!");
            Node temp = curr;
            curr = curr.next;
            return temp.item;
        }
}

    public static void main(String[] args) {

    }  // unit testing (optional)

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
        Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

}
