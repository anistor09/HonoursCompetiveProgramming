import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final double INF = 1e18;

    // Function to return the Euclidean distance between two points
    static double dist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2));
    }

    // Function to check whether a point lies inside or on the boundaries of the circle
    static boolean isInside(Circle c, Point p) {
        return dist(c.C, p) <= c.R;
    }

    // Helper method to get a circle defined by 3 points
    static Point getCircleCenter(double bx, double by, double cx, double cy) {
        double B = bx * bx + by * by;
        double C = cx * cx + cy * cy;
        double D = bx * cy - by * cx;
        return new Point((cy * B - by * C) / (2.0 * D), (bx * C - cx * B) / (2.0 * D));
    }

    // Function to return a unique circle that intersects three points
    static Circle circleFrom(Point A, Point B, Point C) {
        Point I = getCircleCenter(B.X - A.X, B.Y - A.Y, C.X - A.X, C.Y - A.Y);

        I.X += A.X;
        I.Y += A.Y;
        return new Circle(I, dist(I, A));
    }

    // Function to return the smallest circle that intersects 2 points
    static Circle circleFrom(Point A, Point B) {
        // Set the center to be the midpoint of A and B
        Point C = new Point((A.X + B.X) / 2.0, (A.Y + B.Y) / 2.0);

        // Set the radius to be half the distance AB
        return new Circle(C, dist(A, B) / 2.0);
    }

    // Function to check whether a circle encloses the given points
    static boolean isValidCircle(Circle c, List<Point> P) {
        // Iterating through all the points to check whether the points
        // lie inside the circle or not
        for (Point p : P) {
            if (!isInside(c, p))
                return false;
        }
        return true;
    }

    // The following two functions are used to find the equation of the circle when three points are given.

    // Function to return the minimum enclosing circle for N <= 3
    static Circle minCircleTrivial(List<Point> P) {
//        assert P.size() <= 3;
        if (P.isEmpty()) {
            return new Circle(new Point(0, 0), 0);
        } else if (P.size() == 1) {
            return new Circle(P.get(0), 0);
        } else if (P.size() == 2) {
            return circleFrom(P.get(0), P.get(1));
        }

        // To check if MEC can be determined by 2 points only
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                Circle c = circleFrom(P.get(i), P.get(j));
                if (isValidCircle(c, P))
                    return c;
            }
        }
        return circleFrom(P.get(0), P.get(1), P.get(2));
    }

    // Returns the MEC using Welzl's algorithm
    // Takes a set of input points P and a set R
    // points on the circle boundary.
    // n represents the number of points in P
    // that are not yet processed.
    static Circle welzlHelper(List<Point> P, List<Point> R, int n) {
        // Base case when all points processed or |R| = 3
        if (n == 0 || R.size() == 3) {
            return minCircleTrivial(R);
        }

        // Pick a random point randomly
        int idx = (int) (Math.random() * n);
        Point p = P.get(idx);

        // Put the picked point at the end of P
        // since it's more efficient than deleting from the middle of the list
        Collections.swap(P, idx, n - 1);

        // Get the MEC circle d from the set of points P - {p}
        Circle d = welzlHelper(P, R, n - 1);

        // If d contains p, return d
        if (isInside(d, p)) {
            return d;
        }

        // Otherwise, must be on the boundary of the MEC
        R.add(p);

        // Return the MEC for P - {p} and R U {p}
        return welzlHelper(P, R, n - 1);
    }

    static Circle welzl(List<Point> P) {
        System.out.println("in");
        List<Point> P_copy = new ArrayList<>(P);
        Collections.shuffle(P_copy);
        return welzlHelper(P_copy, new ArrayList<>(), P_copy.size());
    }

    public static void main(String args[]) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print(mainCalculation(sc) + "\n");
    }

    public static String mainCalculation(Scanner rows) {
        int n = Integer.parseInt(rows.nextLine());
        List<Point> input = new ArrayList();
        for (int i = 0; i < n; i++) {
            String[] nextLine = rows.nextLine().split(" ");
            double x = Double.parseDouble(nextLine[0]);
            double y = Double.parseDouble(nextLine[1]);
            input.add(new Point(x, y));
        }
//        for (int i = 0; i < n; i++) {
//            Point p = input.get(i);
//            System.out.println(p.X +" "+p.Y);
//        }
        Point output = welzl(input).C;
        return output.X + " " + output.Y;


    }

    // Structure to represent a 2D point
    static class Point {
        double X, Y;

        Point(double x, double y) {
            X = x;
            Y = y;
        }
    }

    // Structure to represent a 2D circle
    static class Circle {
        Point C;
        double R;

        Circle(Point center, double radius) {
            C = center;
            R = radius;
        }
    }


}
