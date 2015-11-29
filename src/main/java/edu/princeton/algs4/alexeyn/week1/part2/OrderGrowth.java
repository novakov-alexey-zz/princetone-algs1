package edu.princeton.algs4.alexeyn.week1.part2;

/**
 * @author Alexey Novakov
 */
public class OrderGrowth {

    public static void main(String[] args) {
        int sum = 0;
        int N = 1000;
        for (int i = 1; i <= N; i = i * 2) {
            sum++;
            //System.out.println(i);
        }

        //x^2 < N x*4
        // 4^2x < N
        // 2x * log4 < logN
        // x = logN

        //2*i < N^2


        for (int i = 2636; i < 2690; i++) {
            if (i % 8 == 0) {
                System.out.println("/8 = " + i);
                break;
            }
        }

        N = 100;
        sum = 0;
        for (int i = 1; i <= N*N; i = i*2)
            for (int j = 0; j < i; j++)
                sum++;

        System.out.println("sum = " + sum);
        // 100^x = 16383
        // x * log100 < log100
    }

}

class MysteryBox { // 16 bytes
    private  int x0, x1, x2; // 3 * 4
    private  double y0, y1, y2; // 3 * 8
    private  boolean z0; // 1
    private  long[] a = new long[136]; // 8 * 136 + 24 mas + 8 ref
    //padding 229
}
