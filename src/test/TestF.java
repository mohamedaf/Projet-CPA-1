package test;

import graphics.WindowView;

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

public class TestF {

    private WindowView f = null;

    public TestF() {
    }

    public void TestFile(int i) {
	String fichier;
	ArrayList<Point> points, enveloppe;
	BufferedReader br;
	String ligne, tab[];
	double longueur, largeur, aire;
	Point2D.Double phg, pbg, phd, pbd;
	Rectangle rectangleMin = null;
	Circle c = null;
	double qualite = -1, AirePolyg = -1, A = 0;

	fichier = "samples/test-" + i + ".points";
	points = new ArrayList<Point>();
	enveloppe = new ArrayList<Point>();

	// lecture du fichier texte
	try {
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(
		    fichier)));

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
	rectangleMin = Toussain.ToussaintRectMin(points, enveloppe, c);

	pbg = rectangleMin.getPbg();
	phg = rectangleMin.getPhg();
	pbd = rectangleMin.getPbd();
	phd = rectangleMin.getPhd();

	/* calcul du nouvel air et sauvegarde du nouveau rectangle */
	longueur = Point2D.Double.distance(phg.getX(), phg.getY(), pbg.getX(),
		pbg.getY());
	largeur = Point2D.Double.distance(phg.getX(), phg.getY(), phd.getX(),
		phd.getY());
	aire = longueur * largeur;

	System.out.println("aire rectangle Min = " + aire);

	for (int j = 0; j < (enveloppe.size() - 1); j++) {
	    A += ((enveloppe.get(j).getX() * enveloppe.get(j + 1).getY()) - (enveloppe
		    .get(j + 1).getX() * enveloppe.get(j).getY()));
	}

	AirePolyg = A / 2;
	qualite = (aire / AirePolyg) - 1;

	System.out.println("qualite = " + qualite);

	if (f == null) {
	    f = new WindowView(i, new Segment(pbg, phg), new Segment(pbg, pbd),
		    new Segment(phg, phd), new Segment(pbd, phd), points,
		    enveloppe, c, this);
	} else {
	    f.getDessin().setNum(i);
	    f.getDessin().setS1(new Segment(pbg, phg));
	    f.getDessin().setS2(new Segment(pbg, pbd));
	    f.getDessin().setS3(new Segment(phg, phd));
	    f.getDessin().setS4(new Segment(pbd, phd));
	    f.getDessin().setPoints(points);
	    f.getDessin().setEnveloppe(enveloppe);
	    f.getDessin().setC(c);
	}
    }

    @SuppressWarnings("unchecked")
    public void Test() {
	String fichier;
	ArrayList<Point> points, enveloppe, pointsMin = null, enveloppeMin = null;
	BufferedReader br;
	String ligne, tab[];
	double longueur, largeur, aire, Amin = Double.MAX_VALUE;
	Point2D.Double phg, pbg, phd, pbd;
	Rectangle rectangle, rectangleMin = null;
	Circle c = null, cMin = null;
	double qualiteRec = -1, qualiteCer = -1, AirePolyg = -1, AireCercle = -1;
	double A = -1, qualiteTotRec = 0, qualiteTotCer = 0;

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

	    qualiteRec = (aire / AirePolyg) - 1;
	    qualiteCer = ((Math.PI * Math.pow(c.getRadius(), 2)) / AirePolyg) - 1;

	    qualiteTotRec += qualiteRec;
	    qualiteTotCer += qualiteCer;

	    System.out.println("qualite Rectangle min = " + qualiteRec);
	    System.out.println("qualite Cercle min = " + qualiteCer);

	    if (aire < Amin) {
		Amin = aire;
		rectangleMin = rectangle;
		pointsMin = (ArrayList<Point>) points.clone();
		enveloppeMin = (ArrayList<Point>) enveloppe.clone();
		cMin = c;
	    }
	}

	System.out.println("\nL'aire du rectangle minimal sur tous les \n"
		+ "rectangles minimaux de tous les fichiers = " + Amin);

	System.out.println("\nqualiteMoyenne Rectangle min = "
		+ (qualiteTotRec / 1663));

	System.out.println("\nqualiteMoyenne Cercle min = "
		+ (qualiteTotCer / 1663));

	pbg = rectangleMin.getPbg();
	phg = rectangleMin.getPhg();
	pbd = rectangleMin.getPbd();
	phd = rectangleMin.getPhd();

	f = new WindowView(-1, new Segment(pbg, phg), new Segment(pbg, pbd),
		new Segment(phg, phd), new Segment(pbd, phd), pointsMin,
		enveloppeMin, cMin, null);
    }
}
