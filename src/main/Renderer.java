package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Shape;
import java.io.IOException;

import entity.Camera;

public class Renderer {
	private Camera m_camera;
	private Graphics2D m_g2;
	private GamePanel m_gp;
	private Font m_font_base;
	private Font m_font;
	
	public Renderer(GamePanel a_gp, Camera camera) {
		this.m_gp = a_gp;
		this.m_camera = camera;
		this.m_g2 = null;
		try {
			this.m_font_base = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/fonts/Perfect DOS VGA 437.ttf").openStream());
		} catch (FontFormatException | IOException e) {
			this.m_font_base = new Font("TimesRoman", Font.PLAIN, 24);
			e.printStackTrace();
		}
		this.m_font = m_font_base.deriveFont(Font.PLAIN, 24);
	}
	
	public void setGraphics(Graphics2D g2) {
		this.m_g2 = g2;
		m_g2.setFont(m_font);
	}
	
	public void renderImage(BufferedImage image, int x, int y, int w, int h) {
		assert(this.m_g2 != null);
		
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		int scaleW = (int) Math.ceil(w*scale);
		int scaleH = (int) Math.ceil(h * scale);
		m_g2.drawImage(image, posX, posY, scaleW, scaleH, null);
	}
	
	public void renderText(String text, int x, int y) {
		
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		
		this.renderTextToScreen(text, posX, posY);
	}
	
	public void renderTextToScreen(String text, int x, int y) {
		TextLayout tl = new TextLayout(text, m_font, m_g2.getFontRenderContext());
		Shape shape = tl.getOutline(null);
		m_g2.translate(x, y);
		m_g2.setColor(Color.black);
		m_g2.draw(shape);
		m_g2.setColor(Color.white);
		m_g2.fill(shape);
		m_g2.translate(-x, -y);
	}
}
