package com.bbr.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.bbr.gui.Drawable;

public class Zone implements Drawable {
	// Lists of Fliers
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToAdd = new ArrayList<Entity>();
	protected List<Entity> entitiesToRemove = new ArrayList<Entity>();
	// Scrolling
	protected int xScroll = 0, yScroll = 0;

	public Zone() {
	}
	///////////////////////////////////////////////////
	//               Add/Remove Fliers               //
	///////////////////////////////////////////////////
	public void addEntity(Entity entity) {
		//flyer.container = this;
		entitiesToAdd.add(entity);
	}
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}
	public boolean hasEntity(Entity entity) {
		return entities.contains(entity);
	}

	/////////////////////////////////////////////////////
	//               Collision Detection               //
	/////////////////////////////////////////////////////
	public Entity getCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity.collidesWith(collider)) {
					return entity;
				}
			}
		}
		return null;
	}
	public Unit getUnitCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Unit && entity.collidesWith(collider)) {
					return (Unit)entity;
				}
			}
		}
		return null;
	}
	public Enemy getEnemyCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Enemy && entity.collidesWith(collider)) {
					return (Enemy)entity;
				}
			}
		}
		return null;
	}
	public void draw(Graphics g) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g);
		}
	}
	public void dt() {
		updateEntities();

		Entity flyer;
		for (int i = 0; i < entities.size(); i++) {
			flyer = entities.get(i);
			flyer.dt();
		}

		// TODO tick
		updateEntities();
	}

	private void updateEntities() {
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		entities.removeAll(entitiesToRemove);
		entitiesToRemove.clear();
	}
	public float getXscroll() {
		// TODO Auto-generated method stub
		return xScroll;
	}
	public float getYscroll() {
		// TODO Auto-generated method stub
		return yScroll;
	}
}
