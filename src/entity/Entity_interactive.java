package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Renderer;
import utils.Vector2D;

public abstract class Entity_interactive extends Entity{
	protected List<Integer> objet_interne = new ArrayList<>();
	protected boolean animate = true;
	protected int tmpAnim = 0;
	protected int ptr_list_image = 0;
	public abstract List<Integer> interaction();
	
	//convertie la position d'une tiles en position r�elle
	public void setPositionTiles(int x, int y) {
		this.m_pos = new Vector2D(0,0);
		this.m_pos.x = x*m_gp.TILE_SIZE;
		this.m_pos.y = y*m_gp.TILE_SIZE;
	}
	
	public void draw(Renderer a_g2) {
		this.tmpAnim++;
		if(animate && tmpAnim >10) {
			tmpAnim = 0;
			ptr_list_image++;
			if(ptr_list_image > m_idleImage.size()-1) ptr_list_image = 0;
		}
		BufferedImage l_image = m_idleImage.get(ptr_list_image);
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.renderImage(l_image, (int) this.m_pos.x,(int) this.m_pos.y, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
	}
}
