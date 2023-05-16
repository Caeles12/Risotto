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

	int m_dir =0;
	
	public Frog(GamePanel a_gp,int x,int y){
		super( a_gp);
		this.m_pos = new Vector2D(x, y);
	}

	@Override
	protected void setDefaultValues() {
		m_life = 100;
		m_damage = 10;
		m_speed = 8;
		m_timer = 0;
		ptr_list_image = 0;
		m_timerAnimation = 0;
		
	}

	@Override
	public void getHostileImage() {
		//gestion des expections 
		try {
			for(int i = 1 ; i < 6 ; i++) m_idleImage.add(ImageIO.read(getClass().getResource("/hostile/grenouille_"+i+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void move() {
		
		if(m_timerAnimation >= 15) {
			ptr_list_image++;
			m_timerAnimation = 0;
		}
		
		if (m_timer > 120) {
			
			
			
			
			switch(m_dir) {
			case 1 : 
				if (!collideWallNext(this.m_pos.x+m_speed, this.m_pos.y)) this.m_pos.x += m_speed;
				break;
			case 2 :
				if (!collideWallNext(this.m_pos.x-m_speed, this.m_pos.y)) this.m_pos.x -= m_speed;
				break;
			case 3 :
				if (!collideWallNext(this.m_pos.x, this.m_pos.y+m_speed)) this.m_pos.y += m_speed;
				break;
			case 4 :
				if (!collideWallNext(this.m_pos.x, this.m_pos.y-m_speed)) this.m_pos.y -= m_speed;
				break;
			default :
				break;
			}
			
		}
		
		if (m_timer > 150) {
			new SpeechBubble(m_gp, "Croa",(int) this.m_pos.x,(int) this.m_pos.y, 4, 15, 2);
			m_dir = r.nextInt(5);
			m_timer = 0;
		}

		//System.out.println(distWall());
	}
	
	/*
	 * Fonction qui verifie si le monstre finit dans le mur apres le prochain movement
	 * @param x coordonnees en x du monstre
	 * @param y coordonnees en x du monstre
	 */
	boolean collideWallNext(float x, float y) {

		
		int posX = (int)x/m_gp.TILE_SIZE; 
		int posY = (int)y/m_gp.TILE_SIZE;
		Tile[] m_tile = m_gp.m_tileM.getTile();
		
		
		if(m_tile[m_gp.m_tileM.getMapTile(posX, posY)].m_collision == true) //on verifie la tile de gauche
		{
			return true;
		}
		else if(m_tile[m_gp.m_tileM.getMapTile(posX+1, posY)].m_collision == true) //on verifie la tile de droite
		{
			return true;
		}
		else if(m_tile[m_gp.m_tileM.getMapTile(posX, posY)].m_collision == true) //on verifie la tile du haut
		{
			return true;
		}
		else if(m_tile[m_gp.m_tileM.getMapTile(posX, posY+1)].m_collision == true) //on verifie la tile du bas
		{
			return true;
		}
			
		return false;
	}


}
