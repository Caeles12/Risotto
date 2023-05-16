package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderer;

abstract public class Hostile extends Entity{

	protected GamePanel m_gp;
	protected int m_damage;
	protected int m_life;
	protected int m_timer;
	protected int m_timerAnimation;
	protected Random r = new Random();
	protected int ptr_list_image = 0;
	
	/**
	 * Constructeur de Hostile
	 * @param a_gp GamePanel, pannel principal du jeu 
	 */
	public Hostile(GamePanel a_gp) {
		this.m_gp = a_gp;
		this.setDefaultValues();
		this.getHostileImage();
	}
	
	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	abstract protected void setDefaultValues();
	
	/**
	 * R�cup�ration de l'image du monstre
	 */
	abstract public void getHostileImage();
	
	/**
	 * Mise � jour des donn�es du monstre
	 */
	public void update() {
		m_timer ++;
		m_timerAnimation ++;
		move();
		
	}
	
	/**
	 * Movement du monstre
	 */
	protected abstract void move();
	
	/**
	 * Affichage du l'image du monstre dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	
	protected void lifeBar() {
		
	}
	
	public void draw(Renderer a_g2) {
		// r�cup�re l'image du joueur
		if(ptr_list_image >= m_idleImage.size()) ptr_list_image =0;
		
		BufferedImage l_image = m_idleImage.get(ptr_list_image);
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.renderImage(l_image, (int) m_x, (int) m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
	}
	
}
