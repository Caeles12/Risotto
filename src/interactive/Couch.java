package interactive;

import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class Couch extends Entity_interactive{
		
	public Couch(int x, int y, GamePanel m_gp, int part_nb) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		
		this.ptr_list_image = part_nb-1;

		this.getCouchImage();
		
		this.animate = false;
	}
	
	public List<Integer> interaction() {
		if(ptr_list_image == 0) {
			m_gp.m_player.regen();
		}
		return null;
	}
	
	public void getCouchImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 3 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/object/COUCH_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {}
	
	@Override
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
		if(ptr_list_image == m_idleImage.size()-1) animate = false;
	}
}

