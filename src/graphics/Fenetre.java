package graphics;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import tools.Circle;
import tools.Segment;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {

    private Dessin dessin;

    public Fenetre(int num, Segment s1, Segment s2, Segment s3, Segment s4,
	    ArrayList<Point> points, ArrayList<Point> enveloppe, Circle c) {
	this.setTitle("Test Rectangle");
	this.setSize(900, 700);
	// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);

	dessin = new Dessin(num, s1, s2, s3, s4, points, enveloppe, c);

	this.setContentPane(dessin);
	this.setVisible(true);
    }

    public void modifDessin(Segment s1, Segment s2, Segment s3, Segment s4) {
	dessin.setS1(s1);
	dessin.setS2(s2);
	dessin.setS3(s3);
	dessin.setS4(s4);
	dessin.repaint();
    }

}
