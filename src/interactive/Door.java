package interactive;

import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class Door extends Entity_interactive{
	
	int m_dim;
	
	public Door(int x, int y, GamePanel m_gp, int dim) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		
		this.m_dim = dim;

		this.getDoorImage();
		
		this.animate = false;
	}
	
	public List<Integer> interaction() {
		m_gp.setDim(m_dim);
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
