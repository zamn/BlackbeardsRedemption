package com.bbr.entity.terrain;

import java.util.List;

import org.newdawn.slick.Graphics;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;


public class Ground extends Entity {

	public Ground(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = true;
	}
	@Override
	public void preDt() {
		List<Entity> entities = container.getTerrainCollided(this);
		//if (entities.size() > 0) System.out.println(entities.size());
		for (Entity e : entities) {
				// float startX = px, endX = px + sx;
				// float startY = py, endY = py + sy;
				float eX = e.getXpos(), eY = e.getYpos();
				float newX = eX, newY = eY;
				boolean changed = false;

				// if (startY <= eY && eY < endY) {
				if (e.getYvel() > 0) { // fall from above
					newY = py - e.getYsize();
					if (newY > eY) newY = eY;
				} else if (e.getYvel() < 0) { // bump from below
					newY = py + sy;
					if (newY < eY) newY = eY;
				}
				if (newY != eY) changed = true;
				e.setYpos(newY);
				if (changed) return;
				// }
		}
	}
	
	@Override
	public void draw(Graphics g) {
		System.out.println("Ground draw");
		super.draw(g);
	}
}
