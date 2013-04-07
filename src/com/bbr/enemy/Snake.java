package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.resource.Settings;

public class Snake extends Enemy {
	private static final int BASE_HEALTH = 200;
	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	protected float startx;
	public Snake(Zone zone, float x, float y) {
		super(zone, x, y, BASE_HEALTH);
		vx = 1;
		terrainCollidable = true;
		startx = x;
	}

	public void preDt() {
		if(Math.abs(px - startx) > 300){
			vx = -vx;
		}
		
		if (container.getPlayer().collidesWith(this)) {
			container.getPlayer().hitBy(this, 100);
			
	}
		
	}
}
