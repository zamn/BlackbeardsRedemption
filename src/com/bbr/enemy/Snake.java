package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;

public class Snake extends Enemy {
	public int getBaseHealth() { return 200; }

	protected float startX;
	protected int hitDelay;
	public Snake(Zone zone, float x, float y) {
		super(zone, x, y);
		vx = 1;
		terrainCollidable = true;
		startX = x;
	}

	public void preDt() {
		if(Math.abs(px - startX) > 300){
			vx = -vx;
		}
		
		if (container.getPlayer().collidesWith(this) && hitDelay <=0) {
			container.getPlayer().hitBy(this, 100);
			hitDelay = 25;
		}
		if (hitDelay > 0)
			hitDelay--;
		
	}
}
