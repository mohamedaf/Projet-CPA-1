package algorithms;

import graphics.WindowView;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import tools.Circle;
import tools.Ligne;
import tools.Rectangle;

public class Toussain {

    public static Rectangle ToussaintRectMin(ArrayList<Point> points,
	    ArrayList<Point> enveloppe, Circle c) {
	if (points.size() < 3) {
	    return null;
	}

	int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE;
	int ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE;
	double a1, a2, a3, a4, angleMin, aire;
	double longueur, largeur, Amin = Double.MAX_VALUE;

	Point Pleft = null, Pright = null, Ptop = null, Pbottom = null;
	Point2D.Double phg, pbg, phd, pbd;
	Ligne leftV, rightV, topH, bottomH;
	Rectangle rectangleMin;
	ArrayList<Double> angles;
	// ArrayList<Point> enveloppe = Graham.enveloppeConvexeGraham(points);
	// Circle c = Ritter.calculCercleMin(points);
	@SuppressWarnings("unused")
	WindowView f;

	/* Recuperation du point le plus a l'ouest de l'enveloppe */
	/* Recuperation du point le plus a l'est de l'enveloppe */
	/* Recuperation du point le plus au sud de l'enveloppe */
	/* Recuperation du point le plus au nord de l'enveloppe */
	for (Point p : enveloppe) {
	    if (p.x < xmin) {
		xmin = p.x;
		Pleft = p;
	    }
	    if (p.x > xmax) {
		xmax = p.x;
		Pright = p;
	    }
	    if (p.y < ymin) {
		ymin = p.y;
		Ptop = p;
	    }
	    if (p.y > ymax) {
		ymax = p.y;
		Pbottom = p;
	    }
	}

	leftV = new Ligne(Pleft, new Point(0, -1));
	rightV = new Ligne(Pright, new Point(0, 1));
	topH = new Ligne(Ptop, new Point(1, 0));
	bottomH = new Ligne(Pbottom, new Point(-1, 0));

	phg = intersectionDroites(topH, leftV);
	pbg = intersectionDroites(bottomH, leftV);
	phd = intersectionDroites(topH, rightV);
	pbd = intersectionDroites(bottomH, rightV);

	rectangleMin = new Rectangle(phg, pbg, phd, pbd);

	/* calcul de l'aire du rectangle Longueur*largeur */
	longueur = phg.distance(pbg);
	largeur = phg.distance(phd);
	Amin = longueur * largeur;

	for (int j = 0; j < enveloppe.size(); j++) {

	    /* calculer les 4 angles puis chercher angle minmal */

	    a1 = calculAngle(
		    topH,
		    Ptop,
		    enveloppe.get((enveloppe.indexOf(Ptop) + 1)
			    % enveloppe.size()));
	    a2 = calculAngle(
		    bottomH,
		    Pbottom,
		    enveloppe.get((enveloppe.indexOf(Pbottom) + 1)
			    % enveloppe.size()));
	    a3 = calculAngle(
		    leftV,
		    Pleft,
		    enveloppe.get((enveloppe.indexOf(Pleft) + 1)
			    % enveloppe.size()));
	    a4 = calculAngle(
		    rightV,
		    Pright,
		    enveloppe.get((enveloppe.indexOf(Pright) + 1)
			    % enveloppe.size()));

	    angles = new ArrayList<Double>();

	    angles.add(a1);
	    angles.add(a2);
	    angles.add(a3);
	    angles.add(a4);

	    angleMin = Collections.min(angles);

	    /* rotation des 4 droites de angleMin */

	    rotationDroite(topH, -angleMin);
	    rotationDroite(bottomH, -angleMin);
	    rotationDroite(leftV, -angleMin);
	    rotationDroite(rightV, -angleMin);

	    /* calcul des nouveaux points d'intersections des 4 droites */

	    phg = intersectionDroites(topH, leftV);
	    pbg = intersectionDroites(bottomH, leftV);
	    phd = intersectionDroites(topH, rightV);
	    pbd = intersectionDroites(bottomH, rightV);

	    /* calcul du nouvel air et sauvegarde du nouveau rectangle */
	    longueur = Point2D.Double.distance(phg.getX(), phg.getY(),
		    pbg.getX(), pbg.getY());
	    largeur = Point2D.Double.distance(phg.getX(), phg.getY(),
		    phd.getX(), phd.getY());
	    aire = longueur * largeur;

	    if (aire < Amin) {
		Amin = aire;
		rectangleMin = new Rectangle(phg, pbg, phd, pbd);
	    }

	    /*
	     * f = new Fenetre(i, new Segment(pbg, phg), new Segment(pbg, pbd),
	     * new Segment(phg, phd), new Segment(pbd, phd), points, enveloppe,
	     * c);
	     */

	    if (angleMin == a1) {
		Ptop = enveloppe.get((enveloppe.indexOf(Ptop) + 1)
			% enveloppe.size());
		topH.setP(Ptop);
	    }
	    if (angleMin == a2) {
		Pbottom = enveloppe.get((enveloppe.indexOf(Pbottom) + 1)
			% enveloppe.size());
		bottomH.setP(Pbottom);
	    }
	    if (angleMin == a3) {
		Pleft = enveloppe.get((enveloppe.indexOf(Pleft) + 1)
			% enveloppe.size());
		leftV.setP(Pleft);
	    }
	    if (angleMin == a4) {
		Pright = enveloppe.get((enveloppe.indexOf(Pright) + 1)
			% enveloppe.size());
		rightV.setP(Pright);
	    }

	}

	pbg = rectangleMin.getPbg();
	phg = rectangleMin.getPhg();
	pbd = rectangleMin.getPbd();
	phd = rectangleMin.getPhd();

	/*
	 * f = new Fenetre(imin, new Segment(pbg, phg), new Segment(pbg, pbd),
	 * new Segment(phg, phd), new Segment(pbd, phd), points, enveloppe, c);
	 */

	return rectangleMin;
    }

    public static double calculAngle(Ligne l, Point p1, Point p2) {
	Point2D.Double v1, v2;
	double EPSILON = 0.00000001, res;

	v1 = l.getVecteurDir();
	v2 = Ligne.ConstruireVecteur(p1, p2);
	res = Math
		.abs(Math.atan(((v1.getY() * v2.getX() - v2.getY() * v1.getX()) / (v1
			.getX() * v2.getX() + v1.getY() * v2.getY()))));
	if (res < EPSILON)
	    return 0;

	return res;
    }

    public static void rotationDroite(Ligne l, double angle) {
	double x, y;
	Point2D.Double v = l.getVecteurDir();

	x = (v.getX() * Math.cos(angle)) + (v.getY() * Math.sin(angle));
	y = (-(v.getX() * Math.sin(angle))) + (v.getY() * Math.cos(angle));

	l.setVecteurDir(new Point2D.Double(x, y));
    }

    public static Point2D.Double intersectionDroites(Ligne l1, Ligne l2) {
	double t;
	Point2D.Double q, p, a, s, r;

	q = new Point2D.Double(l1.getP().getX(), l1.getP().getY());
	p = new Point2D.Double(l2.getP().getX(), l2.getP().getY());
	a = new Point2D.Double(q.getX() - p.getX(), q.getY() - p.getY());
	s = l1.getVecteurDir();
	r = l2.getVecteurDir();

	t = (a.getX() * s.getY() - a.getY() * s.getX())
		/ (r.getX() * s.getY() - r.getY() * s.getX());

	return new Point2D.Double((p.getX() + (t * r.getX())),
		(p.getY() + (t * r.getY())));
    }

}
