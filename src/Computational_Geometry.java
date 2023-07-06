public class Computational_Geometry {
    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static Point difference(Point p1, Point p2) {
        Point p = new Point(p1.x, p1.y);
        p.x = p.x - p2.x;
        p.y = p.y - p2.y;
        return p;
    }

    private static double cross_product(Point p1, Point p2) {
        return (p1.x * p2.y) - (p2.x * p1.y);
    }

    public static double direction(Point i, Point j, Point k) {
        return (cross_product(difference(k, i), difference(j, i)));
    }

    public static boolean on_segment(Point i, Point j, Point k) {
        return (Math.min(i.x, j.x) <= k.x && k.x <= Math.max(i.x, j.x)) &&
                (Math.min(i.y, j.y) <= k.y && k.y <= Math.max(i.y, j.y));
    }

    public static boolean segments_intersect(Point p1, Point p2, Point p3, Point p4) {
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0)))
            return true;
        else if ((d1 == 0) && on_segment(p3, p4, p1))
            return true;
        else if ((d2 == 0) && on_segment(p3, p4, p2))
            return true;
        else if ((d3 == 0) && on_segment(p1, p2, p3))
            return true;
        else return (d4 == 0) && on_segment(p1, p2, p4);
    }

    public static void main(String[] args) {
        System.out.println(segments_intersect(new Point(0, 0), new Point(10, 10),
                new Point(10, 0), new Point(0, 10)));
    }
}
