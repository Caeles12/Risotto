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
	boolean looted;
	
	public Coffre(int x, int y, GamePanel m_gp, List<Integer> inventaire) {
		this.m_gp = m_gp;
		setPositionTiles(x, y);
		this.objet_interne = inventaire;
		this.getCoffreImage();
		
		this.animate = false;
		looted = false;
	}
	
	public List<Integer> interaction() {
		if(!looted) {
			looted = true;
			animate = true;
			tmpAnim = 0;
			return this.objet_interne;
		}
		if(looted) ptr_list_image = 0;
		return null;
	}
	
	public void getCoffreImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 4 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/object/chest_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
		if(ptr_list_image == m_idleImage.size()-1) animate = false;
	}
}
