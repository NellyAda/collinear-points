import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("No points provided");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("There is a null point");
            }
        }
        Point[] copy = points.clone();
        Arrays.sort(copy);
        for (int i = 0; i < copy.length; i++) {
            if ((i + 1 < copy.length) && (copy[i].compareTo(copy[i + 1]) == 0)) {
                throw new IllegalArgumentException("There are duplicated points");
            }
        }

        int n = copy.length;
        ArrayList<LineSegment> auxSegments = new ArrayList<>();
        double slope1;
        double slope2;
        double slope3;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                slope1 = copy[i].slopeTo(copy[j]);
                if (slope1 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Points are equal");
                }
                for (int k = j + 1; k < n - 1; k++) {
                    slope2 = copy[j].slopeTo(copy[k]);
                    if (slope2 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException("Points are equal");
                    }
                    if (Double.compare(slope1, slope2) == 0) {
                        for (int m = k + 1; m < n; m++) {
                            slope3 = copy[k].slopeTo(copy[m]);
                            if (slope3 == Double.NEGATIVE_INFINITY) {
                                throw new IllegalArgumentException("Points are equal");
                            }
                            if (Double.compare(slope2, slope3) == 0) {
                                auxSegments.add(new LineSegment(copy[i], copy[m]));
                            }
                        }
                    }
                }
            }
        }
        LineSegment[] segmentsArr = new LineSegment[auxSegments.size()];
        segments = auxSegments.toArray(segmentsArr);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}