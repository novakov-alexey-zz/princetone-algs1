package edu.princeton.algs4.alexeyn.week1.part1;

/**
 * @author Alexey Novakov
 */
public class SimpleQuickUnion extends QuickUnion {

    public static void main(String[] args) {
        SimpleQuickUnion unionFind = new SimpleQuickUnion(10);
        unionFind.unionBatch(new int[][]{{4, 8}, {1, 0}, {0, 9}, {7, 5}, {5, 3}, {3, 2}});
        unionFind.print();
    }

    public SimpleQuickUnion(int n) {
        super(n);
    }

    @Override
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
}
