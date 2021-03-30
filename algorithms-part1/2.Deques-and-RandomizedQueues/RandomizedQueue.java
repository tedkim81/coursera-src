import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class ListIterator implements Iterator<Item> {

        private int iterSize;

        public ListIterator() {
            iterSize = size;
        }

        public boolean hasNext() {
            return iterSize > 0;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randIdx = (int) (Math.random() * iterSize);
            Item item = items[randIdx];
            items[randIdx] = items[--iterSize];
            items[iterSize] = null;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Item[] items;
    private int size;
    private int capacity;
    private static final int INIT_CAPACITY = 2;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        capacity = INIT_CAPACITY;
        items = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void rebuildItems(int capacity) {
        this.capacity = capacity;
        Item[] items2 = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            items2[i] = items[i];
        }
        items = items2;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items[size++] = item;
        if (size == capacity) {
            capacity *= 2;
            rebuildItems(capacity);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIdx = getRandIdx();
        Item item = items[randIdx];
        items[randIdx] = items[--size];
        items[size] = null;
        if (size == capacity / 4) {
            capacity /= 2;
            rebuildItems(capacity);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[getRandIdx()];
    }

    private int getRandIdx() {
        return (int) (Math.random() * size);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());

        System.out.println("==========");

        rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        for (Integer i : rq) {
            System.out.println(i);
        }
    }

}