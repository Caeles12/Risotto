package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderer;
import utils.Collider;
import utils.Rectangle;
import utils.Vector2D;

abstract public class Hostile extends Entity{

	protected GamePanel m_gp;
	protected int m_damage;
	protected int m_life;
	protected int m_timer;
	protected int m_timerAnimation;
	protected Random r = new Random();
	protected int ptr_list_image = 0;
	protected Collider m_collider;
	protected int[] m_dir;
	
	/**
	 * Constructeur de Hostile
	 * @param a_gp GamePanel, pannel principal du jeu 
	 */
	public Hostile(GamePanel a_gp) {
		this.m_gp = a_gp;
		this.setDefaultValues();
		this.getHostileImage();
		this.m_collider = new Collider(new Rectangle(m_pos, new Vector2D(a_gp.TILE_SIZE/4, a_gp.TILE_SIZE/4),a_gp.TILE_SIZE/2, a_gp.TILE_SIZE/2), a_gp);
	}
	
	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	abstract protected void setDefaultValues();
	
	/**
	 * R�cup�ration de l'image du monstre
	 */
	abstract public void getHostileImage();
	abstract protected void animationRate();
	
	/**
	 * Mise � jour des donn�es du monstre
	 */
	public void update() {
		m_timer ++;
		m_timerAnimation ++;
		move();
		animationRate();
		
	}
	
	/**
	 * Movement du monstre
	 */
	protected void move()
	{
		float norme = 1;
		this.m_collider.m_shape.setOrigin(m_pos);
		
		if(m_dir[0] != 0 || m_dir[1] != 0) {
			norme = (float) Math.sqrt(m_dir[0] * m_dir[0] + m_dir[1] * m_dir[1]);
		}
		
		float vx = (m_dir[0] / norme);
		float vy = (m_dir[1] / norme);
		
		this.m_pos.x += vx*m_speed;
		if(this.m_collider.collidingTileMap(this.m_gp.m_tileM)) {
			this.m_pos.x -= vx*m_speed;
		}
		this.m_pos.y += vy*m_speed;
		if(this.m_collider.collidingTileMap(this.m_gp.m_tileM)) {
			this.m_pos.y -= vy*m_speed;
		}
	}
	
	protected void lifeBar() {
		
	}
	
	/**
	 * Affichage du l'image du monstre dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	
	public void draw(Renderer a_g2) {
		// r�cup�re l'image du joueur
		if(ptr_list_image >= m_idleImage.size()) ptr_list_image =0;
		
		BufferedImage l_image = m_idleImage.get(ptr_list_image);
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.renderImage(l_image, (int) this.m_pos.x, (int) this.m_pos.y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
	}
	
}
