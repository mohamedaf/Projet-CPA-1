package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import tools.Circle;

public class Ritter {

    // calculCercleMin: ArrayList<Point> --> Circle
    // renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    @SuppressWarnings("unchecked")
    public static Circle calculCercleMin(ArrayList<Point> points) {

	if (points.size() < 3 || points.isEmpty()) {
	    return null;
	}

	int i;
	double d, rad, a, b, a2 = 0, b2 = 0, cps, ccp, cs = 0, max;
	Point dummy, p = null, q = null, c, s;
	ArrayList<Point> lp = (ArrayList<Point>) points.clone();

	/* Algo Ritter */

	/* Etape 1 */
	dummy = points.get(0);
	max = Integer.MIN_VALUE;

	/* Etape 2 */
	for (Point pi : points) {
	    d = pi.distance(dummy);
	    if (d >= max) {
		max = d;
		p = pi;
	    }
	}

	max = Integer.MIN_VALUE;

	/* Etape 3 */
	for (Point pi : points) {
	    d = pi.distance(p);
	    if (d >= max) {
		max = d;
		q = pi;
	    }
	}

	/* Etape 4 */
	c = new Point((p.x + q.x) / 2, (p.y + q.y) / 2);

	/* Etape 5 */
	rad = (int) p.distance(c);

	/* Etape 11 */
	while (!lp.isEmpty()) {
	    /* Etape 6 */
	    for (i = 0; i < lp.size(); i++) {
		if ((Point.distance(lp.get(i).x, lp.get(i).y, c.x, c.y)) <= rad) {
		    lp.remove(i);
		    i--; /*
			  * Car on supprime des element et size est compte a
			  * chaque tour de boucle
			  */
		}
	    }

	    /* Etape 7 ------> 10 */
	    if (!lp.isEmpty()) {
		/* c = a*c + b*c */
		/* a = (|cps|/|cs|) */
		/* b = (|ccp|/|cs|) */
		/* |cps| = (rad + |cs|) / 2 */
		/* |ccp| = |cs| - |cps| */

		s = lp.get(0);

		cs = Point.distance(s.x, s.y, c.x, c.y);
		cps = (rad + cs) / 2;
		ccp = cs - cps;

		a = cps / cs;
		b = ccp / cs;

		a2 = (a * c.x) + (b * s.x);
		b2 = (a * c.y) + (b * s.y);

		c.setLocation(a2, b2);
		rad = (rad + cs) / 2;
		lp.remove(s);
	    }
	}

	return new Circle(c, (int) rad);
    }

}
