package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import entity.Player;
import main.GamePanel;
import main.Renderer;

/**
 * 
 * Gestionnaire des tiles du jeu
 *
 */
public class TileManager {
	GamePanel m_gp;			//panel du jeu principal
	Tile[] m_tile;			//tableau de toutes les tiles possibles dans le jeu
	int m_maxTiles = 10;	//nombre maximum de tiles chargeable dans le jeu
	int m_mapTileNum[][];	//r�partition des tiles dans la carte du jeu
	
	int nbCol;
	int nbRow;
	
	/**
	 * Constructeur
	 * @param gp
	 */
	public TileManager(GamePanel gp) {
		this.m_gp =  gp;
		m_tile = new Tile[m_maxTiles];
		
		//m_mapTileNum = new int[m_gp.MAX_SCREEN_COL][m_gp.MAX_SCREE_ROW];
		
		this.getTileImage();
		this.loadMap("/maps/map3.txt");
	}
	
	/**
	 * Chargement de toutes les tuiles du jeu
	 */
	public void getTileImage() {
		try {
			m_tile[0] = new Tile();
			m_tile[0].m_image = ImageIO.read(getClass().getResource("/tiles/GRASS.png"));
			
			m_tile[1] = new Tile();
			m_tile[1].m_image = ImageIO.read(getClass().getResource("/tiles/BRICK2.png"));
			m_tile[1].m_collision = true;
			
			m_tile[2] = new Tile();
			m_tile[2].m_image = ImageIO.read(getClass().getResource("/tiles/WATER.png"));
			
			m_tile[3] = new Tile();
			m_tile[3].m_image = ImageIO.read(getClass().getResource("/tiles/LAVA.png"));
			
			m_tile[4] = new Tile();
			m_tile[4].m_image = ImageIO.read(getClass().getResource("/tiles/SAND.png"));
			
			m_tile[5] = new Tile();
			m_tile[5].m_image = ImageIO.read(getClass().getResource("/tiles/SNOW.png"));
			
			m_tile[6] = new Tile();
			m_tile[6].m_image = ImageIO.read(getClass().getResource("/tiles/FENCE.png"));
			m_tile[6].m_collision = true;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lecture du fichier txt contenant la map et chargement des tuiles correspondantes.
	 */
	public void loadMap(String filePath) {
		//charger le fichier txt de la map
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line = br.readLine();
			String size[] = line.split(" ");
			nbRow = Integer.parseInt(size[0]);
			nbCol = Integer.parseInt(size[1]);
			
			m_mapTileNum = new int[nbCol][nbRow];
			
			int col = 0;
			int row = 0;
			
			// Parcourir le fichier txt pour r�cup�rer les valeurs
			while (col < nbCol && row < nbRow) {
				line = br.readLine();
				while (col < nbCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					m_mapTileNum [col][row] = num;
					col++;
				}
				if (col == nbCol) {
					col = 0;
					row ++;
				}
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getMapTile(int x, int y) {
		return m_mapTileNum[x][y];
	}
	
	/**
	 * Affichage de la carte avec les diff�rentes tuiles
	 * @param g2
	 */
	public void draw(Renderer r) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < nbCol && row < nbRow) {
			int tileNum = m_mapTileNum[col][row];
			r.renderImage(m_tile[tileNum].m_image, x, y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
			col ++;
			x += m_gp.TILE_SIZE;
			if (col == nbCol) {
				col = 0;
				row ++;
				x = 0;
				y += m_gp.TILE_SIZE;
			}
		}
	}
	
	public int getNbCol(){
		return nbCol;
	}
	
	public int getNbRow() {
		return nbRow;
	}
	
	public int[][] getMapTileNum() {
		return m_mapTileNum;
	}
	
	public Tile[] getTile() {
		return m_tile;
	}
}
