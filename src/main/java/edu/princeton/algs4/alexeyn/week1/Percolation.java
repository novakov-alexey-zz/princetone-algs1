package edu.princeton.algs4.alexeyn.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Alexey Novakov
 */
public class Percolation {
    private static final int TOP_ROW = 1;

    private final int n;
    private final WeightedQuickUnionUF quickFind;
    private final boolean[][] states;

    /**
     * Creates Percolation object with nested 2d array representing the grid
     *
     * @param n - size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be > 0");

        this.n = n;
        quickFind = new WeightedQuickUnionUF(n * n + 2);
        states = new boolean[n][n];
    }

    /**
     * Open one site at i - row and j - column
     *
     * @param i - indicates row
     * @param j - indicates column
     */
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            states[i - 1][j - 1] = true;
            int p = to1Dim(i, j);

            if (isTopRow(i)) {
                quickFind.union(p, 0);
            }
            if (isBottomRow(i)) {
                quickFind.union(p, n * n + 1);
            }

            if (j > 1 && isOpen(i, j - 1)) {
                quickFind.union(to1Dim(i, j), to1Dim(i, j - 1));
            }
            if (j < n && isOpen(i, j + 1)) {
                quickFind.union(to1Dim(i, j), to1Dim(i, j + 1));
            }
            if (i > 1 && isOpen(i - 1, j)) {
                quickFind.union(to1Dim(i, j), to1Dim(i - 1, j));
            }
            if (i < n && isOpen(i + 1, j)) {
                quickFind.union(to1Dim(i, j), to1Dim(i + 1, j));
            }
        }
    }

    /**
     * Converts x,y coordinates to vector index
     *
     * @param i - indicates row
     * @param j - indicates column
     * @return vector index based on i and j coordinates
     */
    private int to1Dim(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IndexOutOfBoundsException("Illegal parameter value.");
        }
        return n * (i - 1) + j;
    }

    /**
     * Check whether x,y site is open
     *
     * @param i - indicates row
     * @param j - indicates column
     * @return true if site i,j is open, false otherwise
     */
    public boolean isOpen(int i, int j) {
        rejectNonValidIndices(i, j);
        return states[i - 1][j - 1];
    }

    /**
     * Checks whether x,y site is full
     *
     * @param i - indicates row
     * @param j - indicates column
     * @return true if site i,j is full, false otherwise
     */
    public boolean isFull(int i, int j) {
        rejectNonValidIndices(i, j);
        return quickFind.connected(0, to1Dim(i, j));
    }

    /**
     * Checks if top and bottom rows are connected, i.e. the system is percolating
     *
     * @return true of if top and bottom rows are connected, false otherwise
     */
    public boolean percolates() {
        return quickFind.connected(0, n * n + 1);
    }

    private boolean isTopRow(int x) {
        return x == TOP_ROW;
    }

    private void rejectNonValidIndices(int i, int j) {
        if (i < 1 || j < 1 || i > n || j > n)
            throw new IndexOutOfBoundsException("i or j should be from 1 to N");
    }

    private boolean isBottomRow(int x) {
        return x == n;
    }
}
