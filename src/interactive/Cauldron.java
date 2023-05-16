package interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entity.Entity_interactive;
import entity.SpeechBubble;
import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class Cauldron extends Entity_interactive{
	List<String> nextText;
	int talkDelay = 0;
	
	public Cauldron(int x, int y, GamePanel m_gp, List<Integer> inventaire) {
		this.m_gp = m_gp;
		this.m_pos = new Vector2D(x, y);
		setPositionTiles(x, y);
		this.objet_interne = inventaire;
		this.getCauldronImage();
		
		nextText = new ArrayList<>();
		
	}
	
	public Cauldron(int x, int y, GamePanel m_gp) {
		this.m_gp = m_gp;
		setPositionTiles(x, y);
		this.objet_interne = new ArrayList<>();
		this.getCauldronImage();
		
		nextText = new ArrayList<>();
	}
	
	
	
	public List<Integer> interaction() {
		List<Integer> l = new ArrayList<>();
		if(tryRecipe(1)) {
			l.add(5);
			return l ;
		}
		else {
			if(!objet_interne.contains(1)) nextText.add("besoin : Riz");
			if(!objet_interne.contains(3)) nextText.add("besoin : Eau");
			if(!objet_interne.contains(4)) nextText.add("besoin : Champignon");
		}
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
	public void update() {
		talkDelay++;
		if(!nextText.isEmpty() && talkDelay > 25) {
			talkDelay = 0;
			new SpeechBubble(m_gp, nextText.remove(0), (int) this.m_pos.x+2, (int) this.m_pos.y);
		}
	}
	
	@Override
	public void draw(Renderer a_g2) {
		super.draw(a_g2);
	}
	/**
	 * 
	 * @param recipe
	 * @return si la recette a fonctionner
	 * 
	 * 1- risoto au champignon
	 */
	boolean tryRecipe(int recipe) {
		switch(recipe) {
		case 1: //need [1,3,4]
			int[] tab = {1,3,4};
			for(int i : tab) {
				int tmp = m_gp.m_player.takeItem(i);
				if(tmp != 0) objet_interne.add(tmp);
			}
			return objet_interne.contains(1) && objet_interne.contains(3) && objet_interne.contains(4);
		default:
			return false;
		}
	}
}