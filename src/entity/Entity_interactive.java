package entity;

import java.util.ArrayList;
import java.util.List;

import main.GamePanel;

public abstract class Entity_interactive extends Entity{
	protected List<Integer> objet_interne = new ArrayList<>();
	protected GamePanel m_gp;
	public abstract List<Integer> interaction();
}
