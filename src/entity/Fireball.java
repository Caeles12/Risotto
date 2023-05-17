package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Renderer;
import utils.Vector2D;
import utils.Collider;
import utils.Circle;

public class Fireball extends Entity {
	
	KeyHandler m_keyH;
	Player m_player;
	int m_dureevie;
	float[] m_direction;
	
	public Fireball(GamePanel a_gp, Player p, KeyHandler keyH, int x, int y) {
		this.m_gp = a_gp;
		this.m_pos = new Vector2D(x, y+25);
		this.m_dureevie = 300;
		this.m_speed = 9;
		this.m_keyH = keyH;
		m_gp.m_tab_Map[m_gp.dim].m_list_entity.add(this);
		this.m_collider = new Collider(new Circle(m_pos,new Vector2D(3, 3),7), m_gp);
		this.m_direction = new float[2];
		this.m_player = p;
		this.getFireBallImage();
	}

	@Override
	public void draw(Renderer r) {
		BufferedImage l_image = m_idleImage.get(0);
		r.renderImage(l_image, (int) this.m_pos.x, (int) this.m_pos.y, 15, 15);
	}

	@Override
	public void update() {	
		
		this.m_collider.m_shape.setOrigin(m_pos);
		
		this.m_pos.x += (int) m_speed * m_direction[0];
		this.m_pos.y += (int) m_speed * m_direction[1];
		
		if(this.m_collider.collidingTileMap(m_gp.m_tileM)) {
			m_status = Entity.Status.DESTROY;
		}
		
		for(Entity entity: m_gp.m_tab_Map[m_gp.dim].m_list_entity) {
			if(entity != this && entity.m_collider != null) {
				try {
					if(entity instanceof Hostile && this.m_collider.colliding(entity.m_collider)) {
						((Hostile) entity).takeDamage(25);
						m_status = Entity.Status.DESTROY;
					}
				} catch(Exception e) {
					System.out.println(entity.getClass().getName());
				}
			}
		}

		m_dureevie -= 1;
		if (m_dureevie <= 0) {
			m_status = Entity.Status.DESTROY;
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
	
	public void setDirection(float x, float y) {
		m_direction[0] = x;
		m_direction[1] = y;
	}

}
