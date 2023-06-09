package interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import entity.SpeechBubble;
import main.GamePanel;
import main.Renderer;
import utils.Collider;
import utils.Rectangle;
import utils.Vector2D;

import entity.Object;

public class Coffre extends Entity_interactive{
	boolean looted;
	String talk = "";
	
	
	public Coffre(int x, int y, GamePanel m_gp, int inventaire) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		this.objet_interne = new ArrayList<>();
		objet_interne.add(inventaire);
		this.getCoffreImage();
		
		this.m_collider = new Collider(new Rectangle(m_pos, m_gp.TILE_SIZE, m_gp.TILE_SIZE), m_gp);
		this.solid = true;
		
		this.animate = false;
		looted = false;
		talk = "Trouve : "+Object.getNom(inventaire);
	}
	
	public Coffre(int x, int y, GamePanel m_gp) {
		this.m_gp = m_gp;
		setPositionTiles(x, y);
		this.objet_interne = new ArrayList<>();
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
		if(looted) {
			ptr_list_image = 0;
			talk = "Ce coffre est vide";
		}
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
	
	@Override
	public void update() {
		if(looted && talk != "") {
			new SpeechBubble(m_gp, talk, (int) this.m_pos.x+2, (int) this.m_pos.y);
			talk="";
		}
	}
	
	@Override
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
		if(ptr_list_image == m_idleImage.size()-1) animate = false;
	}
}
