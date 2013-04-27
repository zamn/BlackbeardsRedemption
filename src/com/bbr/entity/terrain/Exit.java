package com.bbr.entity.terrain;

import java.util.List;

import org.newdawn.slick.Graphics;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.entity.player.Pirate;

public class Exit extends Entity {

	public Exit(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = false;
	}
	
	@Override
	public void preDt(){
		List<Entity> entities = container.getTerrainCollided(this);
		for(Entity e : entities){
			if(e instanceof Pirate){
				container.nextLevel();
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
//		Utility.log("Exit draw");
		super.draw(g);
	}
}
