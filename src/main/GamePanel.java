package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;

import entity.Camera;
import entity.Player;
import hostile.Frog;
import entity.Entity;
import entity.Hostile;
import interactive.*;
import tile.Tile;
import tile.TileManager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Panel principal du jeu contenant la map principale
 *
 */
public class GamePanel extends JPanel implements Runnable{
	
	//Param�tres de l'�cran
	final int ORIGINAL_TILE_SIZE = 16; 							// une tuile de taille 16x16
	final int SCALE = 3; 										// �chelle utilis�e pour agrandir l'affichage
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; 	// 48x48
	public int MAX_SCREEN_COL = 16;
	public int MAX_SCREE_ROW = 16; 					 	// ces valeurs donnent une r�solution 4:3
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;	// 576 pixels

	// FPS : taux de rafraichissement
	int m_FPS;
	
	// Cr�ation des diff�rentes instances (Player, KeyHandler, TileManager, GameThread ...)
	KeyHandler m_keyH;
	Thread m_gameThread;
	Player m_player;
	public TileManager m_tileM;
	Camera m_camera;
	Renderer m_renderer;
	
	public class Map{
		public List<Entity> m_list_entity;
		public TileManager m_Map;
		public Map(GamePanel gp, String path) {
			m_list_entity = new ArrayList<Entity>();
			m_Map = new TileManager(gp,path);
		};
	}
	
	public Map[] m_tab_Map;
	public int dim;
	
	/**
	 * Constructeur
	 */
	@SuppressWarnings("unchecked")
	public GamePanel() {
		m_FPS = 60;				
		m_keyH = new KeyHandler();
		m_player = new Player(this, m_keyH);
		
		m_camera = new Camera(this, m_player.m_x, m_player.m_y, 0.5f, 0.1f);
		m_renderer = new Renderer(this, m_camera);
				
		m_tab_Map = new Map[2];
		setDim(0);
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);
	}
	
	public void setDim(int dim) {
		switch(dim) {
		case 0:
			this.dim = 0;
			if(m_tab_Map[dim] == null) init_demo_map(this);
			m_player.m_x = TILE_SIZE*6;
			m_player.m_y = TILE_SIZE*8;
			break;
		case 1:
			this.dim = 1;
			if(m_tab_Map[dim] == null) init_house(this);
			m_player.m_x = TILE_SIZE*5;
			m_player.m_y = TILE_SIZE*1;
			break;
		}
		
		m_tileM = m_tab_Map[dim].m_Map;
	}
	
	public void init_demo_map(GamePanel gp) {
		m_tab_Map[dim] = new Map(gp,"/maps/map3.txt");
		
		m_tab_Map[dim].m_list_entity.add(new Coffre(6, 1, gp, null));
		m_tab_Map[dim].m_list_entity.add(new Door(6, 7, gp, 1));
		m_tab_Map[dim].m_list_entity.add(new Frog(this, 200, 100));
	}
	
	public void init_house(GamePanel gp) {
		m_tab_Map[dim] = new Map(this,"/maps/house.txt");
		
		m_tab_Map[dim].m_list_entity.add(new Cauldron(3,1,gp,null));
		m_tab_Map[dim].m_list_entity.add(new Door(5,0,gp,0));
		
	}
	
	/**
	 * Lancement du thread principal
	 */
	public void startGameThread() {
		m_gameThread = new Thread(this);
		m_gameThread.start();
	}
	
	public void run() {
		
		double drawInterval = 1000000000/m_FPS; // rafraichissement chaque 0.0166666 secondes
		double nextDrawTime = System.nanoTime() + drawInterval; 
		
		while(m_gameThread != null) { //Tant que le thread du jeu est actif
			
			//Permet de mettre � jour les diff�rentes variables du jeu
			this.update();
			
			//Dessine sur l'�cran le personnage et la map avec les nouvelles informations. la m�thode "paintComponent" doit obligatoirement �tre appel�e avec "repaint()"
			this.repaint();
			
			//Calcule le temps de pause du thread
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Mise � jour des donn�es des entit�s
	 */
	public void update() {
		testCollision();
		m_player.update();
		m_camera.move(m_player.m_x, m_player.m_y);
		m_camera.zoom(1);
		int i = 0;
		while(i<m_tab_Map[dim].m_list_entity.size()) {
			m_tab_Map[dim].m_list_entity.get(i).update();
			if(m_tab_Map[dim].m_list_entity.get(i).m_status == Entity.Status.DESTROY) {
				m_tab_Map[dim].m_list_entity.remove(i);
			}else {
				i++;
			}
		}
	}
	
	/**
	 * Affichage des �l�ments
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		m_renderer.setGraphics(g2);
		
		m_tileM.draw(m_renderer);
		
		Iterator<Entity> iter = m_tab_Map[dim].m_list_entity.iterator();
		while(iter.hasNext()) iter.next().draw(m_renderer);
		
		m_player.draw(m_renderer);
		
		
		g2.dispose();
		m_renderer.update();
	}
	
	public void testCollision() {
		int x =(int) m_player.getXCoordonates() / TILE_SIZE;
		int y =(int) m_player.getYCoordonates() / TILE_SIZE;
		int[][] mapTileNum = m_tileM.getMapTileNum();
		Tile[] tiles = m_tileM.getTile();
		
		if (tiles[mapTileNum[x][y]].m_collision) {
			m_player.setTape(0, true);
		} else {
			m_player.setTape(0, false);
		}
		if (tiles[mapTileNum[x][y]].m_collision) {
			m_player.setTape(1, true);
		} else {
			m_player.setTape(1, false);
		}
		if (tiles[mapTileNum[x+1][y]].m_collision) {
			m_player.setTape(2, true);
		} else {
			m_player.setTape(2, false);
		}
		if (tiles[mapTileNum[x][y+1]].m_collision) {
			m_player.setTape(3, true);
		} else {
			m_player.setTape(3, false);
		}
	}
	
}
