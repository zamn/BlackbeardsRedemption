package com.bbr.entity.terrain;

import java.util.List;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;

// solid platform
public class Platform extends Entity {
	public Platform(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		terrainCollidable = false;
	}
	public void preDt() {
		List<Entity> entities = container.getTerrainCollided(this);
		System.out.println(entities.size());
		for (Entity e : entities) {
			float startX = px, endX = px + sx;
			float startY = py, endY = py + sy;
			float eX = e.getXpos(), eY = e.getYpos();
			float newX = eX, newY = eY;

//			if (startX <= eX && eX < endX) {
				if (e.getXvel() > 0) { // left dash to right
					newX = px - e.getXsize();
					if (newX > eX) newX = eX;
				} else if (e.getXvel() < 0) { // right dash to left
					newX = px + sx;
					if (newX < eX) newX = eX;
				}
				e.setXpos(newX);
//			}
//			if (startY <= eY && eY < endY) {
				if (e.getYvel() > 0) { // fall from above
					newY = py - e.getYsize();
					if (newY > eY) newY = eY;
				} else if (e.getYvel() < 0) { // bump from below
					newY = py + sy;
					if (newY < eY) newY = eY;
				}
				e.setYpos(newY);
//			}
		}
	}
}
