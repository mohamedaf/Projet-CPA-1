package tools;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Segment {
    private Point2D.Double a, b;

    public Segment(Point a, Point b) {
	this.a = new Point2D.Double(a.x, a.y);
	this.b = new Point2D.Double(b.x, b.y);
    }

    public Segment(Point2D.Double a, Point2D.Double b) {
	this.a = a;
	this.b = b;
    }

    public Point2D.Double getA() {
	return a;
    }

    public void setA(Point2D.Double a) {
	this.a = a;
    }

    public Point2D.Double getB() {
	return b;
    }

    public void setB(Point2D.Double b) {
	this.b = b;
    }
}
