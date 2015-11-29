package edu.princeton.algs4.alexeyn.week4;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Alexey Novakov
 */
public class Board {
    private static final int BLANK_BLOCK = 0;
    private int[][] blocks;

    public Board(int[][] blocks) {
        Objects.requireNonNull(blocks);
        this.blocks = deepCopy(blocks);
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() { // number of blocks out of place
        int count = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                boolean isNotBlank = blocks[i][j] != BLANK_BLOCK;
                int goalPosition = to1Dim(i, j);

                if (blocks[i][j] != goalPosition && isNotBlank) {
                    count++;
                }
            }
        }

        return count;
    }

    private int to1Dim(int i, int j) {
        return j + 1 + i * blocks.length;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int distance = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                int block = blocks[i][j];

                if (block != BLANK_BLOCK) {
                    block--;
                    int i1 = block / blocks.length;
                    int j1 = block - (i1 * blocks.length);
                    distance += Math.abs(i - i1) + Math.abs(j - j1);
                }
            }

        }

        return distance;
    }

    public boolean isGoal() { // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        int[] blank = findBlankPos();
        int blankI = blank[0];
        int blankJ = blank[1];
        int i = randomPos();
        int i1 = randomPos();
        int j = randomPos();
        int j1 = randomPos();

        while (i == blankI && j == blankJ) {
            i = randomPos();
            j = randomPos();
        }

        while (i == i1 && j == j1 || (i1 == blankI && j1 == blankJ)) {
            i1 = randomPos();
            j1 = randomPos();
        }

        int[][] blocksCopy = deepCopy(blocks);
        blocksCopy[i][j] = blocksCopy[i1][j1];
        blocksCopy[i1][j1] = blocks[i][j];

        return new Board(blocksCopy);
    }

    private int[][] deepCopy(int[][] matrix) {
        return Arrays.stream(matrix).map(el -> Arrays.copyOf(el, el.length)).toArray($ -> matrix.clone());
    }

    private int randomPos() {
        return StdRandom.uniform(0, blocks.length);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Board board = (Board) that;

        return Arrays.deepEquals(blocks, board.blocks);
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        List<Board> result = new ArrayList<>();
        int[] blankPos = findBlankPos();
        int blankI = blankPos[0];
        int blankJ = blankPos[1];

        if (blankI + 1 < blocks.length) {
            swapAndAdd(matrix -> {
                matrix[blankI][blankJ] = blocks[blankI + 1][blankJ];
                matrix[blankI + 1][blankJ] = BLANK_BLOCK;
            }, result);
        }

        if (blankI - 1 >= 0) {
            swapAndAdd(matrix -> {
                matrix[blankI][blankJ] = blocks[blankI - 1][blankJ];
                matrix[blankI - 1][blankJ] = BLANK_BLOCK;
            }, result);
        }

        if (blankJ + 1 < blocks.length) {
            swapAndAdd(matrix -> {
                matrix[blankI][blankJ] = blocks[blankI][blankJ + 1];
                matrix[blankI][blankJ + 1] = BLANK_BLOCK;
            }, result);
        }

        if (blankJ - 1 >= 0) {
            swapAndAdd(matrix -> {
                matrix[blankI][blankJ] = blocks[blankI][blankJ - 1];
                matrix[blankI][blankJ - 1] = BLANK_BLOCK;
            }, result);
        }

        return result;
    }

    private void swapAndAdd(Consumer<int[][]> swapStrategy, List<Board> set) {
        int[][] copy = deepCopy(blocks);
        swapStrategy.accept(copy);
        set.add(new Board(copy));
    }

    private int[] findBlankPos() {
        int[] result = new int[2];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == BLANK_BLOCK) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }

        throw new RuntimeException("Board has no blank block");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(blocks.length).append("\n");

        for (int[] block : blocks) {
            for (int col = 0; col < blocks.length; col++) {
                sb.append(String.format("%2d ", block[col]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
