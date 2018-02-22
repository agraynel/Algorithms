import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        if (points == null)
            throw new IllegalArgumentException("null!");
        for (Point p: points) {
            if (p == null)
                throw new IllegalArgumentException("null!");
        }

        Point[] newPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);

        for (int i = 1; i < newPoints.length; i++) {
            if (newPoints[i - 1].compareTo(newPoints[i]) == 0)
                throw new IllegalArgumentException("duplicate!");
        }

        helper(newPoints);
    }

    public int numberOfSegments() { // the number of line segments{
        return lineSegments.size();
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] res = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            res[i] = lineSegments.get(i);
        }
        return res;
    }

    private void helper(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Comparator<Point> slopeOrder = p.slopeOrder();
            Point[] newPoints = new Point[points.length];
            System.arraycopy( points, 0, newPoints, 0, points.length );
            Arrays.sort(newPoints, slopeOrder);

            int start = 1, end = 2;
            while (end < newPoints.length) {
                if (slopeOrder.compare(newPoints[start], newPoints[end]) == 0) {
                    end++;

                } else {
                    if (end - start >= 3) {
                        if (newPoints[start].compareTo(newPoints[start + 1]) < 0)
                            lineSegments.add(new LineSegment(newPoints[start], newPoints[end - 1]));
                    }
                    start = end;
                    end++;
                }
            }
        }
    }
}