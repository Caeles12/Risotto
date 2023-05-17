package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
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
	private int frame;
	
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
		this.frame = 0;
	}
	
	public void update() {
		frame ++;
	}
	
	public void setGraphics(Graphics2D g2) {
		this.m_g2 = g2;
		m_g2.setFont(m_font);
	}
	
	public void setFontSize(int size) {
		this.m_font = m_font_base.deriveFont(Font.PLAIN, size);
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
	
	public void renderUIImage(BufferedImage image, int x, int y, int w, int h) {
		assert(this.m_g2 != null);
		
		m_g2.drawImage(image, x, y, w, h, null);
	}
	
	public void renderCircle(int x, int y, int r) {
		
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		int radius = (int) Math.ceil(r*scale);
		m_g2.setColor(Color.RED);
		m_g2.setStroke(new BasicStroke(5f));
		m_g2.drawOval(posX-radius, posY-radius, radius*2, radius*2);
	}
	
	public void renderRect(int x, int y, int w, int h) {
		assert(this.m_g2 != null);
		
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		int scaleW = (int) Math.ceil(w*scale);
		int scaleH = (int) Math.ceil(h * scale);
		m_g2.setColor(Color.GREEN);
		m_g2.setStroke(new BasicStroke(5f));
		m_g2.drawRect(posX, posY, scaleW, scaleH);
	}
	
	public void renderRect(int x, int y, int w, int h, Color color) {
		assert(this.m_g2 != null);
		
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		int scaleW = (int) Math.ceil(w*scale);
		int scaleH = (int) Math.ceil(h * scale);
		m_g2.setColor(color);
		m_g2.fillRect(posX, posY, scaleW, scaleH);
	}
	
	public void renderUIRect(int x, int y, int w, int h, Color color) {
		assert(this.m_g2 != null);
				
		m_g2.setColor(color);
		m_g2.fillRect(x, y, w, h);
	}
	
	public void renderText(String text, int x, int y) {
		renderText(text, x, y, 1, 0, 0, 0);
	}
	
	public void renderCenteredText(String text, int x, int y) {
		renderText(text, x, y, 1, 0, 0, 0);
	}
	
	public void renderText(String text, int x, int y, float opacity) {
		renderText(text, x, y, opacity, 0, 0, 0);
	}
	
	public void renderCenteredText(String text, int x, int y, float opacity) {
		renderText(text, x, y, opacity, 0, 0, 0);
	}
	
	public void renderText(String text, int x, int y, float opacity,  float wobbleAmplitude, float wobbleSpeed, float wobbleFrequency) {
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		
		this.renderTextToScreen(text, posX, posY, opacity, wobbleAmplitude, wobbleSpeed, wobbleFrequency);
	}
	
	public void renderCenteredText(String text, int x, int y, float opacity,  float wobbleAmplitude, float wobbleSpeed, float wobbleFrequency) {
		float scale = this.m_camera.getScale();
		
		int posX = (int) (Math.floor((x - this.m_camera.getX())*scale) + (this.m_gp.SCREEN_WIDTH/2));
		int posY = (int) (Math.floor((y - this.m_camera.getY())*scale) + (this.m_gp.SCREEN_HEIGHT/2));
		
		this.renderCenteredTextToScreen(text, posX, posY, opacity, wobbleAmplitude, wobbleSpeed, wobbleFrequency);
	}
	
	public void renderTextToScreen(String text, int x, int y) {
		renderTextToScreen(text, x, y, 1, 0, 0, 0);
	}
	
	public void renderCenteredTextToScreen(String text, int x, int y) {
		renderCenteredTextToScreen(text, x, y, 1, 0, 0, 0);
	}
	
	public void renderTextToScreen(String text, int x, int y, float opacity) {
		renderTextToScreen(text, x, y, opacity, 0, 0, 0);
	}
	
	public void renderCenteredTextToScreen(String text, int x, int y, float opacity) {
		renderCenteredTextToScreen(text, x, y, opacity, 0, 0, 0);
	}
	
	
	public void renderCenteredTextToScreen(String text, int x, int y, float opacity, float wobbleAmplitude, float wobbleSpeed, float wobbleFrequency) {
		int n_x = (int) (x-(m_font.getStringBounds(text, m_g2.getFontRenderContext()).getWidth()/2));
		renderTextToScreen(text, n_x, y, opacity, wobbleAmplitude, wobbleSpeed, wobbleFrequency);
	}
	
	public void renderTextToScreen(String text, int x, int y, float opacity, float wobbleAmplitude, float wobbleSpeed, float wobbleFrequency) {
		renderTextToScreen(text, x, y, opacity, wobbleAmplitude, wobbleSpeed, wobbleFrequency,Color.black,Color.white);
	}
	
public void renderTextToScreen(String text, int x, int y, float opacity, float wobbleAmplitude, float wobbleSpeed, float wobbleFrequency, Color c1, Color c2) {
		
		
		float amplitude = wobbleAmplitude;
		float speed = wobbleSpeed;
		float frequency = wobbleFrequency;
		
		if(speed<=0) {
			speed = 1;
		}
		if(frequency==0) {
			frequency = 1;
		}
		
		int char_x = x;
		m_g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		for(int i=0; i<text.length(); i++) {
			int char_y = y;
			char_y += Math.sin((i+frame/speed)*Math.PI/frequency)*amplitude;
			TextLayout tl = new TextLayout(text.charAt(i)+"", m_font, m_g2.getFontRenderContext());
			Shape shape = tl.getOutline(null);
			m_g2.translate(char_x, char_y);
			m_g2.setStroke(new BasicStroke(5f));
			m_g2.setColor(c1);
			m_g2.draw(shape);
			m_g2.setColor(c2);
			m_g2.fill(shape);
			m_g2.translate(-char_x, -char_y);
			char_x += (int) m_font.getStringBounds(text.charAt(i)+"", m_g2.getFontRenderContext()).getWidth();
		}
		m_g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
}
