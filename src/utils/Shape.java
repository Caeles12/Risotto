package utils;

public abstract class Shape {
	Vector2D m_origin;
	
	public Shape(Vector2D origin) {
		this.m_origin = origin;
	}
	
	public Vector2D getOrigin() {
		return m_origin;
	}

	public void setOrigin(Vector2D origin) {
		this.m_origin = origin;
	}
}