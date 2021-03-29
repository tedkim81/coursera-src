import java.util.*;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node prev;
        public Node next;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator(Node first) {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node first;
    private Node last;
    private int size = 0;

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first != null) {
            Node n = new Node(item);
            n.next = first;
            first.prev = n;
            first = n;
        } else {
            first = new Node(item);
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (last != null) {
            Node n = new Node(item);
            n.prev = last;
            last.next = n;
            last = n;
        } else {
            last = new Node(item);
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        first.prev.next = null;
        first.prev = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        last.next.prev = null;
        last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        System.out.println("isEmpty: "+dq.isEmpty()+", size: "+dq.size());
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addLast(4);
        dq.addLast(5);
        dq.addLast(6);
        System.out.println("removeFirst: "+dq.removeFirst());
        System.out.println("removeLast: "+dq.removeLast());
        for (Integer i : dq) {
            System.out.println("dq item: "+i);
        }
    }

}