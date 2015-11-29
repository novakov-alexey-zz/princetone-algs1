package edu.princeton.algs4.alexeyn.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.stream.IntStream;


/**
 * @author Alexey Novakov
 */
public class Subset {

    public static void main(String[] args) {
        String[] elements = getInputString().trim().split(" ");
        int k = getNumberOfElementsToPoll(args);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        Arrays.stream(elements).forEach(queue::enqueue);
        IntStream.range(0, k).forEach(i -> StdOut.println(queue.dequeue()));
    }

    private static int getNumberOfElementsToPoll(String[] args) {
        if (args != null && args.length > 0)
            return Integer.parseInt(args[0]);
        else
            throw new IllegalArgumentException("There is no k number entered!");
    }

    private static String getInputString() {
        String input = StdIn.readLine();

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("There is no input string entered!");
        }
        return input;
    }
}
