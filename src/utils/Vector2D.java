package utils;

public class Vector2D {
	public float x;
	public float y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float dot(Vector2D other) {
		return (this.x*other.x)+(this.y*other.y);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(this.dot(this));
	}
	
	public float distanceTo(Vector2D other) {
		return this.minus(other).magnitude();
	}
	
	public Vector2D minus(Vector2D other) {
		return new Vector2D(this.x-other.x, this.y-other.y);
	}
	
	public Vector2D plus(Vector2D other) {
		return new Vector2D(this.x+other.x, this.y+other.y);
	}
	
	public Vector2D scale(float factor) {
		return new Vector2D(this.x * factor, this.y*factor);
	}
	
	public Vector2D direction() {
		return this.scale(1.0f / this.magnitude());
	}
	
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

}
