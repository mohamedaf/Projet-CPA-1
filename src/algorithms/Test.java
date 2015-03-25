package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

	String fichier = "samples/test-56.points";
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Point> enveloppe = new ArrayList<Point>();

	// lecture du fichier texte
	try {
	    InputStream ips = new FileInputStream(fichier);
	    InputStreamReader ipsr = new InputStreamReader(ips);
	    BufferedReader br = new BufferedReader(ipsr);
	    String ligne;
	    String tab[];

	    while ((ligne = br.readLine()) != null) {
		tab = ligne.split(" ");
		points.add(new Point(Integer.parseInt(tab[0]), Integer
			.parseInt(tab[1])));
	    }
	    br.close();
	} catch (Exception e) {
	    System.out.println(e.toString());
	}

	DefaultTeam d = new DefaultTeam();
	Rectangle rec = d.ToussaintRectMin(points);
    }
}
