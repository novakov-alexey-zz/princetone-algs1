package edu.princeton.algs4.alexeyn.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Alexey Novakov
 */
public class PointSET {
    private Set<Point2D> set;

    public PointSET() { // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() { // is the set empty?
        return set.isEmpty();
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {   // add the point to the set (if it is not already in the set)
        Objects.requireNonNull(p);
        set.add(p);
    }

    public boolean contains(Point2D p) {  // does the set contain point p?
        Objects.requireNonNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        set.stream().forEach(p -> StdDraw.point(p.x(), p.y()));
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Objects.requireNonNull(rect);
        return set.stream().filter(rect::contains).collect(Collectors.toList());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) return null;

        Point2D nearest = set.iterator().next();
        double minDistance = nearest.distanceSquaredTo(p);

        for (Point2D currentPoint : set) {
            double distance = currentPoint.distanceSquaredTo(p);
            if (distance < minDistance) {
                nearest = currentPoint;
                minDistance = distance;
            }
        }

        return nearest;
    }
}
