package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.resource.Settings;

public class Snake extends Enemy {
	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	public Snake(Zone zone, float x, float y) {
		super(zone, x, y);
		vx = 1;
		terrainCollidable = true;
	}

	public void preDt() {
		vx = -1;
		
	}
}
