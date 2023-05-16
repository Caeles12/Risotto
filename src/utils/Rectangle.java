package utils;

public class Rectangle extends Shape{
	float m_width;
	float m_height;
	
	
	public Rectangle(Vector2D origin, Vector2D offset, float width, float height) {
		super(origin, offset);
		this.m_width = width;
		this.m_height = height;
	}
	
	public Rectangle(Vector2D origin, float width, float height) {
		super(origin);
		this.m_width = width;
		this.m_height = height;
	}
	
	public float getWidth() {
		return this.m_width;
	}
	
	public void setWidth(float width) {
		this.m_width = width;
	}
	
	public float getHeight() {
		return this.m_height;
	}
	
	public void setHeight(float height) {
		this.m_height = height;
	}
}