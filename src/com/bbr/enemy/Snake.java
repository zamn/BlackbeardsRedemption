package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.resource.Settings;

public class Snake extends Enemy {
	private static final int BASE_HEALTH = 200;
	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	protected float startx;
	protected int hit_delay;
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
		
		if (container.getPlayer().collidesWith(this) && hit_delay <=0) {
			container.getPlayer().hitBy(this, 100);
			hit_delay = 25;
	}
		if (hit_delay > 0)
			hit_delay--;
		
	}
}
