import java.util.LinkedList;
import java.util.Queue;

public class Board {

    private int n, mhd;
    private int[] array;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.n = blocks.length;
        matrixToArray(blocks);
        countManhattan(blocks);
    }

    private void matrixToArray(int[][] matrix) {
        array = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            array[i] = matrix[i / n][i % n];
        }
    }

    private void countManhattan(int[][] matrix) {
        mhd = 0;
        for (int i = 0; i < n * n; i++) {
            if (array[i] != 0) {
                mhd += Math.abs(i / n - (array[i] - 1) / n) + Math.abs(i % n - (array[i] - 1) % n);
            }
        }
    }

    private int[][] arrayToMatrix(int[] array) {
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = array[i * n + j];
            }
        }
        return res;
    }

    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        int res = 0;
        for (int i = 0; i < n * n; i++) {
            if (array[i] != 0 && array[i] != i + 1)
                res++;
        }
        return res;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        return mhd;
    }

    public boolean isGoal() { // is this board the goal board?
        return manhattan() == 0;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        Board board;
        if (array[0] == 0) {
            board = swap(1, 1, 0,1);
        } else if (array[1] == 0) {
            board = swap(0, 0, 1,0);
        } else {
            board = swap(0, 0, 0,1);
        }
        return board;
    }

    private Board swap(int i, int j, int m, int n) {
        int[][] matrix = arrayToMatrix(array);
        int temp = matrix[i][j];
        matrix[i][j] = matrix[m][n];
        matrix[m][n] = temp;
        return new Board(matrix);
    }

    public boolean equals(Object y) { // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension() || this.manhattan() != that.manhattan())
            return false;
        for (int i = 0; i < array.length; i++) {
            if (this.array[i] != that.array[i])
                return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        Queue<Board> queue = new LinkedList<>();
        // find blank (i, j)
        int i = 0, j = 0;
        for (int k = 0; k < n * n; k++) {
            if (array[k] == 0) {
                i = k / n;
                j = k % n;
                break;
            }
        }

        if (i >= 1) {
            queue.offer(swap(i, j, i - 1, j));
        }

        if (j >= 1) {
            queue.offer(swap(i, j, i, j - 1));
        }

        if (i < n - 1) {
            queue.offer(swap(i, j, i + 1, j));
        }

        if (j < n - 1) {
            queue.offer(swap(i, j, i, j + 1));
        }
        return queue;
    }

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        int[][] matrix = arrayToMatrix(array);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", matrix[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)

    }
}