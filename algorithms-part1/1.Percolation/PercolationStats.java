import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] parr;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        parr = new double[trials];
        for (int i=0; i<trials; i++) {
            Percolation percolation = new Percolation(n);
            int[] idxList = new int[n*n];
            for (int j=0; j<n*n; j++) {
                idxList[j] = j;
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
            parr[i] = (double)percolation.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(parr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(parr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = "+percolationStats.mean());
        System.out.println("stddev                  = "+percolationStats.stddev());
        System.out.println("95% confidence interval = ["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
    }
}