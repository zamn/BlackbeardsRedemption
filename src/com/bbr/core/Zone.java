package com.bbr.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.bbr.entity.Enemy;
import com.bbr.entity.Entity;
import com.bbr.entity.Unit;
import com.bbr.gui.Drawable;
import com.bbr.resource.Settings;

// TODO add zone boundaries where entities are destroyed/paused
public class Zone implements Drawable {
	// Lists of Fliers
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToAdd = new ArrayList<Entity>();
	protected List<Entity> entitiesToRemove = new ArrayList<Entity>();
	// Scrolling
	protected Entity followed;
	protected int xScroll = 0, yScroll = 0;

	public Zone() { }

	public void addEntity(Entity entity) {
		entitiesToAdd.add(entity);
	}
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}
	public boolean hasEntity(Entity entity) {
		return entities.contains(entity);
	}

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
		updateScrolling();
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

		updateEntities();
	}

	private void updateEntities() {
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		entities.removeAll(entitiesToRemove);
		entitiesToRemove.clear();
	}

	public void follow(Entity entity) {
		followed = entity;
	}
	private void updateScrolling() {
		if (followed != null) {
			float xCenter = followed.getXpos() + followed.getXsize() / 2;
			float yCenter = followed.getYpos() + followed.getYsize() / 2;
			xScroll = (int)(xCenter - Settings.valueInt("windowWidth")/2);
			yScroll = (int)(yCenter - Settings.valueInt("windowHeight")/2);
		}
	}
	public int getXscroll() { return xScroll; }
	public int getYscroll() { return yScroll; }
}
