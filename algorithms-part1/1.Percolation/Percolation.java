import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private int n;
    private WeightedQuickUnionUF gridUF;
    private WeightedQuickUnionUF fullCheckUF; // to avoid backwash problem
    private int top;
    private int bottom;
    private boolean[] isOpens;
    private int numberOfOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        gridUF = new WeightedQuickUnionUF(n*n + 2);
        fullCheckUF = new WeightedQuickUnionUF(n*n + 1);
        top = 0;
        bottom = n*n + 1;
        isOpens = new boolean[n*n + 1];
        numberOfOpen = 0;
    }

    private int toIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("("+row+", "+col+") is out of range");
        }
        return (row-1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int idx = toIndex(row, col);
        isOpens[idx] = true;
        numberOfOpen++;
        int[] diffRow = {-1, 0, 1, 0};
        int[] diffCol = {0, 1, 0, -1};
        for (int i=0; i<4; i++) {
            int row2 = row + diffRow[i];
            int col2 = col + diffCol[i];
            if (col2 < 1 || col2 > n) {
                continue;
            }
            if (row2 < 1) {
                gridUF.union(top, idx);
                fullCheckUF.union(top, idx);
            } else if (row2 > n) {
                gridUF.union(bottom, idx);
            } else if (isOpen(row2, col2)) {
                int idx2 = toIndex(row2, col2);
                gridUF.union(idx2, idx);
                fullCheckUF.union(idx2, idx);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpens[toIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return fullCheckUF.find(top) == fullCheckUF.find(toIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridUF.find(top) == gridUF.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 20;
        Percolation percolation = new Percolation(n);
        int[] idxList = new int[n*n];
        for (int i=0; i<n*n; i++) {
            idxList[i] = i;
        }
        StdRandom.shuffle(idxList);
        for (int idx : idxList) {
            int row = (idx / n) + 1;
            int col = (idx % n) + 1;
            percolation.open(row, col);
            if (percolation.percolates()) {
                break;
            }
        }
        System.out.println("numberOfOpenSites: "+percolation.numberOfOpenSites());
    }
}