package edu.princeton.algs4.alexeyn.week3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexey Novakov
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkNotNull(points);
        checkDuplicates(points);
        findSegments(points);
    }

    private void findSegments(Point[] origPoints) {
        List<LineSegment> foundSegments = new LinkedList<>();

        Point[] points = Arrays.copyOf(origPoints, origPoints.length);
        Arrays.sort(points);

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                                points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            foundSegments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(String.format("Point %s is repeated", points[j]));
                }
            }
        }
    }

    private void checkNotNull(Point[] points) {
        Objects.requireNonNull(points);
        Arrays.stream(points).forEach(Objects::requireNonNull);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}
