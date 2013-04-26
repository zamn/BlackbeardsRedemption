package com.bbr.entity.terrain;

import java.util.List;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;


public class Ground extends Platform {

	public Ground(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = true;
	}

}
