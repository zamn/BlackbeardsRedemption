package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;


public class Ground extends Entity {

	public Ground(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = true;
	}

}
