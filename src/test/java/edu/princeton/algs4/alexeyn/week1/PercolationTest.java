package edu.princeton.algs4.alexeyn.week1;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Alexey Novakov
 */
@RunWith(JUnit4.class)
public class PercolationTest {
    public static final int N = 5;
    private Percolation percolation;

    @Before
    public void setUp() {
        percolation = new Percolation(N);
    }

    @Test
    public void testOpen() throws Exception {
        //when
        percolation.open(1, 2);
        percolation.open(2, 2);
        //then
        Assert.assertTrue(percolation.isOpen(1, 2));
        Assert.assertTrue(percolation.isOpen(2, 2));
    }

    @Test
    public void testIsOpen() throws Exception {
        //when
        percolation.open(1, 2);
        percolation.open(2, 2);
        //then
        Assert.assertTrue(percolation.isOpen(2, 2));
    }

    @Test
    public void testIsFullWhenConnectedToTop() throws Exception {
        percolation.open(3, 3);
        Assert.assertFalse(percolation.isFull(3, 3));

        percolation.open(2, 3);
        Assert.assertFalse(percolation.isFull(3, 3));

        percolation.open(1, 3);
        Assert.assertTrue(percolation.isFull(3, 3));
    }

    @Test
    public void testIsFullWhenConnectedToBottomButNotToTop() {
        percolation.open(3, 3);
        Assert.assertFalse(percolation.isFull(3, 3));

        percolation.open(4, 3);
        Assert.assertFalse(percolation.isFull(3, 3));

        percolation.open(5, 3);
        Assert.assertFalse(percolation.isFull(3, 3));
    }

    @Test
    public void testPercolates() throws Exception {
        //given
        int n = 10;
        Percolation percolation = new Percolation(n);

        //when
        while (!percolation.percolates()) {
            int i = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);
            percolation.open(i, j);
        }

        //then
        print(percolation, n);
        Assert.assertTrue(percolation.percolates());
    }

    private void print(Percolation percolation, int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                String mark = percolation.isFull(i, j) ? "x" : (percolation.isOpen(i, j) ? "o" : " ");
                System.out.print(mark);
            }
            System.out.println();
        }
    }

    @Test
    public void singleSiteGridPercolatesWhenOpen() {
        //given
        Percolation percolation = new Percolation(1);

        //when
        percolation.open(1, 1);

        //then
        Assert.assertTrue(percolation.percolates());
    }

    @Test
    public void waterFillsOpenSites() {
        //given
        Percolation percolation = new Percolation(3);

        //when
        percolation.open(3, 3);
        percolation.open(2, 3);
        percolation.open(2, 2);
        percolation.open(1, 1);

        //then
        Assert.assertFalse(percolation.percolates());

        //when
        percolation.open(2, 1);
        //then
        Assert.assertTrue(percolation.percolates());
    }
}