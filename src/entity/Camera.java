package entity;

import main.GamePanel;
import main.Renderer;
import utils.Vector2D;


public class Camera{
	Vector2D m_pos;
	private float m_scale;
	private float m_lerp;
	
	public Camera(GamePanel a_gp, Vector2D pos, float scale, float lerp) {
		this.m_pos = pos.copy();
		this.m_scale = scale;
		this.m_lerp = lerp;
	}
	
	public void move(Vector2D target) {
		
		
		this.m_pos.x = this.m_pos.x + (target.x - this.m_pos.x) * m_lerp;
		this.m_pos.y = this.m_pos.y + (target.y - this.m_pos.y) * m_lerp;
	}
	
	public void zoom(float scale) {		
		this.m_scale = m_scale + (scale - m_scale) * m_lerp;
	}
	
	public float getX() {
		return this.m_pos.x;
	}
	
	public float getY() {
		return this.m_pos.y;
	}
	
	public float getScale() {
		return m_scale;
	}
}
