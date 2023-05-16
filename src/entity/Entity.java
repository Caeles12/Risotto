package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Renderer;
import utils.Vector2D;

/**
 * Entit� de base du jeu
 *
 */
public abstract class Entity {
	public enum Status {
		  RUNNING, DESTROY
	};
	
	public Vector2D m_pos;
	public int m_speed;					//D�placement de l'entit�
	public List<BufferedImage> m_idleImage = new ArrayList<>();	//Une image de l'entit�
	public Status m_status = Status.RUNNING;
	
	public abstract void draw(Renderer r);
	public abstract void update();
}
