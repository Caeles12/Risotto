package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Renderer;

import tile.TileManager;
import utils.Collider;
import utils.Shape;
import utils.Vector2D;
import utils.Rectangle;

/**
 * D�fintition du comportement d'un joueur
 *
 */
public class Player extends Entity{

	GamePanel m_gp;
	KeyHandler m_keyH;
	ArrayList<Item> m_inventaire;
	int m_life;
	int m_magie;
	int[] m_direction;
	float[] m_collision;
	boolean[] m_tape;
	boolean m_spell;
	int c;
	int m_ralentisseur;
	
	Collider m_collider;
	int interact_cooldown;
	
	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.m_inventaire = new ArrayList<Item>();
		this.m_direction = new int[2];
		this.m_tape = new boolean[4];
		this.m_collision = new float[2];
		this.m_pos = new Vector2D(100, 100);
		this.m_collider = new Collider(new Rectangle(m_pos, new Vector2D(a_gp.TILE_SIZE/4, a_gp.TILE_SIZE/4),a_gp.TILE_SIZE/2, a_gp.TILE_SIZE/2), a_gp);
		
		this.setDefaultValues();
		this.getPlayerImageBase();
		
		this.interact_cooldown = 0;
	}
	
	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues() {
		m_speed = 4;
		m_life = 100;
		m_magie = 80;
		for (int i = 0; i < 4; i++) {
			m_tape[i] = false;
		}
		m_spell = false;
	}
	
	/**
	 * R�cup�ration de l'image du personnage
	 */
	public void getPlayerImageBase() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/witch.png")));
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/spellwitch.png")));
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/potitbalais.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void LanceFireball(float x, float y) {
		if (m_ralentisseur <= 0) {
			m_magie -= 1;
			m_spell = true;
			m_ralentisseur = 16;
			Fireball f = new Fireball(m_gp, this, m_keyH, (int) this.m_pos.x + 1, (int) this.m_pos.y);
			f.setDirection(x, y);
			c = 0;
		} else {
			m_ralentisseur -= 1;
		}
	}
	
	/**
	 * Mise � jour des donn�es du joueur
	 */
	public void update() {
		this.m_collider.m_shape.setOrigin(m_pos);
		
		if (m_keyH.isPressed(37)) { // GAUCHE
			m_direction[0] += -1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(38)) { // HAUT
			m_direction[0] += 0;
			m_direction[1] += -1;
		} 
		if (m_keyH.isPressed(39)) { // DROITE
			m_direction[0] += 1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(40)) { // BAS
			m_direction[0] += 0;
			m_direction[1] += 1;
		}
		
		if(m_direction[0] != 0 || m_direction[1] != 0) {
			float norme = (float) Math.sqrt(m_direction[0] * m_direction[0] + m_direction[1] * m_direction[1]);
			
			float vx = (m_direction[0] / norme);
			float vy = (m_direction[1] / norme);
			
			this.m_pos.x += vx*m_speed;
			System.out.println(this.m_collider.m_shape.getOrigin().x);
			if(this.m_collider.collidingTileMap(this.m_gp.m_tileM)) {
				this.m_pos.x -= vx*m_speed;
			}
			this.m_pos.y += vy*m_speed;
			if(this.m_collider.collidingTileMap(this.m_gp.m_tileM)) {
				this.m_pos.y -= vy*m_speed;
			}
		}
		
		m_direction[0] = 0;
		m_direction[1] = 0;
		
	
		int[] fb_dir = {0, 0};
		
		if (m_keyH.isPressed(81) && getMagie() >= 1) { // FireBall q Gauche 
			fb_dir[0] += -1;
			fb_dir[1] += 0;
		} if (m_keyH.isPressed(90) && getMagie() >= 1) { // FireBall z Haut
			fb_dir[0] += 0;
			fb_dir[1] += -1;
		} if (m_keyH.isPressed(68) && getMagie() >= 1) { // FireBall d Droit 
			fb_dir[0] += 1;
			fb_dir[1] += 0;
		} if (m_keyH.isPressed(83) && getMagie() >= 1) { // FireBall s Bas
			fb_dir[0] += 0;
			fb_dir[1] += 1;
		} if (fb_dir[0] != 0 || fb_dir[1] != 0) {
			float norme = (float) Math.sqrt(fb_dir[0] * fb_dir[0] + fb_dir[1] * fb_dir[1]);
			
			float vx = (fb_dir[0] / norme);
			float vy = (fb_dir[1] / norme);
			
			LanceFireball(vx, vy);
		}
		
		interact_cooldown++;
		if (m_keyH.isPressed(69) && interact_cooldown >10) {
			interact_cooldown = 0;
			Iterator<Entity> iter = m_gp.m_tab_Map[m_gp.dim].m_list_entity.iterator();
			while(iter.hasNext()) {
				Entity tmp = iter.next();
				if(tmp instanceof Entity_interactive) {
					if(this.m_pos.distanceTo(tmp.m_pos) < 80) {
						((Entity_interactive) tmp).interaction();
					}
				}
				
			}
		}
		
		if (c > 17) {
			m_spell = false;
		}
		c += 1;
		
	}
	
	/**get_lastPressed
	 * Affichage du l'image du joueur dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Renderer r) {

		// r�cup�re l'image du joueur
		BufferedImage l_image;
		if (m_spell) {
			l_image = m_idleImage.get(1);
		}
		else {
			for (int i = 0; i < 4; i++) {
				m_spell = false;
			}
			l_image = m_idleImage.get(0);
		}
		
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		r.renderImage(l_image, (int) this.m_pos.x, (int) this.m_pos.y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);

	}
	
	public float getXCoordinates() {
		return this.m_pos.x;
	}
	
	public float getYCoordinates() {
		return this.m_pos.y;
	}
	
	public void setTape(int pos, boolean b) {
		m_tape[pos] = b;
	}
	
	public int getMagie() {
		return m_magie;
	}
	
	public boolean fullInventory() {
		return m_inventaire.size() == 10;
	}

	public void addToInventory(Item i) {
		if (!fullInventory()) {
			m_inventaire.add(i);
		} else {
			new SpeechBubble(m_gp, "Poches pleines !", (int) this.m_pos.x, (int) this.m_pos.y - 10);
		}
	}
	
}
