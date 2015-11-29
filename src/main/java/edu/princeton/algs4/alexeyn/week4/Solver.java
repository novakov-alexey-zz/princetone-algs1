package edu.princeton.algs4.alexeyn.week4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * @author Alexey Novakov
 */
public class Solver {
    private SearchNode solutionNode;

    private class SearchNode {
        private SearchNode prevSearchNode;
        private Board board;
        private int moves;
        private int manhattan;
        private int priority;

        public SearchNode(Board board) {
            this(board, 0, null);
        }

        public SearchNode(Board board, int moves, SearchNode prevSearchNode) {
            this.board = board;
            this.moves = moves;
            this.prevSearchNode = prevSearchNode;
            this.manhattan = board.manhattan();
            this.priority = manhattan + moves;
            assert prevSearchNode == null || this.priority >= prevSearchNode.priority;
        }
    }

    public Solver(Board initial) { //find a solution to the initial board (using the A* algorithm)
        Objects.requireNonNull(initial);
        solve(newMinPQ(initial), newMinPQ(initial.twin()));
    }

    private void solve(MinPQ<SearchNode> mainQueue, MinPQ<SearchNode> twinQueue) {
        while (true) {
            SearchNode searchNode = mainQueue.delMin();
            SearchNode twinSearchNode = twinQueue.delMin();

            if (searchNode.board.isGoal()) {
                solutionNode = searchNode;
                return;
            } else if (twinSearchNode.board.isGoal()) {
                return;
            } else {
                insertNeighbors(searchNode, mainQueue::insert);
                insertNeighbors(twinSearchNode, twinQueue::insert);
            }
        }
    }

    private void insertNeighbors(SearchNode searchNode, Consumer<SearchNode> insertFunc) {
        Iterable<Board> neighbors = searchNode.board.neighbors();

        StreamSupport
                .stream(neighbors.spliterator(), false)
                .filter(e -> (searchNode.prevSearchNode == null || !e.equals(searchNode.prevSearchNode.board)))
                .forEach(e -> insertFunc.accept(new SearchNode(e, searchNode.moves + 1, searchNode)));
    }

    private MinPQ<SearchNode> newMinPQ(Board board) {
        MinPQ<SearchNode> minPQ = new MinPQ<>((o1, o2) -> o1.priority - o2.priority);
        minPQ.insert(new SearchNode(board));
        return minPQ;
    }

    public boolean isSolvable() { // is the initial board solvable?
        return solutionNode != null;
    }

    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        return isSolvable() ? solutionNode.moves : -1;
    }

    public Iterable<Board> solution() {  // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> stack = new Stack<>();
        SearchNode current = solutionNode;
        while (current != null) {
            stack.push(current.board);
            current = current.prevSearchNode;
        }

        return stack;
    }
}
