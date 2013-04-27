package com.bbr.entity.terrain;

import com.bbr.core.Zone;

public class BreakablePlatformG extends BreakablePlatform {
	public BreakablePlatformG(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		this.terrainCollidable = true;
	}
}
