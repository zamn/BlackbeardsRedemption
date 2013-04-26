package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.entity.projectile.Projectile;
import com.bbr.entity.terrain.Platform;

public class BreakablePlatform extends Platform {
	
	public BreakablePlatform(Zone container, float x, float y) {
		super(container, x, y);
	}
	
	public void preDt() {
		super.preDt();
		Entity e = container.getCollided(this);
		if(e instanceof Projectile) {
			container.removeEntity(this);
		}
	}
}
