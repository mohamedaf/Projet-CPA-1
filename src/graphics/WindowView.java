package graphics;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import test.TestF;
import tools.Circle;
import tools.Segment;

@SuppressWarnings("serial")
public class WindowView extends JFrame {

    private Dessin dessin;

    public WindowView(int num, Segment s1, Segment s2, Segment s3, Segment s4,
	    ArrayList<Point> points, ArrayList<Point> enveloppe, Circle c,
	    TestF f) {
	this.setTitle("Test Rectangle");
	this.setSize(900, 700);
	// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setBackground(Color.BLACK);

	dessin = new Dessin(num, s1, s2, s3, s4, points, enveloppe, c, f);

	this.setContentPane(dessin);
	// ajoute un écouteur d'événements personnalisé à la fenêtre
	this.addKeyListener(new WindowKeyListener(dessin));

	this.setVisible(true);
    }

    public Dessin getDessin() {
	return dessin;
    }

    public void setDessin(Dessin dessin) {
	this.dessin = dessin;
    }

    public void modifDessin(Segment s1, Segment s2, Segment s3, Segment s4) {
	dessin.setS1(s1);
	dessin.setS2(s2);
	dessin.setS3(s3);
	dessin.setS4(s4);
	dessin.repaint();
    }

}
