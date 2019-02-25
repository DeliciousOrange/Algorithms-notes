/******************************************************************************
 *  Name:   LianYing Zhang
 *  NetID:  lianying168000
 *  Precept:    P00
 *
 *  Description:    An immutable data type for points in the plane.
 *
 *  Written:    18/02/2019
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;    //  x-coordinate of this point
    private final int y;    //  y-coordinate of this point

    // Initializes a new point
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //  Draws this point to standard draw.
    public void draw() {
        StdDraw.point(x, y);
    }

    //  Draws the line segment between this point and the specified point to standard draw
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // Returns a string representation of this point.
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    //  Compares two points biy y-coordinate, breaking ties by x-coordinate.
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        else if (this.y > that.y) {
            return +1;
        }
        else if (this.x < that.x) {
            return -1;
        }
        else if (this.x > that.x) {
            return +1;
        }
        else {
            return 0;
        }
    }

    // Returns the slope between this point and the specified point.
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        else if (this.y == that.y) {
            return +0.0;
        }
        else {
            return (double) (that.y - this.y) / (that.x - this.x);
        }
    }

    private static int compare1(Point u, Point v, Point w) {
        if (v.slopeTo(u) > w.slopeTo(u)) {
            return +1;
        }
        else if (v.slopeTo(u) < w.slopeTo(u)) {
            return -1;
        }
        else {
            return 0;
        }

    }

    //  Compares two points by the slope they make with this point.
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    // Inner class.
    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            return compare1(Point.this, v, w);
        }
    }

    // Unit tests the Point data type.
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(1, 3);
        Point p4 = new Point(3, 2);

        StdOut.println(p1.slopeTo(p2));
        StdOut.println(p1.slopeTo(p3));
        StdOut.println(p1.slopeTo(p4));
        StdOut.println(p1.slopeTo(p1));

        StdOut.println(p1.compareTo(p2));
        StdOut.println(p1.compareTo(p3));
        StdOut.println(p1.compareTo(p4));

        Comparator<Point> cmp = p1.slopeOrder();
        StdOut.println(cmp.compare(p2, p3));
        StdOut.println(cmp.compare(p2, p4));
        StdOut.println(cmp.compare(p3, p4));
    }
}
