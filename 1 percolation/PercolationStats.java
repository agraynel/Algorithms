import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] T;
    private final double C = 1.96;

    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("illegal arguments!");
        }

        T = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int step = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    step++;
                }
            }
            T[i] = (double)step/(n * n);
        }
    }

    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(T);
    }

    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(T);
    }

    public double confidenceLo() { // low  endpoint of 95% confidence interval
        return mean() - C * stddev() / Math.sqrt(T.length);
    }

    public double confidenceHi() { // high endpoint of 95% confidence interval
        return mean() + C * stddev() / Math.sqrt(T.length);
    }

    public static void main(String[] args) { // test client (described below)

    }
}