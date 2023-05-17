package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Renderer;
import utils.Vector2D;

public class Fireball extends Entity {
	
	KeyHandler m_keyH;
	int m_dureevie;
	int[] m_direction;
	
	public Fireball(GamePanel a_gp, KeyHandler keyH, int x, int y) {
		this.m_gp = a_gp;
		this.m_pos = new Vector2D(x, y);
		this.m_dureevie = 300;
		this.m_speed = 5;
		this.m_keyH = keyH;
		m_gp.m_tab_Map[m_gp.dim].m_list_entity.add(this);
		this.m_direction = new int[2];
		this.getFireBallImage();
	}

	@Override
	public void draw(Renderer r) {
		BufferedImage l_image = m_idleImage.get(0);
		r.renderImage(l_image, (int) this.m_pos.x, (int) this.m_pos.y + 25, 15, 15);
	}

	@Override
	public void update() {
		if (m_keyH.isPressed(37)) { // GAUCHE
			m_direction[0] += -1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(38)) { // HAUT
			m_direction[0] += 0;
			m_direction[1] += -1;
		} 
		if (m_keyH.isPressed(39)) { // DROITE
			m_direction[0] += 1;
			m_direction[1] += 0;
		}
		if (m_keyH.isPressed(40)) { // BAS
			m_direction[0] += 0;
			m_direction[1] += 1;
		}
		
		if (m_direction[0] == 0 && m_direction[1] == 0) {
			m_direction[0] += 1;
			m_direction[1] += 0;
		}
		this.m_pos.x += m_speed * m_direction[0];
		this.m_pos.y += m_speed * m_direction[1];

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
	
	public void setDirection(int x, int y) {
		m_direction[0] = x;
		m_direction[1] = y;
	}

}
