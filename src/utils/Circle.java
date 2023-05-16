package utils;

public class Circle extends Shape{
	float m_radius;
	
	public Circle(Vector2D origin, float radius) {
		super(origin);
		this.m_radius = radius;
	}

	public float getRadius() {
		return m_radius;
	}

	public void setRadius(float radius) {
		this.m_radius = radius;
	}
}