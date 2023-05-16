package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Renderer;

/**
 * D�fintition du comportement d'un joueur
 *
 */
public class Player extends Entity{

	GamePanel m_gp;
	KeyHandler m_keyH;
	int[] m_inventaire;
	int m_life;
	int m_magie;
	int[] m_direction;
	boolean[] m_tape;
	boolean m_spell;
	int c;
	int m_ralentisseur;
	
	int interact_cooldown;
	
	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.m_inventaire = new int[10];
		this.m_direction = new int[2];
		this.m_tape = new boolean[4];
		this.setDefaultValues();
		this.getPlayerImageBase();
		
		this.interact_cooldown = 0;
	}
	
	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues() {
		m_x = 100;
		m_y = 100;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mise � jour des donn�es du joueur
	 */
	public void update() {
		if (m_keyH.isPressed(37) && m_tape[0] == false) { // GAUCHE
			m_direction[0] += -1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(38) && m_tape[1] == false) { // HAUT
			m_direction[0] += 0;
			m_direction[1] += -1;
		} 
		if (m_keyH.isPressed(39) && m_tape[2] == false) { // DROITE
			m_direction[0] += 1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(40) && m_tape[3] == false) { // BAS
			m_direction[0] += 0;
			m_direction[1] += 1;
		}
		
		if(m_direction[0] != 0 || m_direction[1] != 0) {
			float norme = (float) Math.sqrt(m_direction[0] * m_direction[0] + m_direction[1] * m_direction[1]);
			float vx = (m_speed * m_direction[0]) / norme;
			float vy = (m_speed * m_direction[1]) / norme;
			
			System.out.println(Math.sqrt(vx*vx + vy*vy));
			m_x += vx;
			m_y += vy;
		}
		
		m_direction[0] = 0;
		m_direction[1] = 0;
		
		if (m_keyH.isPressed(70)) { // FireBall f
			if (m_ralentisseur <= 0) {
				m_magie -= 10;
				m_spell = true;
				m_ralentisseur = 10;
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
					if(Math.sqrt(Math.pow(e.m_x-this.m_x,2)+Math.pow(e.m_y-this.m_y,2)) < 80) {
						addItem(((Entity_interactive) e).interaction());
					}
				}
			}
		}
		if (c > 10) {
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
		r.renderImage(l_image, (int) m_x, (int) m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);

	}
	
	public float getXCoordonates() {
		return m_x;
	}
	
	public float getYCoordonates() {
		return m_y;
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
		for(int i = 0 ; i < m_inventaire.length; i++){
			if(m_inventaire[i]==id) {
				m_inventaire[i]=0;
				return id;
			}
		}
		return 0;
	}
	
	public boolean addItem(List<Integer> li) {
		if(li == null) return false;
		for(int e : li) {
			for(int i = 0 ; i < m_inventaire.length ;i++) {
				if(m_inventaire[i] == 0) {
					m_inventaire[i] = e;
					break;
				}
			}
		}
		return true;
	}
	
}
