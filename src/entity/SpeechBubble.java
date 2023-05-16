package entity;

import main.GamePanel;
import main.Renderer;
import utils.Vector2D;

public class SpeechBubble extends Entity{
	
	GamePanel m_gp;
	String m_text;
	float m_opacity;
	
	public SpeechBubble(GamePanel a_gp, String text, int x,int y){
		this.m_gp = a_gp;
		this.m_text = text;
		this.m_pos = new Vector2D(x, y);
		this.m_opacity = 1;
		
		m_gp.m_tab_Map[m_gp.dim].m_list_entity.add(this);
	}
	
	@Override
	public void update() {
		this.m_pos.y -= 1;
		m_opacity -= 0.01;
		if(m_opacity <= 0) {
			m_status = Status.DESTROY;
		}
	}

	@Override
	public void draw(Renderer r) {
		r.renderText(m_text,(int) this.m_pos.x,(int) this.m_pos.y, m_opacity, 4, 15, 2);
	}
}
