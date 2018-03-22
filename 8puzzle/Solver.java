import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class Solver {
    private SearchNode res;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prevNode;
        private int move, weight;

        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.move = prevNode == null ? 0 : prevNode.move + 1;
            this.weight = move + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return this.weight - that.weight;
        }

        public SearchNode twin() {
            return new SearchNode(board.twin(), prevNode);
        }

        public boolean isGoal() {
            return board.isGoal();
        }
    }

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        if (initial == null)
            throw new IllegalArgumentException("Board is null!");
        SearchNode searchNode = new SearchNode(initial, null);
        findResultNode(searchNode);
    }

    private void findResultNode(SearchNode searchNode) {
        SearchNode twinNode = searchNode.twin();
        MinPQ<SearchNode> originPQ = new MinPQ<>();
        originPQ.insert(searchNode);
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        twinPQ.insert(twinNode);

        while (true) {
            if (searchNode.isGoal()) {
                res = searchNode;
                break;
            }
            if (twinNode.isGoal()) {
                res = null;
                break;
            }
            searchNode = nextNode(originPQ);
        }

    }

    private SearchNode nextNode(MinPQ<SearchNode> pq) {
        SearchNode minNode = pq.delMin();

        for (Board neighbor: minNode.board.neighbors()) {
            if (minNode.prevNode != null && !minNode.prevNode.board.equals(neighbor)) {
                pq.insert(new SearchNode(neighbor, minNode));
            }
        }
        return minNode;
    }

    public boolean isSolvable() { // is the initial board solvable?
        return res != null;
    }

    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        return isSolvable() ? res.move : -1;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<>();
        while (res != null) {
            stack.push(res.board);
            res = res.prevNode;
        }
        return stack;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)

    }
}
