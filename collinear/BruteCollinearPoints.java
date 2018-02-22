import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null)
            throw new IllegalArgumentException("null!");
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("null!");
        }

        Point[] newPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(newPoints);

        for (int i = 1; i < newPoints.length; i++) {
            if (newPoints[i - 1].compareTo(newPoints[i]) == 0)
                throw new IllegalArgumentException("duplicate!");
        }

        for (int i = 0; i < newPoints.length; i++) {
            Point p1 = newPoints[i];
            for (int j = i + 1; j < newPoints.length; j++) {
                Point p2 = newPoints[j];
                for (int k = j + 1; k < newPoints.length; k++) {
                    Point p3 = newPoints[k];

                    if (p1.slopeTo(p2) == p1.slopeTo(p3)) {
                        for (int l = k + 1; l < newPoints.length; l++) {
                            Point p4 = newPoints[l];
                            if (p1.slopeTo(p2) == p1.slopeTo(p4)) {
                                Point[] sortPoints = {p1, p2, p3, p4};
                                Arrays.sort(sortPoints);
                                lineSegments.add(new LineSegment(sortPoints[0], sortPoints[3]));
                            }
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments() { // the number of line segments
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