package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.GamePanel;
import main.Renderer;
import utils.Collider;
import utils.Vector2D;

/**
 * Entit� de base du jeu
 *
 */
public abstract class Entity {
	public enum Status {
		  RUNNING, DESTROY
	};
	
	protected GamePanel m_gp;
	public Vector2D m_pos;
	public int m_speed;					//D�placement de l'entit�
	public List<BufferedImage> m_idleImage = new ArrayList<>();	//Une image de l'entit�
	public Status m_status = Status.RUNNING;
	protected Collider m_collider;
	protected boolean solid = false;
	
	public abstract void draw(Renderer r);
	public abstract void update();
	
	public boolean checkCollisions() {
		if(this.m_collider.collidingTileMap(this.m_gp.m_tileM)) {
			return true;
		}
		
		for(Entity entity: m_gp.m_tab_Map[m_gp.dim].m_list_entity) {
			if(entity != this && entity.m_collider != null) {
				try {
					if(entity.solid && this.m_collider.colliding(entity.m_collider)) {
						return true;
					}
				} catch(Exception e) {
					System.out.println(entity.getClass().getName());
				}
			}
		}
		
		return false;
	}
}
