import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>(100);
        for (int i=0; i<n; i++) {
            rq.enqueue(StdIn.readString());
        }
        for (int i=0; i<n; i++) {
            System.out.println(rq.dequeue());
        }
    }
}