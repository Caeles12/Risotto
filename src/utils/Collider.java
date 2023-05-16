package utils;

import main.GamePanel;
import tile.Tile;
import tile.TileManager;

public class Collider {
	Shape m_shape;
	GamePanel m_gp;
	
	public Collider(Shape shape, GamePanel a_gp) {
		this.m_shape = shape;
		this.m_gp = a_gp;
	}
	
	public boolean colliding(Collider collider) {
		return this.colliding(collider.m_shape);
	}
	
	public boolean colliding(Shape other) {
		if(this.m_shape instanceof Circle && other instanceof Circle) {
			Circle thisShape = (Circle) this.m_shape;
			Circle otherShape = (Circle) other;
			
			return circleCircleCollision(thisShape, otherShape);
		}
		else if(this.m_shape instanceof Rectangle && other instanceof Rectangle) {
			Rectangle thisShape = (Rectangle) this.m_shape;
			Rectangle otherShape = (Rectangle) other;
			
			return rectRectCollision(thisShape, otherShape);
		}
		else if(this.m_shape instanceof Circle && other instanceof Rectangle) {
			Circle thisShape = (Circle) this.m_shape;
			Rectangle otherShape = (Rectangle) other;
			
			return circleRectCollision(thisShape, otherShape);
		}
		else if(this.m_shape instanceof Rectangle && other instanceof Circle) {
			Circle otherShape = (Circle) other;
			Rectangle thisShape = (Rectangle) this.m_shape;
			
			return circleRectCollision(otherShape, thisShape);
		}
		
		return false;
	}
	
	public boolean collidingTileMap(TileManager tm) {
		int x = (int) (this.m_shape.m_origin.x + m_gp.TILE_SIZE/2) / m_gp.TILE_SIZE;
		int y = (int) (this.m_shape.m_origin.y + m_gp.TILE_SIZE/2) / m_gp.TILE_SIZE;
		int[][] mapTileNum = tm.getMapTileNum();
		Tile[] tiles = tm.getTile();
		int maxW = tm.getNbCol();
		int maxH = tm.getNbRow();
		
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				if((x+i>=0 && x+i<maxW) && (y+j>=0 && y+j<maxH)) {
					if(tiles[mapTileNum[x+i][y+j]].m_collision) {
						Rectangle rect = new Rectangle(new Vector2D((x+i)*m_gp.TILE_SIZE, (y+j)*m_gp.TILE_SIZE), m_gp.TILE_SIZE, m_gp.TILE_SIZE);
						if(this.colliding(rect)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private static boolean circleCircleCollision(Circle c1, Circle c2) {
		return c1.m_origin.distanceTo(c2.m_origin) < c1.m_radius + c2.m_radius;
	}
	
	private static boolean circleRectCollision(Circle c, Rectangle r) {
		float testX = c.m_origin.x;
		float testY = c.m_origin.y;
		
		if(c.m_origin.x < r.m_origin.x)	testX = r.m_origin.x;
		else if(c.m_origin.x > r.m_origin.x + r.m_width) testX = r.m_origin.x + r.m_width;
		if(c.m_origin.y < r.m_origin.y)	testY = r.m_origin.y;
		else if(c.m_origin.y > r.m_origin.y + r.m_height) testY = r.m_origin.y + r.m_height;
		
		float distX = Math.abs(c.m_origin.x - testX);
		float distY = Math.abs(c.m_origin.y - testY);
		
		float distance = (float) Math.sqrt( (distX*distX) + (distY*distY) );
		
		return distance <= c.m_radius;
	}
	
	private static boolean rectRectCollision(Rectangle r1, Rectangle r2) {
		return 	r1.m_origin.x + r1.m_width >= r2.m_origin.x &&
				r1.m_origin.x <= r2.m_origin.x + r2.m_width &&
				r1.m_origin.y + r1.m_height >= r2.m_origin.y &&
				r1.m_origin.y  <= r2.m_origin.y + r2.m_height;
	}
}
