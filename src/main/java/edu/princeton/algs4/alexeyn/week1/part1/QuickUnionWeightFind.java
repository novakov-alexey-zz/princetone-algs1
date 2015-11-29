package edu.princeton.algs4.alexeyn.week1.part1;

/**
 * @author Alexey Novakov
 */
public class QuickUnionWeightFind extends QuickUnion{
    private int[] sz;

    public static void main(String[] args) {
        QuickUnionWeightFind unionFind = new QuickUnionWeightFind(10);
        unionFind.unionBatch(new int[][]{{9,7}, {7,1}, {3,0}, {7,5}, {8,7}, {6,4}, {6,3}, {6,1}, {0,2}});
        unionFind.print();
    }

    public QuickUnionWeightFind(int n) {
        super(n);
        this.sz = new int[n];
        for (int i = 0; i < sz.length; i++) {
            sz[i] = 1;
        }
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);

        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }

    }
}
