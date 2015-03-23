package algorithms;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class DefaultTeam {

    // calculCercleMin: ArrayList<Point> --> Circle
    // renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    public Circle calculCercleMin(ArrayList<Point> points) {

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

		c = new Point((int) a2, (int) b2);
		rad = (int) (c.distance(s));
		lp.remove(s);
	    }

	    if (!lp.isEmpty()) {
		rad = (rad + cs) / 2;
		c.setLocation(a2, b2);
	    }
	}

	return new Circle(c, (int) rad);
    }

    public ArrayList<Point> enveloppeConvexeTriParPixel(ArrayList<Point> points) {
	System.out.println("taille avant : " + points.size());
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

    private double produit_vectoriel(Point p, Point q, Point r) {
	return ((q.getX() - p.getX()) * (r.getY() - p.getY()))
		- ((q.getY() - p.getY()) * (r.getX() - p.getX()));
    }

    private boolean tourne_a_droite(Point p, Point q, Point r) {
	return (produit_vectoriel(p, q, r) <= 0);
    }

    public ArrayList<Point> enveloppeConvexeGraham(ArrayList<Point> points) {
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

    // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
    // renvoie l'enveloppe convexe de la liste.
    public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points) {
	if (points.size() < 3) {
	    return null;
	}

	ArrayList<Point> enveloppe = enveloppeConvexeGraham(points);

	for (Point p : enveloppe) {
	    System.out.println("x : " + p.getX() + ", y : " + p.getY() + " ");
	}

	ToussaintRectMin(points);

	return enveloppe;
    }

    public Rectangle ToussaintRectMin(ArrayList<Point> points) {
	if (points.size() < 3) {
	    return null;
	}

	int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE;
	int ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE;
	double a1, a2, a3, a4, angleMin, aire, deg = 57.2957795;
	double longueur, largeur, Amin = Double.MAX_VALUE;

	Point Pleft = null, Pright = null, Ptop = null, Pbottom = null;
	Point2D.Double phg, pbg, phd, pbd;
	Ligne leftV, rightV, topH, bottomH;
	Rectangle rectangleMin;
	ArrayList<Double> angles;
	ArrayList<Point> enveloppe = enveloppeConvexeGraham(points);
	Circle c = calculCercleMin(points);

	for (Point p : enveloppe) {
	    System.out.println("x : " + p.getX() + ", y : " + p.getY() + " ");
	}

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

	leftV = new Ligne(Pleft, Pleft, new Point(Pleft.x, Pleft.y - 5));
	rightV = new Ligne(Pright, Pright, new Point(Pright.x, Pright.y + 5));
	topH = new Ligne(Ptop, Ptop, new Point(Ptop.x + 5, Ptop.y));
	bottomH = new Ligne(Pbottom, Pbottom, new Point(Pbottom.x - 5,
		Pbottom.y));

	phg = new Point2D.Double(xmin, ymin);
	pbg = new Point2D.Double(xmin, ymax);
	phd = new Point2D.Double(xmax, ymin);
	pbd = new Point2D.Double(xmax, ymax);

	rectangleMin = new Rectangle(phg, pbg, phd, pbd);

	Fenetre f = new Fenetre(1, new Segment(pbg, phg),
		new Segment(pbg, pbd), new Segment(phg, phd), new Segment(pbd,
			phd), points, enveloppe, c);

	/* calcul de l'aire du rectangle Longueur*largeur */
	longueur = Point.distance(xmin, ymin, xmin, ymax);
	largeur = Point.distance(xmin, ymax, xmax, ymax);

	Amin = longueur * largeur;

	int i = 2, j, r = 0;

	System.out.println("Titi\n\n\n");

	for (j = 0; j < enveloppe.size(); i++, j++) {

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

	    System.out.println("avant Rotation");

	    System.out.println("a1top : " + a1 + ", a2bottom : " + a2
		    + ", a3left : " + a3 + ", a4right : " + a4);

	    angles = new ArrayList<Double>();

	    angles.add(a1);
	    angles.add(a2);
	    angles.add(a3);
	    angles.add(a4);

	    angleMin = Collections.min(angles);

	    System.out.println("angleMin : " + angleMin + ", en deg : "
		    + (angleMin * deg));

	    /* rotation des 4 droites de angleMin */

	    rotationDroite(topH, angleMin);
	    rotationDroite(bottomH, angleMin);
	    rotationDroite(leftV, angleMin);
	    rotationDroite(rightV, angleMin);

	    /*************************************************************/
	    /*
	     * System.out
	     * .println("Apres Rotation: (l'un des angles doit etre = 0)");
	     * 
	     * a1 = calculAngle( topH, Ptop,
	     * enveloppe.get((enveloppe.indexOf(Ptop) + 1) % enveloppe.size()));
	     * a2 = calculAngle( bottomH, Pbottom,
	     * enveloppe.get((enveloppe.indexOf(Pbottom) + 1) %
	     * enveloppe.size())); a3 = calculAngle( leftV, Pleft,
	     * enveloppe.get((enveloppe.indexOf(Pleft) + 1) %
	     * enveloppe.size())); a4 = calculAngle( rightV, Pright,
	     * enveloppe.get((enveloppe.indexOf(Pright) + 1) %
	     * enveloppe.size()));
	     * 
	     * angles = new ArrayList<Double>();
	     * 
	     * angles.add(a1); angles.add(a2); angles.add(a3); angles.add(a4);
	     * 
	     * angleMin = Collections.min(angles);
	     * 
	     * System.out.println("a1top : " + a1 + ", a2bottom : " + a2 +
	     * ", a3left : " + a3 + ", a4right : " + a4);
	     */
	    /*************************************************************/

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

	    f = new Fenetre(i, new Segment(pbg, phg), new Segment(pbg, pbd),
		    new Segment(phg, phd), new Segment(pbd, phd), points,
		    enveloppe, c);

	    if (angleMin == a1) {
		Ptop = enveloppe.get((enveloppe.indexOf(Ptop) + 1)
			% enveloppe.size());
	    } else if (angleMin == a2) {
		Pbottom = enveloppe.get((enveloppe.indexOf(Pbottom) + 1)
			% enveloppe.size());
	    } else if (angleMin == a3) {
		Pleft = enveloppe.get((enveloppe.indexOf(Pleft) + 1)
			% enveloppe.size());
	    } else if (angleMin == a4) {
		Pright = enveloppe.get((enveloppe.indexOf(Pright) + 1)
			% enveloppe.size());
	    } else
		System.out.println("Probleme !!!!!  \n");

	}

	pbg = rectangleMin.getPbg();
	phg = rectangleMin.getPhg();
	pbd = rectangleMin.getPbd();
	phd = rectangleMin.getPhd();

	f = new Fenetre(20, new Segment(pbg, phg), new Segment(pbg, pbd),
		new Segment(phg, phd), new Segment(pbd, phd), points,
		enveloppe, c);

	return rectangleMin;
    }

    public double calculAngle(Ligne l, Point p1, Point p2) {
	Point2D.Double v1, v2;

	v1 = l.getVecteurDir();
	v2 = l.ConstruireVecteur(p1, p2);

	return Math
		.abs(Math.atan(((v1.getY() * v2.getX() - v2.getY() * v1.getX()) / (v1
			.getX() * v2.getX() + v1.getY() * v2.getY()))))/*
								        * *
								        * 57.2957795
								        * )
								        */;
    }

    public void rotationDroite(Ligne l, double angle) {
	double x, y;
	Point2D.Double v = l.getVecteurDir();

	x = (v.getX() * Math.cos(angle)) - (v.getY() * Math.sin(angle));
	y = (v.getX() * Math.sin(angle)) + (v.getY() * Math.cos(angle));

	l.setVecteurDir(new Point2D.Double(x, y));
    }

    public Point2D.Double intersectionDroites(Ligne l1, Ligne l2) {
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
