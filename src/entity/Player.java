package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;
import java.util.Iterator;

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
	int m_ralentisseur;
	int[] m_direction;
	
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
		this.setDefaultValues();
		this.getPlayerImage();
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
		m_ralentisseur = 0;
		m_direction[0] = 0;
		m_direction[1] = 0;
	}
	
	/**
	 * R�cup�ration de l'image du personnage
	 */
	public void getPlayerImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/superhero.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mise � jour des donn�es du joueur
	 */
	public void update() {
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
		double norme = Math.sqrt(m_direction[0] * m_direction[0] + m_direction[1] * m_direction[1]);
		System.out.println(norme);
		m_x += (int) (m_speed * m_direction[0] / norme);
		m_y += (int) (m_speed * m_direction[1] / norme);
		m_direction[0] = 0;
		m_direction[1] = 0;
		if (m_keyH.isPressed(70)) { // FireBall f
			if (m_ralentisseur <= 0) {
			m_magie -= 10;
			System.out.println("MAGIE");
			m_ralentisseur = 10;
			} else {
				m_ralentisseur -= 1;
			}
		}
		
		if(m_keyH.isPressed(69)) {
			Iterator<Entity> iter = m_gp.m_list_entity.iterator();
			while(iter.hasNext()) {
				Entity tmp = iter.next();
				if(tmp instanceof Entity_interactive) {
					if(Math.sqrt(Math.pow(tmp.m_x-this.m_x,2)+Math.pow(tmp.m_y-this.m_y,2)) < 80) {
						((Entity_interactive) tmp).interaction();
					}
				}
				
			}
		}
	}
	
	/**get_lastPressed
	 * Affichage du l'image du joueur dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Renderer r) {

		// r�cup�re l'image du joueur
		BufferedImage l_image = m_idleImage.get(0);
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		r.renderImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);

	}
	
	
}
