package edu.princeton.algs4.alexeyn.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alexey Novakov
 */
public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();
    private Map<Double, List<Point>> collinearSegments = new HashMap<>();

    public FastCollinearPoints(Point[] points) {
        checkNotNull(points);
        checkDuplicates(points);
        findSegments(points);
    }

    private void findSegments(Point[] origPoints) {
        Point[] points = Arrays.copyOf(origPoints, origPoints.length);

        for (Point p : origPoints) {
            double prevSlope = Double.NEGATIVE_INFINITY;
            double slope = 0;
            List<Point> collinearPoints = new ArrayList<>();

            Arrays.sort(points, p.slopeOrder());
            for (int i = 1; i < points.length; i++) {
                Point q = points[i];
                slope = p.slopeTo(q);

                if (slope == prevSlope) {
                    collinearPoints.add(q);
                } else {
                    checkIfSegmentExistsThenAdd(p, prevSlope, collinearPoints);
                    collinearPoints.clear();
                    collinearPoints.add(q);
                }
                prevSlope = slope;
            }

            checkIfSegmentExistsThenAdd(p, slope, collinearPoints);
        }
    }

    private void checkIfSegmentExistsThenAdd(Point p, double prevSlope, List<Point> collinearPoints) {
        if (collinearPoints.size() >= 3) {
            collinearPoints.add(p);
            addSegment(collinearPoints, prevSlope);
        }
    }

    private void addSegment(List<Point> collinearPoints, double slope) {
        Collections.sort(collinearPoints);
        Point startPoint = collinearPoints.get(0);
        Point endPoint = collinearPoints.get(collinearPoints.size() - 1);

        List<Point> endPoints = collinearSegments.get(slope);
        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            collinearSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point p : endPoints) {
                if (p.compareTo(endPoint) == 0) {
                    return;
                }
            }

            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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
}
