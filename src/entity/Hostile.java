package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

abstract public class Hostile extends Entity{

	GamePanel m_gp;
	protected int m_damage;
	protected int m_life;
	protected int m_timer;
	protected Random r = new Random();
	
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
	public void draw(Graphics2D a_g2) {
		// r�cup�re l'image du joueur
		BufferedImage l_image = m_idleImage;
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
	}
	
}
