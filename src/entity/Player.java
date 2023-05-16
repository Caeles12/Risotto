package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	ArrayList<Integer> m_inventaire;
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
	
	String nextText = "";
	
	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.m_inventaire = new ArrayList<Integer>();
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
	
	/**
	 * Mise � jour des donn�es du joueur
	 */
	public void update() {
		if(nextText != "") {
			new SpeechBubble(m_gp, nextText , (int) this.m_pos.x, (int) this.m_pos.y - 10);
			nextText = "";
		}
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
			//System.out.println(this.m_collider.m_shape.getOrigin().x);
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
		
		if (m_keyH.isPressed(70) && m_magie >= 10) { // FireBall f
			if (m_ralentisseur <= 0) {
				m_magie -= 10;
				m_spell = true;

				m_ralentisseur = 16;
				Fireball f = new Fireball(m_gp, m_keyH, (int) this.m_pos.x + 1, (int) this.m_pos.y);
				f.setDirection(m_direction[0], m_direction[1]);
				c = 0;
			} else {
				m_ralentisseur -= 1;
			}
		}
		interact_cooldown++;
		if (m_keyH.isPressed(69) && interact_cooldown >10) {
			interact_cooldown = 0;
			for(Entity e : m_gp.m_tab_Map[m_gp.dim].m_list_entity) {
				if(e instanceof Entity_interactive) {
					if(this.m_pos.distanceTo(e.m_pos) < 80) {
						addItem(((Entity_interactive) e).interaction());

					}
				}
			}
			
			if(m_inventaire.contains(2)) { //recherche d'eau pour remplir seau
				int x = (int)(m_pos.x/m_gp.TILE_SIZE);
				int y = (int)(m_pos.y/m_gp.TILE_SIZE);
				for(int i = 0 ;  i < 3 ; i++) {
					for(int j = 0 ; j < 3 ; j++) {
						if(m_gp.m_tab_Map[m_gp.dim].m_Map.getMapTile(x-1+i, y-1+j) == 2) {
							takeItem(2);
							addToInventory(3);
							nextText = "Seau remplis d'eau";
						}
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
			m_spell = false;
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
	
	/**
	 * 
	 * @param id
	 * @return l'id de l'item si trouver, sinon 0
	 * 
	 * cherche et enleve un item de l'inventaire
	 */
	public int takeItem(int id) {
		if(m_inventaire.contains(id)) {
			m_inventaire.remove(m_inventaire.indexOf(id));
			return id;
		}
		return 0;
	}
	
	public boolean addItem(List<Integer> li) {
		if(li == null) return false;
		for(int e : li) {
			addToInventory(e);
		}
		return true;
	}
	
	public boolean fullInventory() {
		return m_inventaire.size() == 10;
	}

	public void addToInventory(int i) {
		if (!fullInventory()) {
			m_inventaire.add(i);
		} else {
			nextText = "Poches pleines !";
			
		}
	}
	
	public void regen() {
		if(m_life < 100 || m_magie < 80) {
			m_life = 100;
			m_magie = 80;
			nextText = "Santee et Magie restoree <3";
		}
		else nextText = "Je ne suis pas fatiguee ;~;";
	}
	
}
