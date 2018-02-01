import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private int count;

    public Percolation(int n) {  // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("n should be larger than zero!");
        }
        this.n = n;
        sites = new boolean[n * n + 1];
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
        for (int i = n * n; i >= n * (n - 1) + 1; i--) {
            uf.union(n * n + 1, i);
        }
        count = 0;
    }

    public void open(int row, int col) { // open site (row, col) if it is not open already
        int x = changeScale(row, col);
        if (sites[x]) return;
        sites[x] = true;
        count++;

        uf.union(x, x);

        int i = row - 1;
        int j = col;
        openHelper(x, i, j);

        i = row + 1;
        j = col;
        openHelper(x, i, j);

        i = row;
        j = col - 1;
        openHelper(x, i, j);

        i = row;
        j = col + 1;
        openHelper(x, i, j);
    }

    private void openHelper(int x, int i, int j) {
        if (i > 0 && i <= n && j > 0 && j <= n) {
            int y = changeScale(i, j);
            if (isOpen(i, j)) {
                uf.union(x, y);
            }
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        int x = changeScale(row, col);
        return sites[x];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (isOpen(row, col)) {
            int x = changeScale(row, col);
            if (uf.connected(0, x)) return true;
        }
        return false;
    }

    public int numberOfOpenSites() { // number of open sites
        return count;
    }

    public boolean percolates() { // does the system percolate?
        if (n == 1) {
            if (sites[0]) return true;
            return false;
        }
        return uf.connected(0, n * n + 1);
    }

    private int changeScale(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("out of bound!");
        }
        return (row - 1) * n + col;
    }

    public static void main(String[] args) { // test client (optional)

    }
}
