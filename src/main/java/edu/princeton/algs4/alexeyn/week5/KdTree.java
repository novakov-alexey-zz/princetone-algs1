package edu.princeton.algs4.alexeyn.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexey Novakov
 */
public class KdTree {
    private static final double Y_MAX = 1;
    private static final double X_MIN = 0;
    private static final double Y_MIN = 0;
    private static final double X_MAX = 1;
    private Node root;
    private int treeSize;

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private RectHV rect;

        public Node(Point2D point, RectHV rectHV) {
            this.point = point;
            this.rect = rectHV;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return treeSize;
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean vertical) {
        if (node == null) {
            treeSize++;
            return new Node(p, rect);
        }
        if (node.point.equals(p)) return node;

        RectHV newRect;

        int cmp = getComparator(vertical).compare(node.point, p);
        if (cmp > 0) {
            if (node.left == null)
                newRect = vertical ? new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax())
                        : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
            else
                newRect = node.left.rect;
            node.left = insert(node.left, p, newRect, !vertical);
        } else {
            if (node.right == null)
                newRect = vertical ? new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax())
                        : new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            else
                newRect = node.right.rect;
            node.right = insert(node.right, p, newRect, !vertical);
        }

        return node;
    }

    private Comparator<Point2D> getComparator(boolean vertical) {
        return vertical ? Point2D.X_ORDER : Point2D.Y_ORDER;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty())
            root = insert(root, p, new RectHV(X_MIN, Y_MIN, X_MAX, Y_MAX), true);
        else
            root = insert(root, p, root.rect, true);
    }

    public boolean contains(Point2D p) {
        return search(root, p, true);
    }

    private boolean search(Node node, Point2D p, boolean vertical) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;

        int cmp = getComparator(vertical).compare(node.point, p);

        if (cmp > 0)
            return search(node.left, p, !vertical);
        else
            return search(node.right, p, !vertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isXAxis) {
        if (node != null && node.point != null) {
            node.point.draw();
            draw(node.left, !isXAxis);
            draw(node.right, !isXAxis);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        return range(root, rect, new LinkedList<>());
    }

    private List<Point2D> range(Node node, RectHV rect, List<Point2D> aggregator) {
        if (node == null)
            return aggregator;

        if (rect.contains(node.point))
            aggregator.add(node.point);

        if (node.left != null && rect.intersects(node.left.rect)) {
            range(node.left, rect, aggregator);
        }

        if (node.right != null && rect.intersects(node.right.rect)) {
            range(node.right, rect, aggregator);
        }

        return aggregator;
    }

    private Point2D nearest(Node node, Point2D p, Point2D mp, boolean vert) {
        Point2D min = mp;

        if (node == null) return min;
        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(min))
            min = node.point;

        if ((vert && node.point.x() < p.x()) || node.point.y() < p.y()) {
            if (node.point.x() < p.x()) {
                min = nearest(node.right, p, min, !vert);
                if (node.left != null && (min.distanceSquaredTo(p) > node.left.rect.distanceSquaredTo(p)))
                    min = nearest(node.left, p, min, !vert);
            } else {
                min = nearest(node.left, p, min, !vert);
                if (node.right != null && (min.distanceSquaredTo(p) > node.right.rect.distanceSquaredTo(p)))
                    min = nearest(node.right, p, min, !vert);
            }
        } else {
            if (node.point.y() < p.y()) {
                min = nearest(node.right, p, min, !vert);
                if (node.left != null && (min.distanceSquaredTo(p) > node.left.rect.distanceSquaredTo(p)))
                    min = nearest(node.left, p, min, !vert);
            } else {
                min = nearest(node.left, p, min, !vert);
                if (node.right != null && (min.distanceSquaredTo(p) > node.right.rect.distanceSquaredTo(p)))
                    min = nearest(node.right, p, min, !vert);
            }
        }
        return min;
    }

    // a nearest neighbor in the set to p: null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, p, root.point, true);
    }
}
