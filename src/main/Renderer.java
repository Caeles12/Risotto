package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Camera;

public class Renderer {
	private Camera m_camera;
	private Graphics2D m_g2;
	private GamePanel m_gp;
	
	public Renderer(GamePanel a_gp, Camera camera) {
		this.m_gp = a_gp;
		this.m_camera = camera;
		this.m_g2 = null;
	}
	
	public void setGraphics(Graphics2D g2) {
		this.m_g2 = g2;
	}
	
	public void renderImage(BufferedImage image, int x, int y, int w, int h) {
		assert(this.m_g2 != null);
		int posX = x - this.m_camera.m_x + (this.m_gp.SCREEN_WIDTH/2);
		int posY = y - this.m_camera.m_y + (this.m_gp.SCREEN_HEIGHT/2);
		m_g2.drawImage(image, posX, posY, w, h, null);
	}
}
