package interactive;

import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class Cauldron extends Entity_interactive{
	
	public Cauldron(int x, int y, GamePanel m_gp, List<Integer> inventaire) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		this.objet_interne = inventaire;
		this.getCauldronImage();
		
	}
	
	public List<Integer> interaction() {
		
		return null;
	}
	
	public void getCauldronImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 6 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/object/Cauldron_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {}
	
	@Override
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
	}
}