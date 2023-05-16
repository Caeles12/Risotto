package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Renderer;

/**
 * Entité de base du jeu
 *
 */
public abstract class Entity {
	public int m_x, m_y;				//position sur la map
	public int m_speed;					//Déplacement de l'entité
	public List<BufferedImage> m_idleImage = new ArrayList<>();	//Une image de l'entité
	public abstract void draw(Renderer a_g2);
}
