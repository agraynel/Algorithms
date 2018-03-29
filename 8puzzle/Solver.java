import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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

        @Override
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

        SearchNode curr = originPQ.delMin();
        SearchNode currTwin = twinPQ.delMin();

        while (!curr.board.isGoal() && !currTwin.board.isGoal()) {
            for (Board neighbor : curr.board.neighbors()) {
                // A critical optimization
                if (curr.prevNode != null && neighbor.equals(curr.prevNode.board)) {
                    continue;
                }
                originPQ.insert(new SearchNode(neighbor, curr));
            }

            for (Board neighbor : currTwin.board.neighbors()) {
                if (currTwin.prevNode != null && neighbor.equals(currTwin.prevNode.board)) {
                    continue;
                }
                twinPQ.insert(new SearchNode(neighbor, currTwin));
            }

            curr = originPQ.delMin();
            currTwin = twinPQ.delMin();
        }
        res = curr.board.isGoal() ? curr : null;
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

    public static void main(String [] args) {
    }
}
