package com.bbr.entity.terrain;

import java.util.List;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.player.Pirate;

public class Exit extends Entity{

	public Exit(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = false;
	}
	
	public void preDt(){
		List<Entity> entities = container.getTerrainCollided(this);
		for(Entity e : entities){
			if(e instanceof Pirate){
				container.nextLevel();
			}
		}
	}

}
