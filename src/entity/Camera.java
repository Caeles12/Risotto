package entity;

import main.GamePanel;

public class Camera extends Entity{
	
	private float m_scale;
	
	public Camera(GamePanel a_gp, int pos_x, int pos_y, float scale) {
		this.m_x = pos_x;
		this.m_y = pos_y;
		this.m_scale = scale;
	}
	
	public void move(int x, int y) {
		int targetX = x;
		int targetY = y;
		int currentX = m_x;
		int currentY = m_y;
		
		this.m_x = (int) (currentX + (targetX - currentX) * 0.1);
		this.m_y = (int) (currentY + (targetY - currentY) * 0.1);
	}
	
	public float getScale() {
		return m_scale;
	}
}
