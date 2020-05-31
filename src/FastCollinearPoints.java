import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        Point[] aux = new Point[n - 1];
        // Think of p as the origin
        for (int i = 0; i < n; i++) {
            // For each other point q, determine the slope it makes with p
            int k = 0;
            int j = 0;
            while (j < n) {
                if (i != j) {
                    aux[k++] = copy[j];
                }
                j++;
            }
            Point point = copy[i];
            Arrays.sort(aux, point.slopeOrder());
            ArrayList<Point> segment = new ArrayList<Point>();
            segment.add(point);
            for (int idx = 0; idx < n - 2; idx++) {
                double slope1 = point.slopeTo(aux[idx]);
                if (Double.NEGATIVE_INFINITY == slope1) {
                    throw new IllegalArgumentException("Points are equal");
                }
                double slope2 = point.slopeTo(aux[idx + 1]);
                if (Double.NEGATIVE_INFINITY == slope2) {
                    throw new IllegalArgumentException("Points are equal");
                }
                if (Double.compare(slope1, slope2) == 0) {
                    segment.add(aux[idx]);
                } else {
                    segment.add(aux[idx]);
                    if (segment.size() > 2) {
                        Point[] segmentArr = new Point[segment.size()];
                        segmentArr = segment.toArray(segmentArr);
                        Arrays.sort(segmentArr);
                        if (segmentArr[0].compareTo(point) == 0) {
                            auxSegments.add(new LineSegment(point, segmentArr[segmentArr.length - 1]));
                        }
                    }
                    segment.clear();
                    segment.add(point);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}