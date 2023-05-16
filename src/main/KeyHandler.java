package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

/**
 * Gestionnaire d'�v�nements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{
	
	HashSet<Integer> m_lastPressed = new HashSet<Integer>();

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// r�cup�re le code du boutton appuy�
		int code = e.getKeyCode();
		//System.out.println(code);
		m_lastPressed.add(code);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		m_lastPressed.remove(code);
	}
	
	public boolean isPressed(int elt) {
		return m_lastPressed.contains(elt);
	}
	
}
