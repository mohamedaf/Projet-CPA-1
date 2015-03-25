package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class Graham {

    public static ArrayList<Point> enveloppeConvexeTriParPixel(
	    ArrayList<Point> points) {
	Point[] ymin = new Point[2000];
	Point[] ymax = new Point[2000];
	ArrayList<Point> l = new ArrayList<Point>();

	for (Point p : points) {
	    if (ymax[p.x] == null || ymax[p.x].y < p.y) {
		ymax[p.x] = p;
	    }

	    if (ymin[p.x] == null || ymin[p.x].y > p.y) {
		ymin[p.x] = p;
	    }
	}

	for (int i = 0; i < ymin.length; i++) {
	    if (ymin[i] != null) {
		l.add(ymin[i]);
	    }
	}

	for (int i = (ymax.length - 1); i >= 0; i--) {
	    if (ymax[i] != null) {
		l.add(ymax[i]);
	    }
	}

	if (l.get(l.size() - 1).equals(l.get(0)))
	    l.remove(l.size() - 1);

	return l;
    }

    private static double produit_vectoriel(Point p, Point q, Point r) {
	return ((q.getX() - p.getX()) * (r.getY() - p.getY()))
		- ((q.getY() - p.getY()) * (r.getX() - p.getX()));
    }

    private static boolean tourne_a_droite(Point p, Point q, Point r) {
	return (produit_vectoriel(p, q, r) < 0);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Point> enveloppeConvexeGraham(
	    ArrayList<Point> points) {
	int i;
	Point p, q, r;
	ArrayList<Point> enveloppe = (ArrayList<Point>) enveloppeConvexeTriParPixel(
		points).clone();

	for (i = 0; i + 1 < enveloppe.size(); i++) {
	    p = enveloppe.get(i);
	    q = enveloppe.get((i + 1) % enveloppe.size());
	    r = enveloppe.get((i + 2) % enveloppe.size());

	    if (tourne_a_droite(p, q, r)) {
		enveloppe.remove(i + 1);
		if (i != 0)
		    i--;
		i--;
	    }
	}

	/* supprimer doublons consecutifs eventuels */

	for (i = 1; i < enveloppe.size(); i++) {
	    if ((enveloppe.get(i).x == enveloppe.get(i - 1).x)
		    && (enveloppe.get(i).y == enveloppe.get(i - 1).y)) {
		enveloppe.remove(i);
		i--;
	    }
	}

	return enveloppe;
    }

}
