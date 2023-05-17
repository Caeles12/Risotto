package interactive;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class House extends Entity_interactive{
	
	public House(int x, int y, GamePanel m_gp, int part_nb) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		

		this.getHouseImage();
		
		this.animate = false;
	}
	
	public List<Integer> interaction() {
		return null;
	}
	
	public void getHouseImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/object/House_1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {}
	
	@Override
	public void draw(Renderer a_g2) {
		BufferedImage l_image = m_idleImage.get(ptr_list_image);
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.renderImage(l_image, (int) this.m_pos.x,(int) this.m_pos.y, 4*m_gp.TILE_SIZE, 6*m_gp.TILE_SIZE);
	}
}

