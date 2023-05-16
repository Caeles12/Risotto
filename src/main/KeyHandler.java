package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'�v�nements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{
	
	int m_lastPressed;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// r�cup�re le code du boutton appuy�
		int code = e.getKeyCode();
		System.out.println(code);
		m_lastPressed = code;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		m_lastPressed = -1;
	}
	
	public int get_lastPressed() {
		return m_lastPressed;
	}

}
