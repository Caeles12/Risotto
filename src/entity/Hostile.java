package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderer;
import interactive.Item;

abstract public class Hostile extends Entity{

	protected int m_damage;
	protected int m_life;  //enlever des pv a cette variable si des degats sont pris
	protected int m_lifeTemp; // sert pour la barre de vie
	protected int m_timer;
	protected int m_timerInvincible;
	protected int m_timerAnimation;
	protected Random r = new Random();
	protected int ptr_list_image = 0;
	protected int[] m_dir;
	protected List<BufferedImage> m_lifeBar;
	
	/**
	 * Constructeur de Hostile
	 * @param a_gp GamePanel, pannel principal du jeu 
	 */
	public Hostile(GamePanel a_gp) {
		this.m_gp = a_gp;
		this.setDefaultValues();
		this.getHostileImage();
		this.m_lifeBar = new ArrayList<BufferedImage>();
		this.setLifeBar();
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
		if(m_life>=0) {
			m_timer ++;
			m_timerInvincible ++;
			move();
			animationRate();
			updateLifeBar();
			attack();
		}
		m_timerAnimation ++;

		
	}
	
	public void attack() {
		if(m_timerInvincible > 40) {
			if(m_collider.colliding(m_gp.m_player.m_collider)) {
				m_gp.m_player.take_damage();
				m_timerInvincible = 0;
			}
		}

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
		if(checkCollisions()) {
			this.m_pos.x -= vx*m_speed;
		}
		this.m_pos.y += vy*m_speed;
		if(checkCollisions()) {
			this.m_pos.y -= vy*m_speed;
		}
	}
	
	//sert à l'init, n'update pas (peut servir à l'init si on vide la liste a chaque fois)
	void setLifeBar() {
		int nbDemiCoeur = m_life/25; //choper les degat du joueur et les mettres a la place du 25 !!	
		try {
				
			for(int i=0; i<nbDemiCoeur/2; i++) {
				m_lifeBar.add(ImageIO.read(getClass().getResource("/hostile/coeurPlein.png")));
			}
				
			if(nbDemiCoeur%2 != 0) {
				m_lifeBar.add(ImageIO.read(getClass().getResource("/hostile/demiCoeur.png")));
			}
			
		}catch (IOException e) { e.printStackTrace(); }
		
		
	}
	
	void updateLifeBar() {
		int damagetake = m_lifeTemp-m_life;
		
		if(damagetake >= 25 && m_life >=0) {
			try {
				if(m_life/50 <  m_lifeBar.size() && m_life/50.0 == (int) m_life/50) {
					m_lifeBar.remove(m_lifeBar.size()-1);
				}
				else {
					m_lifeBar.set(m_lifeBar.size()-1, ImageIO.read(getClass().getResource("/hostile/demiCoeur.png")));
				}
				
				damagetake -= 25;
				m_lifeTemp -= 25;
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * Affichage du l'image du monstre dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	
	public void draw(Renderer a_g2) {
		// r�cup�re l'image du joueur

		
		BufferedImage l_image = m_idleImage.get(ptr_list_image);
		// affiche le monstre avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		int imgDir = (this.m_dir[0]<0 ? -1 : 1);
		int imgOffset = (this.m_dir[0]<0 ? m_gp.TILE_SIZE : 0);
		a_g2.renderImage(l_image, (int) this.m_pos.x+imgOffset, (int) this.m_pos.y, m_gp.TILE_SIZE*imgDir, m_gp.TILE_SIZE);
		
		//affiche la barre de vie du monstre
		int nbCoeur = m_lifeBar.size()-1;
		int tailleCoeur = 24; //la taille d'un coeur en pixel
		
		for (int i=0; i<=nbCoeur; i++) {
			a_g2.renderImage(m_lifeBar.get(i), (int) (this.m_pos.x+i*tailleCoeur-nbCoeur*tailleCoeur/2), (int) this.m_pos.y-30, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
		}
	}
	
	public void takeDamage(int damage) {
		m_life -= damage;
		if(m_life <=0) {
			new Item((int) m_pos.x,(int) m_pos.y, m_gp, 6);
			new SpeechBubble(m_gp, "Couic",(int) this.m_pos.x,(int) this.m_pos.y, 5, 10, 1);
			m_status = Status.DESTROY;
			m_life = 0;
		}
	}
	
}
