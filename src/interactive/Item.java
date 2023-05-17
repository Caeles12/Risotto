package interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Entity_interactive;
import entity.Object;
import entity.SpeechBubble;
import main.GamePanel;
import main.Renderer;
import utils.Collider;
import utils.Rectangle;
import utils.Vector2D;

public class Item extends Entity_interactive{
	String talk = "";
	boolean looted = false;
	int m_item;
	
	public Item(int x, int y, GamePanel m_gp, int item) {
		this.m_item = item;
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		this.objet_interne = new ArrayList<>();
		objet_interne.add(item);
		this.getItemImage();
		
		this.m_collider = new Collider(new Rectangle(m_pos, m_gp.TILE_SIZE, m_gp.TILE_SIZE), m_gp);
		m_gp.m_tab_Map[m_gp.dim].m_list_entity.add(this);
		
		this.animate = false;
		talk = "+1 "+Object.getNom(item)+"!";
	}
	
	public List<Integer> interaction() {
		looted = true;
		return this.objet_interne;
	}
	
	public void getItemImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/items/"+Object.getImage(m_item)+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		if(looted) {
			new SpeechBubble(m_gp, talk, (int) this.m_pos.x+2, (int) this.m_pos.y);
			this.m_status = Entity.Status.DESTROY;
			talk="";
		}
	}
	
	@Override
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
		if(ptr_list_image == m_idleImage.size()-1) animate = false;
	}
}
