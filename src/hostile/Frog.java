package hostile;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Hostile;
import main.GamePanel;
import main.Renderer;

public class Frog extends Hostile {

	double m_dir =2*3.14;
	
	public Frog(GamePanel a_gp){
		super( a_gp);
	}

	@Override
	protected void setDefaultValues() {
		m_x = 200;
		m_y = 100;
		m_life = 100;
		m_damage = 10;
		m_speed = 2;
		
	}

	@Override
	public void getHostileImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/superhero.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void move() {
		
		if (m_timer > 120) {
			m_x += m_speed;
		}
		
		if (m_timer > 180) {
			m_dir = r.nextFloat()*2*3.14;
			m_timer = 0;
		}

		
	}

	@Override
	public void draw(Renderer a_g2) {
		// TODO Auto-generated method stub
		
	}
}
