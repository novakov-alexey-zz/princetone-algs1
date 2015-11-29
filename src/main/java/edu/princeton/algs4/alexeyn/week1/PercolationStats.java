package edu.princeton.algs4.alexeyn.week1;


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Alexey Novakov
 */
public class PercolationStats {
    private final double[] percolationThresholds;

    /**
     * perform times independent experiments on an n-by-n grid
     *
     * @param n     - size of the grid (height & width)
     * @param times - number times to perform the experiment
     */
    public PercolationStats(int n, int times) {
        if (n <= 0 || times <= 0) throw new IllegalArgumentException("Both arguments should be > 0");

        percolationThresholds = new double[times];
        process(n);
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(percolationThresholds);
    }

    /**
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(percolationThresholds.length));
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(percolationThresholds.length));
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Please enter N for size of the matrix and T as times of experiment separated by space");
        } else {
            try {
                int n = Integer.parseInt(args[0]);
                int times = Integer.parseInt(args[1]);
                PercolationStats percolationStats = new PercolationStats(n, times);
                System.out.printf("mean                     = %s%n", percolationStats.mean());
                System.out.printf("stddev                   = %s%n", percolationStats.stddev());
                System.out.printf("95%% confidence interval  = %s, %s%n",
                        percolationStats.confidenceLo(), percolationStats.confidenceHi());
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid arguments. " + nfe.getMessage());
            }
        }
    }

    private void process(int n) {
        for (int t = 0; t < percolationThresholds.length; t++) {
            percolationThresholds[t] = run(new Percolation(n), n);
        }
    }

    private double run(Percolation percolation, int n) {
        int opened = 0;
        while (!percolation.percolates()) {
            int i = getUniformRandom(n);
            int j = getUniformRandom(n);

            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                opened++;
            }
        }
        return getPercolationThreshold(opened, n);
    }

    private double getPercolationThreshold(int opened, int n) {
        int square = n * n;
        return (double) opened / square;
    }

    private int getUniformRandom(int n) {
        return StdRandom.uniform(1, n + 1);
    }
}
