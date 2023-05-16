package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderer;

public class Fireball extends Entity {
	
	GamePanel m_gp;
	int m_dureevie;
	
	public Fireball(GamePanel a_gp, int x, int y) {
		this.m_gp = a_gp;
		this.m_x = x;
		this.m_y = y;
		this.m_dureevie = 300;
		this.m_speed = 10;
		m_gp.m_list_entity[m_gp.dim].add(this);
		this.getFireBallImage();
	}

	@Override
	public void draw(Renderer r) {
		BufferedImage l_image = m_idleImage.get(0);
		r.renderImage(l_image, (int) m_x + 40, (int) m_y + 25, 15, 15);
	}

	@Override
	public void update() {
		m_x += 1 * m_speed;
		m_dureevie -= 1;
		if (m_dureevie <= 0) {
			m_status = Status.DESTROY;
		}
	}
	
	public void getFireBallImage() {
		//gestion des expections 
		try {
			m_idleImage.add(ImageIO.read(getClass().getResource("/player/fireball.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
