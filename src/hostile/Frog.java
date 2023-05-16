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
		m_speed = 8;
		
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
				if (!collideWallNext(m_x+m_speed, m_y)) m_x += m_speed;
				break;
			case 2 :
				if (!collideWallNext(m_x-m_speed, m_y)) m_x -= m_speed;
				break;
			case 3 :
				if (!collideWallNext(m_x, m_y+m_speed)) m_y += m_speed;
				break;
			case 4 :
				if (!collideWallNext(m_x, m_y-m_speed)) m_y -= m_speed;
				break;
			default :
				break;
			}
			
		}
		
		if (m_timer > 150) {
			new SpeechBubble(m_gp, "Croa",(int) m_x,(int) m_y);
			m_dir = r.nextInt(5);
			m_timer = 0;
		}

		//System.out.println(distWall());
	}
	
	/*
	 * Fonction qui verifie si le monstre finit dans le mur apres le prochain movement
	 * @param x coordonners en x du monstre
	 * @param y coordonners en x du monstre
	 */
	boolean collideWallNext(float x, float y) {

		
		int posX = (int)x/m_gp.TILE_SIZE; 
		int posY = (int)y/m_gp.TILE_SIZE;
		
		if(m_gp.m_tileM.getMapTile(posX, posY) != 0) //on verifie la tile de gauche
		{
			return true;
		}
		else if(m_gp.m_tileM.getMapTile(posX+1, posY) != 0) //on verifie la tile de droite
		{
			return true;
		}
		else if(m_gp.m_tileM.getMapTile(posX, posY) != 0) //on verifie la tile du haut
		{
			return true;
		}
		else if(m_gp.m_tileM.getMapTile(posX, posY+1) != 0) //on verifie la tile du bas
		{
			return true;
		}
			
		return false;
	}


}
