package hostile;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Hostile;
import entity.SpeechBubble;

import main.GamePanel;
import main.Renderer;

import utils.Vector2D;

import tile.Tile;

public class Frog extends Hostile {

	
	public Frog(GamePanel a_gp,int x,int y){
		super( a_gp);
		this.m_pos = new Vector2D(x, y);
	}

	@Override
	protected void setDefaultValues() {
		m_life = 200;
		m_lifeTemp = m_life;
		m_damage = 10;
		m_speed = 8;
		m_timer = 0;
		ptr_list_image = 0;
		m_timerAnimation = 0;
		m_dir = new int[2];
		
	}

	@Override
	public void getHostileImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 8 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/hostile/grenouille_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void move() {
		
		
		if (m_timer > 120) {
			
			super.move();
			
		}
		
		if (m_timer > 150) {
			new SpeechBubble(m_gp, "Croa",(int) this.m_pos.x,(int) this.m_pos.y, 4, 15, 2);
			m_dir[0] = r.nextInt(3)-1;
			m_dir[1] = r.nextInt(3)-1;
			m_timer = 0;
		}
	}
	
	protected void animationRate() {
		if(m_timerAnimation >= 15) {
			ptr_list_image++;
			m_timerAnimation = 0;
		}
	}

}
