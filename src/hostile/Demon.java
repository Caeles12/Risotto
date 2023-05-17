package hostile;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Hostile;
import main.GamePanel;
import utils.Collider;
import utils.Rectangle;
import utils.Vector2D;

public class Demon extends Hostile{

	boolean detect;
	
	public Demon(GamePanel a_gp, int x, int y) {
		super(a_gp);
		this.m_pos = new Vector2D(x, y);
		this.m_collider = new Collider(new Rectangle(m_pos, new Vector2D(a_gp.TILE_SIZE/4, a_gp.TILE_SIZE/4),a_gp.TILE_SIZE/2, a_gp.TILE_SIZE/2), a_gp);
	}

	@Override
	protected void setDefaultValues() {
		m_life = 50;
		m_lifeTemp = m_life;
		m_damage = 25;
		m_speed = 3;
		m_timer = 0;
		ptr_list_image = 0;
		m_timerAnimation = 0;
		m_dir = new int[2];
		detect = false;
		
	}

	@Override
	public void getHostileImage() {
		//gestion des expections 
				try {
					for(int i = 1 ; i < 5 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/hostile/demon_"+i+".png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
		
	}

	protected void move() {
		
		if(m_gp.m_player.m_pos.distanceTo(this.m_pos) < 150) {
			detect = true;
			Vector2D direction = m_gp.m_player.m_pos.minus(this.m_pos);
			m_dir[0] = (int) direction.x;
			m_dir[1] = (int) direction.y;
		}
		else {
			detect = false;
			m_dir[0] = 0;
			m_dir[1] = 0;
		}
		
		super.move();
	}
	@Override
	protected void animationRate() {
		if(detect) {
			if(m_timerAnimation >= 15) {
				ptr_list_image++;
				m_timerAnimation = 0;
			}
			if(ptr_list_image > m_idleImage.size()-1 && m_life > 0) ptr_list_image =1;
		}
		else {
			ptr_list_image =0;
		}

		

		
	}

}
