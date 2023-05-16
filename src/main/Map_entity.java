package main;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;

public class Map_entity {
	List<Entity> m_list_entity;
	
	public Map_entity() {
		 m_list_entity = new ArrayList<>();
	}
	
	public List<Entity >getEntities() {
		return m_list_entity;
	}

}
