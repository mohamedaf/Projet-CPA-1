package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowKeyListener implements KeyListener {
    private final Dessin dessin;

    public WindowKeyListener(Dessin dessin) {
	this.dessin = dessin;
    }

    @Override
    public void keyPressed(KeyEvent e) {
	dessin.nextFile();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
