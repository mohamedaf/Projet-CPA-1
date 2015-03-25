package test;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tools.Rectangle;
import algorithms.Toussain;

public class Test {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

	String fichier;
	ArrayList<Point> points, enveloppe;
	BufferedReader br;
	String ligne, tab[];

	for (int i = 0; i < 10; i++) {
	    fichier = "samples/test-" + (5 + (10 * i)) + ".points";
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

	    Rectangle rec = Toussain.ToussaintRectMin(points);
	}
    }
}
