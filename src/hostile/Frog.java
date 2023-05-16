package hostile;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Hostile;
import entity.SpeechBubble;
import main.GamePanel;
import main.Renderer;

public class Frog extends Hostile {

	int m_dir =0;
	
	public Frog(GamePanel a_gp,int x,int y){
		super( a_gp);
		this.m_x = x;
		this.m_y = y;
	}

	@Override
	protected void setDefaultValues() {
		m_life = 100;
		m_damage = 10;
		m_speed = 4;
		
	}

	@Override
	public void getHostileImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 2 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/hostile/grenouille_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void move() {
		
		if (m_timer > 120) {
			
			switch(m_dir) {
			case 1 : 
				m_x += m_speed;
				break;
			case 2 :
				m_x -= m_speed;
				break;
			case 3 :
				m_y += m_speed;
				break;
			case 4 :
				m_y -= m_speed;
				break;
			default :
				break;
			}
		}
		
		if (m_timer > 150) {
			new SpeechBubble(m_gp, "Croa", m_x, m_y);
			m_dir = r.nextInt(5);
			m_timer = 0;
		}

		//System.out.println(distWall());
	}
	
	float distWall() {
		float dist =0;
			dist = m_gp.m_tileM.getMapTile(m_x/m_gp.TILE_SIZE, m_y/m_gp.TILE_SIZE);
			
		return dist;
	}


}
