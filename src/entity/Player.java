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

import hostile.Demon;
import main.GamePanel;
import main.GamePanel.Map;
import main.KeyHandler;
import main.Renderer;
import main.UI;
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
	
	/*
	 * 0 - start menu
	 * 1 - win menu
	 * 2 - loose menu
	 */
	int menuNb = 0; 

	KeyHandler m_keyH;	public void getPlayerImageBalais() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 5 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/player/witch_"+i+".png")));
			m_spellImg = ImageIO.read(getClass().getResource("/player/special/spellwitch.png"));
			
			
			fullH = ImageIO.read(getClass().getResource("/hostile/coeurPlein.png"));
			halfH = ImageIO.read(getClass().getResource("/hostile/demiCoeur.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	ArrayList<Integer> m_inventaire;
	BufferedImage m_spellImg;
	List<BufferedImage> m_balaisImage = new ArrayList<>();
	int m_life;
	int m_life_cap;
	int m_magie;
	int m_magie_cap;
	int[] m_direction;
	int[] m_lastdir;
	float[] m_collision;
	boolean[] m_tape;
	boolean m_spell;

	public boolean m_can_cast;
	boolean m_has_broom;

	int c;
	int m_ralentisseur;
	
	int interact_cooldown;
	
	protected int tmpAnim = 0;
	protected int ptr_list_image = 0;
	protected boolean animate = true;
	
	List<String> nextText;
	int talkdelay = 0;
	
	protected List<BufferedImage> m_lifeBar;
	BufferedImage fullH;
	BufferedImage halfH;
	
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
		this.m_lastdir = new int[2];
		this.m_tape = new boolean[4];
		this.m_collision = new float[2];
		this.m_pos = new Vector2D(100, 100);
		this.m_collider = new Collider(new Rectangle(m_pos, new Vector2D(a_gp.TILE_SIZE/4, a_gp.TILE_SIZE/4),a_gp.TILE_SIZE/2, a_gp.TILE_SIZE/2), a_gp);
		
		this.setDefaultValues();
		this.getPlayerImageBase();
		
		this.interact_cooldown = 0;
		nextText = new ArrayList<>();
		
		this.m_lifeBar = new ArrayList<BufferedImage>();
		this.setLifeBar();
	}
	
	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues() {
		m_speed = 4;
		m_life_cap = 10;
		m_life = m_life_cap;
		m_magie_cap = 80;
		m_magie = m_magie_cap;
		for (int i = 0; i < 4; i++) {
			m_tape[i] = false;
		}
		m_spell = false;
		m_has_broom = false;
	}
	
	/**
	 * R�cup�ration de l'image du personnage
	 */
	public void getPlayerImageBase() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 5 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/player/witch_"+i+".png")));
			m_spellImg = ImageIO.read(getClass().getResource("/player/special/spellwitch.png"));
			for(int i = 1 ; i < 5 ; i++) m_balaisImage.add(ImageIO.read(getClass().getResource("/player/special/balais/potitbalais_"+i+".png")));
			
			fullH = ImageIO.read(getClass().getResource("/hostile/coeurPlein.png"));
			halfH = ImageIO.read(getClass().getResource("/hostile/demiCoeur.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void LanceFireball(float x, float y) {
		m_ralentisseur -= 1;
		if(!m_can_cast) {
			if(m_ralentisseur <= 0) {
				nextText.add("Sans nourriture pas de magie ;-;");
				m_ralentisseur = 30;
			}
			return;
		}
		if (m_ralentisseur <= 0) {
			m_magie -= 1;
			m_spell = true;
			m_ralentisseur = 16;
			Fireball f = new Fireball(m_gp, this, m_keyH, (int) this.m_pos.x + m_gp.TILE_SIZE/2, (int) this.m_pos.y + m_gp.TILE_SIZE/2);
			f.setDirection(x, y);
			c = 0;
		}
	}
	
	/**
	 * Mise � jour des donn�es du joueur
	 */
	public void update() {
		if(m_life<=0 && !m_gp.menu) { //defaite
			m_gp.menu = true;
			menuNb = 2;
			m_gp.m_tab_Map = new Map[3];
			m_gp.setDim(2);
			regen();
			m_inventaire = new ArrayList<>();
		}
		int count = 0; //victoire
		for(int e : m_inventaire) {
			
			if(e == 6 ) count++;
		}
		if(count > 3 && !m_gp.menu) {
			m_gp.menu = true;
			menuNb = 1;
			m_gp.m_tab_Map = new Map[3];
			m_gp.setDim(2);
			regen();
			m_inventaire = new ArrayList<>();
		}
		talkdelay++;
		if(!nextText.isEmpty() && talkdelay >15) {
			new SpeechBubble(m_gp, nextText.remove(0) , (int) this.m_pos.x, (int) this.m_pos.y - 10);
			talkdelay = 0;
		}
		this.m_collider.m_shape.setOrigin(m_pos);
		

		boolean keyPressed = false;
		m_direction[0] = 0;
		m_direction[1] = 0;
		
		if (m_keyH.isPressed(37)) { // GAUCHE
			m_direction[0] += -1;
			m_direction[1] += 0;
			keyPressed = true;
		}
		if (m_keyH.isPressed(38)) { // HAUT
			m_direction[0] += 0;
			m_direction[1] += -1;
			keyPressed = true;
		} 
		if (m_keyH.isPressed(39)) { // DROITE
			m_direction[0] += 1;
			m_direction[1] += 0;
			keyPressed = true;
		}
		if (m_keyH.isPressed(40)) { // BAS
			m_direction[0] += 0;
			m_direction[1] += 1;
			keyPressed = true;
		}
		
		if(keyPressed) {
			m_lastdir[0] = m_direction[0];
			m_lastdir[1] = m_direction[1];
		}
		
		
		
		if((m_direction[0] != 0 || m_direction[1] != 0)) {
			float norme = (float) Math.sqrt(m_direction[0] * m_direction[0] + m_direction[1] * m_direction[1]);
			
			float vx = (m_direction[0] / norme);
			float vy = (m_direction[1] / norme);
			
			this.m_pos.x += vx*m_speed;
			//System.out.println(this.m_collider.m_shape.getOrigin().x);
			if(checkCollisions()) {
				this.m_pos.x -= vx*m_speed;
			}
			this.m_pos.y += vy*m_speed;
			if(checkCollisions()) {
				this.m_pos.y -= vy*m_speed;
			}
		}
		
	
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
			for(Entity e : m_gp.m_tab_Map[m_gp.dim].m_list_entity) {
				if(e instanceof Entity_interactive) {
					if(this.m_pos.distanceTo(e.m_pos) < 50) {
						addItem(((Entity_interactive) e).interaction());

					}
				}
			}
			
			if(m_inventaire.contains(2)) { //recherche d'eau pour remplir seau
				int x = (int)(m_pos.x/m_gp.TILE_SIZE)+1;
				int y = (int)(m_pos.y/m_gp.TILE_SIZE)+1;
				for(int i = 0 ;  i < 3 ; i++) {
					for(int j = 0 ; j < 3 ; j++) {
						int ni = x-1+i;
						int nj = y-1+j;
						if(ni>-1 && ni<m_gp.m_tab_Map[m_gp.dim].m_Map.getNbCol() && nj>-1 && nj<m_gp.m_tab_Map[m_gp.dim].m_Map.getNbRow()) {
							if(m_gp.m_tab_Map[m_gp.dim].m_Map.getMapTile(ni, nj) == 2) {
								takeItem(2);
								addToInventory(3);
								nextText.add("Seau remplis d'eau");
								break;								
							}							
						}
						if(m_inventaire.contains(3)) break;
					}
					if(m_inventaire.contains(3)) break;
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
			l_image = m_spellImg;
		}
		else {
			List<BufferedImage> imgs = m_idleImage;
			if(m_has_broom) {
				imgs = m_balaisImage;
			}
			for (int i = 0; i < 4; i++) {
				m_spell = false;
			}
			this.tmpAnim++;
			if(animate && tmpAnim >10) {
				tmpAnim = 0;
				ptr_list_image++;
				if(ptr_list_image > imgs.size()-1) ptr_list_image = 0;
			}
			l_image = imgs.get(ptr_list_image);
		}
		
		
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		
		int imgDir = (this.m_lastdir[0]<0 ? -1 : 1);
		int imgOffset = (this.m_lastdir[0]<0 ? m_gp.TILE_SIZE : 0);
		r.renderImage(l_image, (int) this.m_pos.x + imgOffset, (int) this.m_pos.y, m_gp.TILE_SIZE*imgDir, m_gp.TILE_SIZE);
		if(!m_gp.menu) {
			//affiche la barre de vie du monstre
			int nbCoeur = m_lifeBar.size()-1;
			int tailleCoeur = 24; //la taille d'un coeur en pixel
			for (int i=0; i<=nbCoeur; i++) {
				r.renderUIImage(m_lifeBar.get(i), (int) (i*tailleCoeur), 10, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
			}
			
			if(m_can_cast) {
				r.renderUIRect(20, 50, 78, 18, new Color(200,200,200));
				r.renderUIRect(24, 54, m_magie*70/m_magie_cap, 10, new Color(0,0,200));
			}
			UI.text(m_gp, "inventaire :", 20, 90, 15);
			for(int i = 0 ; i < m_inventaire.size() ; i++) {
				UI.text(m_gp, Object.getNom(m_inventaire.get(i)), 30, 110+i*20, 15);
			}
		}
		else {
			UI.text(m_gp, "Chaudron & Champignons", 90, 150, 48,4,12,5,Color.black,Color.magenta);
			if(menuNb == 0) {
				UI.text(m_gp, "z q s d -> Magie", 300, 240,20);
				UI.text(m_gp, "fleches directionnelles -> deplacement", 180, 260,20);
				UI.text(m_gp, "e -> interagir", 300, 280,20);
			}
			if(menuNb == 1) {
				UI.text(m_gp, "VICTOIRE", 300, 260, 35,8,12,5,Color.black,Color.yellow);
				UI.text(m_gp, "Vous etes incroyable :D", 280, 320,20);
			}
			if(menuNb == 2) {
				UI.text(m_gp, "GAME OVER", 300, 260, 35,8,12,5,Color.black,Color.red);
				UI.text(m_gp, "Vous pouvez y retourner, bon courage", 180, 320,20);
			}
		}
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
			if(e==5) {
				m_can_cast = true;
			}else if(e==7) {
				m_has_broom = true;
			}
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
			nextText.add("Poches pleines !");
			
		}
	}
	
	public void regen() {
		if(m_life < m_life_cap || m_magie < m_magie_cap) {
			m_life = m_life_cap;
			m_magie = m_magie_cap;
			nextText.add("Santee et Magie restoree <3");
			m_lifeBar.clear();
			setLifeBar();
		}
		else nextText.add("Je ne suis pas fatiguee ;~;");
	}
	
	//sert à l'init, n'update pas (peut servir à l'init si on vide la liste a chaque fois)
		void setLifeBar() {
			int nbDemiCoeur = m_life; //choper les degat du joueur et les mettres a la place du 25 !!					
			for(int i=0; i<nbDemiCoeur/2; i++) {
				m_lifeBar.add(fullH);
			}	
			if(nbDemiCoeur%2 != 0) {
				m_lifeBar.add(halfH);
			}
		}

		void take_damage() {
			if(m_life >0) {
				m_life--;
				nextText.add("Aie >~<");
				if(m_life < 0) m_life = m_life_cap;
				if(m_lifeBar.get(m_lifeBar.size()-1) == fullH) m_lifeBar.set(m_lifeBar.size()-1, halfH);
				else m_lifeBar.remove(m_lifeBar.size()-1);
			}
			
		}
	
}
