import java.util.*;

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
            int randIdx = (int)(Math.random() * iterSize);
            iterSize--;
            return items[randIdx];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Item[] items;
    private int capacity;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue(int capacity) {
        items = (Item[]) new Object[capacity];
        this.capacity = capacity;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIdx = getRandIdx();
        Item item = items[randIdx];
        items[randIdx] = items[size-1];
        items[size-1] = null;
        size--;
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
        return (int)(Math.random() * size);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>(100);
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
        System.out.println(rq.dequeue());
    }

}