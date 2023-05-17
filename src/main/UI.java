package main;

public class UI {
	
	public static void text(GamePanel gp, String text, int x, int y, int fontSize) {
		Renderer r = gp.m_renderer;
		r.setFontSize(fontSize);
		r.renderTextToScreen(text, x, y);
		r.setFontSize(24);
	}
}