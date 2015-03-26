package test;

import graphics.Fenetre;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tools.Circle;
import tools.Rectangle;
import tools.Segment;
import algorithms.Graham;
import algorithms.Ritter;
import algorithms.Toussain;

public class Test {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
	String fichier;
	ArrayList<Point> points, enveloppe, pointsMin = null, enveloppeMin = null;
	BufferedReader br;
	String ligne, tab[];
	double longueur, largeur, aire, Amin = Double.MAX_VALUE;
	Point2D.Double phg, pbg, phd, pbd;
	Rectangle rectangle, rectangleMin = null;
	Circle c = null, cMin = null;
	double qualite = -1, AirePolyg = -1, A = -1, qualiteTot = 0;

	for (int i = 2; i < 1665; i++) {
	    fichier = "samples/test-" + i + ".points";
	    points = new ArrayList<Point>();
	    enveloppe = new ArrayList<Point>();

	    // lecture du fichier texte
	    try {
		br = new BufferedReader(new InputStreamReader(
			new FileInputStream(fichier)));

		while ((ligne = br.readLine()) != null) {
		    tab = ligne.split(" ");
		    points.add(new Point(Integer.parseInt(tab[0]), Integer
			    .parseInt(tab[1])));
		}
		br.close();
	    } catch (Exception e) {
		System.out.println(e.toString());
	    }

	    c = Ritter.calculCercleMin(points);
	    enveloppe = Graham.enveloppeConvexeGraham(points);
	    rectangle = Toussain.ToussaintRectMin(points, enveloppe, c);

	    pbg = rectangle.getPbg();
	    phg = rectangle.getPhg();
	    pbd = rectangle.getPbd();
	    phd = rectangle.getPhd();

	    /* calcul du nouvel air et sauvegarde du nouveau rectangle */
	    longueur = Point2D.Double.distance(phg.getX(), phg.getY(),
		    pbg.getX(), pbg.getY());
	    largeur = Point2D.Double.distance(phg.getX(), phg.getY(),
		    phd.getX(), phd.getY());
	    aire = longueur * largeur;

	    A = 0;

	    for (int j = 0; j < (enveloppe.size() - 1); j++) {
		A += ((enveloppe.get(j).getX() * enveloppe.get(j + 1).getY()) - (enveloppe
			.get(j + 1).getX() * enveloppe.get(j).getY()));
	    }

	    AirePolyg = A / 2;
	    qualite = (aire / AirePolyg) - 1;
	    qualiteTot += qualite;

	    System.out.println("qualite = " + qualite);

	    if (aire < Amin) {
		Amin = aire;
		rectangleMin = rectangle;
		pointsMin = (ArrayList<Point>) points.clone();
		enveloppeMin = (ArrayList<Point>) enveloppe.clone();
		cMin = Ritter.calculCercleMin(points);
	    }
	}

	System.out
		.println("L'aire du rectangle minimal sur tous les rectangles minimaux de tous les fichiers = "
			+ Amin);

	System.out.println("qualiteMoyenne = " + (qualiteTot / 1663));

	pbg = rectangleMin.getPbg();
	phg = rectangleMin.getPhg();
	pbd = rectangleMin.getPbd();
	phd = rectangleMin.getPhd();

	@SuppressWarnings("unused")
	Fenetre f = new Fenetre(1, new Segment(pbg, phg),
		new Segment(pbg, pbd), new Segment(phg, phd), new Segment(pbd,
			phd), pointsMin, enveloppeMin, cMin);
    }
}
