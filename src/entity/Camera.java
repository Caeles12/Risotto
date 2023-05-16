package entity;

import main.GamePanel;

public class Camera extends Entity{
	public Camera(GamePanel a_gp, int pos_x, int pos_y) {
		this.m_x = pos_x;
		this.m_y = pos_y;
	}
	
	public void move(int x, int y) {
		this.m_x = x;
		this.m_y = y;
	}
}
