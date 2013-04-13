package com.bbr.entity.terrain;

import com.bbr.core.Zone;

public class FallingPlatform extends Platform {
	public FallingPlatform(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
	}
	
	public void preDt() {
		super.preDt();
		if(this.collidesWith(container.getPlayer())) {
			this.terrainCollidable = true;
		}
	}
}
