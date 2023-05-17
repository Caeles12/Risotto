package main;

import java.awt.Color;

public class UI {
	
	public static void text(GamePanel gp, String text, int x, int y, int fontSize) {
		Renderer r = gp.m_renderer;
		r.setFontSize(fontSize);
		r.renderTextToScreen(text, x, y);
		r.setFontSize(24);
	}
	
	public static void text(GamePanel gp, String text, int x, int y, int fontSize, float w, float h , float f) {
		Renderer r = gp.m_renderer;
		r.setFontSize(fontSize);
		r.renderTextToScreen(text, x, y,1,w,h,f);
		r.setFontSize(24);
	}
	
	public static void text(GamePanel gp, String text, int x, int y, int fontSize, float w, float h , float f, Color c1, Color c2) {
		Renderer r = gp.m_renderer;
		r.setFontSize(fontSize);
		r.renderTextToScreen(text, x, y,1,w,h,f,c1,c2);
		r.setFontSize(24);
	}
}