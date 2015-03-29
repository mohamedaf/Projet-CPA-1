package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import test.TestF;
import tools.Circle;
import tools.Segment;

@SuppressWarnings("serial")
public class Dessin extends JPanel {
    private int num;
    private Segment s1, s2, s3, s4;
    private ArrayList<Point> points;
    private ArrayList<Point> enveloppe;
    private Circle c;
    private TestF f = null;

    private static int index = 3;

    public Dessin(int num, Segment s1, Segment s2, Segment s3, Segment s4,
	    ArrayList<Point> points, ArrayList<Point> enveloppe, Circle c,
	    TestF f) {
	this.num = num;
	this.s1 = s1;
	this.s2 = s2;
	this.s3 = s3;
	this.s4 = s4;
	this.points = points;
	this.enveloppe = enveloppe;
	this.c = c;
	this.f = f;
	this.index = num + 1;
    }

    public int getNum() {
	return num;
    }

    public void setNum(int num) {
	this.num = num;
    }

    public ArrayList<Point> getPoints() {
	return points;
    }

    public void setPoints(ArrayList<Point> points) {
	this.points = points;
    }

    public ArrayList<Point> getEnveloppe() {
	return enveloppe;
    }

    public void setEnveloppe(ArrayList<Point> enveloppe) {
	this.enveloppe = enveloppe;
    }

    public void setS1(Segment s1) {
	this.s1 = s1;
    }

    public void setS2(Segment s2) {
	this.s2 = s2;
    }

    public void setS3(Segment s3) {
	this.s3 = s3;
    }

    public void setS4(Segment s4) {
	this.s4 = s4;
    }

    public Segment getS1() {
	return s1;
    }

    public Segment getS2() {
	return s2;
    }

    public Segment getS3() {
	return s3;
    }

    public Segment getS4() {
	return s4;
    }

    public Circle getC() {
	return c;
    }

    public void setC(Circle c) {
	this.c = c;
    }

    public TestF getF() {
	return f;
    }

    public void setF(TestF f) {
	this.f = f;
    }

    @Override
    public void paintComponent(Graphics g) {
	int x1, y1, x2, y2;

	g.setColor(Color.WHITE);
	for (Point p : points) {
	    g.drawRect(p.x, p.y, 1, 1);
	}

	g.setColor(Color.RED);

	g.drawOval((c.getCenter().x - c.getRadius()),
		(c.getCenter().y - c.getRadius()), 2 * c.getRadius(),
		2 * c.getRadius());

	g.setColor(Color.MAGENTA);

	for (int i = 0; i < enveloppe.size(); i++) {
	    x1 = enveloppe.get(i).x;
	    y1 = enveloppe.get(i).y;
	    x2 = enveloppe.get((i + 1) % enveloppe.size()).x;
	    y2 = enveloppe.get((i + 1) % enveloppe.size()).y;
	    g.drawLine(x1, y1, x2, y2);
	}

	g.setColor(Color.WHITE);
	g.drawString("Tape 'e' to change file", 10, 20);

	if (num != -1) {
	    g.drawString("Fichier numero : " + num, 10, 40);
	}

	g.setColor(Color.CYAN);
	g.drawLine((int) s1.getA().getX(), (int) s1.getA().getY(), (int) s1
		.getB().getX(), (int) s1.getB().getY());
	g.drawLine((int) s2.getA().getX(), (int) s2.getA().getY(), (int) s2
		.getB().getX(), (int) s2.getB().getY());
	g.drawLine((int) s3.getA().getX(), (int) s3.getA().getY(), (int) s3
		.getB().getX(), (int) s3.getB().getY());
	g.drawLine((int) s4.getA().getX(), (int) s4.getA().getY(), (int) s4
		.getB().getX(), (int) s4.getB().getY());
    }

    @SuppressWarnings("static-access")
    public void nextFile() {
	if (index < 1665 && (f != null)) {
	    f.TestFile(this.index++);
	    this.setBackground(Color.BLACK);
	    this.repaint();
	}
    }
}
