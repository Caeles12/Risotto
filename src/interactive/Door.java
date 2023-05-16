package interactive;

import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;
import tile.TileManager;

public class Door extends Entity_interactive{
	
	public Door(int x, int y, GamePanel m_gp, List<Integer> inventaire) {
		this.m_gp = m_gp;
		setPositionTiles(x, y);
		this.objet_interne = inventaire;
		this.getDoorImage();
		
		this.animate = false;
	}
	
	public List<Integer> interaction() {
		m_gp.Map_actuelle = 1;
		m_gp.init_house(m_gp);
		return null;
	}
	
	public void getDoorImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/object/DOOR.png")));
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
