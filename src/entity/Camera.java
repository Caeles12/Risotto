package entity;

import main.GamePanel;
import main.Renderer;


public class Camera{
  
	private float m_x;
	private float m_y;
	private float m_scale;
	private float m_lerp;
	
	public Camera(GamePanel a_gp, int pos_x, int pos_y, float scale, float lerp) {
		this.m_x = pos_x;
		this.m_y = pos_y;
		this.m_scale = scale;
		this.m_lerp = lerp;
	}
	
	public void move(int x, int y) {
		int targetX = x;
		int targetY = y;
		float currentX = m_x;
		float currentY = m_y;
		
		this.m_x = currentX + (targetX - currentX) * m_lerp;
		this.m_y = currentY + (targetY - currentY) * m_lerp;
	}
	
	public void zoom(float scale) {		
		this.m_scale = m_scale + (scale - m_scale) * m_lerp;
	}
	
	public float getX() {
		return m_x;
	}
	
	public float getY() {
		return m_y;
	}
	
	public float getScale() {
		return m_scale;
	}

	@Override
	public void draw(Renderer a_g2) {
		// TODO Auto-generated method stub
		
	}
}
