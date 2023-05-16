package entity;

import main.GamePanel;
import main.Renderer;

public class SpeechBubble extends Entity{
	
	GamePanel m_gp;
	String m_text;
	float m_opacity;
	
	public SpeechBubble(GamePanel a_gp, String text, int x,int y){
		this.m_gp = a_gp;
		this.m_text = text;
		this.m_x = x;
		this.m_y = y;
		this.m_opacity = 1;
		
		m_gp.m_list_entity[m_gp.dim].add(this);
	}
	
	@Override
	public void update() {
		m_y -= 1;
		m_opacity -= 0.01;
		if(m_opacity <= 0) {
			m_status = Status.DESTROY;
		}
	}

	@Override
	public void draw(Renderer r) {
		r.renderText(m_text,(int) m_x,(int) m_y, m_opacity, 4, 15, 2);
	}
}
