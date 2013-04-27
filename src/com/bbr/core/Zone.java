package com.bbr.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;

import com.bbr.entity.Enemy;
import com.bbr.entity.Entity;
import com.bbr.entity.Unit;
import com.bbr.entity.player.Player;
import com.bbr.entity.terrain.Platform;
import com.bbr.gui.Drawable;
import com.bbr.level.LevelHandler;
import com.bbr.resource.Settings;
import com.bbr.resource.Song;

// TODO add zone boundaries where entities are destroyed/paused
public class Zone implements Drawable {
	// Lists of Fliers
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToAdd = new ArrayList<Entity>();
	protected List<Entity> entitiesToRemove = new ArrayList<Entity>();
	protected Player player;
	protected LevelHandler levelHandler;
	// Scrolling
	protected Entity followed;
	protected int xScroll = 0, yScroll = 0;
	protected int xScrollTarget = 0, yScrollTarget = 0; // further = faster scroll
	// Multimedia Experience
	private String songName;
	protected Image background;

	public Zone(LevelHandler levelHandler) {
		this.levelHandler = levelHandler;
	}
	public void nextLevel() {
		levelHandler.nextLevel();
	}

	public void addEntity(Entity entity) {
		entitiesToAdd.add(entity);
		if (entity instanceof Player) player = (Player)entity;
	}
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}
	public boolean hasEntity(Entity entity) {
		return entities.contains(entity);
	}

	@Override
	public void draw(Graphics g) {
		updateScrolling();
		if (background != null) background.draw(0, 0);
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g);
		}
	}
	public void dt() {
		if(!Song.isPlaying(songName))
			Song.playMusic(songName);
		updateEntities();

		Entity flyer;
		for (int i = 0; i < entities.size(); i++) {
			flyer = entities.get(i);
			flyer.dt();
		}

		updateEntities();
		// scrolling velocity
		float xScrollDelta = (xScrollTarget - xScroll) * .1f;
		float yScrollDelta = (yScrollTarget - yScroll) * .1f;
		xScroll += xScrollDelta;
		yScroll += yScrollDelta;
	}

	private void updateEntities() {
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		entities.removeAll(entitiesToRemove);
		entitiesToRemove.clear();
	}

	public void clear() {
		Song.stopMusic();
		entitiesToAdd.clear();
		entitiesToRemove.addAll(entities);
	}
	// Scrolling
	public void follow(Entity entity) {
		followed = entity;
	}
	private void updateScrolling() {
		if (followed != null) {
//			float xCenter = followed.getXpos() + followed.getXsize() / 2;
//			xScroll = (int)(xCenter - Settings.valueInt("windowWidth")/2);
			float xPos = followed.getXpos();// + followed.getXsize() / 2;
			if (!followed.isFacingRight()) {
				xPos += followed.getXsize();
			} else {
				xPos += followed.getXsize();
			}
			xScrollTarget = (int)(xPos - Settings.valueInt("windowWidth")/2);
			//float yCenter = followed.getYpos() + followed.getYsize() / 2;
			//yScroll = (int)(yCenter - Settings.valueInt("windowHeight")/2);
		}
	}
	public int getXscroll() { return xScroll; }
	public int getYscroll() { return yScroll; }
	// collision detection
	public Platform getPlatformBelow(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
//					Utility.log(mover.getYpos() + mover.getYsize() - collided.getYpos());
//					if (mover.getYpos() + mover.getYsize() - collided.getYpos() < 0.001) {
						return (Platform)collided;
//					}
				}
			}
		}
		return null;
	}
	public Player getPlayer() {
		return player;
	}
	public List<Entity> getTerrainCollided(Entity collider) {
		List<Entity> entitiesCollided = new ArrayList<Entity>();
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != collider) {
				if (collided.isTerrainCollidable() && collided.collidesWith(collider)) {
					entitiesCollided.add(collided);
				}
			}
		}
		return entitiesCollided;
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
	public Player getPlayerCollided(Entity collider) {
		Entity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			if (entity != collider) {
				if (entity instanceof Player && entity.collidesWith(collider)) {
					return (Player)entity;
				}
			}
		}
		return null;
	}
	// collision detection with platforms
	public Platform collidesWithBottomOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverYpos = mover.getYpos() + mover.getYsize();
					if (moverYpos >= collided.getYpos()) {
						return (Platform)collided;
					}
				}
			}
		}
		return null;
	}
	public Platform collidesWithRightOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
						float moverXpos = mover.getXpos() + mover.getYpos();
						if(moverXpos >= collided.getXpos() && moverXpos <= (collided.getXpos() + collided.getXsize()))
							return (Platform) collided;
					
				}
			}
		}
		return null;
	}
	public Platform collidesWithLeftOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
						float moverXpos = mover.getXpos();
						if(moverXpos >= collided.getXpos() && moverXpos <= (collided.getXpos() + collided.getXsize()))
							return (Platform) collided;
					
				}
			}
		}
		return null;
	}
	public Platform collidesWithTopOf(Entity mover) {
		Entity collided;
		for (int i = 0; i < entities.size(); i++) {
			collided = entities.get(i);
			if (collided != mover) {
				if (collided instanceof Platform && collided.collidesWith(mover)) {
					float moverYpos = mover.getYpos() + mover.getYsize();
					if (moverYpos >= collided.getYpos()) {
						return (Platform)collided;
					}
				}
			}
		}
		return null;
	}
	// Multimedia
	public void setBackground(Image bg){
		this.background = bg;
	}
	public void setMusic(String song){
		songName = song;
	}
}
