import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        if (points == null || points.length == 0)
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

        for (int i = 0; i < newPoints.length - 3; i++) {
            Arrays.sort(newPoints);
            Arrays.sort(newPoints, newPoints[i].slopeOrder());

            for (int start = 1, end = 2; end < newPoints.length; end++) {
                while (end < newPoints.length && newPoints[0].slopeTo(newPoints[start]) == newPoints[0].slopeTo(newPoints[end])) {
                    end++;
                }
                if (end - start >= 3 && newPoints[0].compareTo(newPoints[start]) < 0)
                    lineSegments.add(new LineSegment(newPoints[0], newPoints[end - 1]));
                start = end;
            }
        }
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
}