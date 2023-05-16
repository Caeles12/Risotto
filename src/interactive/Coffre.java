package interactive;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;

public class Coffre extends Entity_interactive{
	
	public Coffre(int x, int y, GamePanel m_gp, List<Integer> inventaire) {
		this.m_gp = m_gp;
		this.m_x = x;
		this.m_y = y;
		this.objet_interne = inventaire;
		this.getCoffreImage();
	}
	
	public List<Integer> interaction() {
		return this.objet_interne;
	}
	
	public void getCoffreImage() {
		//gestion des expections 
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/Player/superhero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Renderer a_g2) {
		// récupère l'image du joueur
		BufferedImage l_image = m_idleImage;
		// affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
		a_g2.renderImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
	}
}
