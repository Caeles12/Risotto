package utils;

public abstract class Shape {
	Vector2D m_origin;
	Vector2D m_offset;
	
	public Shape(Vector2D origin, Vector2D offset) {
		this.m_origin = origin;
		this.m_offset = offset;
	}
	
	public Shape(Vector2D origin) {
		this.m_origin = origin;
		this.m_offset = new Vector2D(0, 0);
	}
	
	public Vector2D getOrigin() {
		return m_origin;
	}

	public void setOrigin(Vector2D origin) {
		this.m_origin = origin;
	}
	
	public Vector2D getOffset() {
		return m_offset;
	}
	
	public void setOffset(Vector2D offset) {
		this.m_offset = offset;
	}
	
	public Vector2D getPos() {
		return m_origin.plus(m_offset);
	}
}