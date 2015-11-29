package edu.princeton.algs4.alexeyn.week1.part1;

/**
 * @author Alexey Novakov
 */
public abstract class QuickUnion {
    protected final int[] id;

    public QuickUnion(int n) {
        this.id = new int[n];

        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    public abstract void union(int p, int q);

    protected void print() {
        for (int anId : id) {
            System.out.print(anId + " ");
        }
    }

    protected void unionBatch(int[][] batch) {
        for (int[] aBatch : batch) {
            union(aBatch[0], aBatch[1]);
        }
    }

    protected int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }

        return i;
    }
}
