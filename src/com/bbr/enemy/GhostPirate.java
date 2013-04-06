package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.resource.Settings;

public class GhostPirate extends Enemy {
	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	public GhostPirate(Zone zone, float x, float y) {
		super(zone, x, y, 0);
		vx = 1;
		terrainCollidable = false;
	}

	public void preDt() {
		vy = (float)(Math.sin(2*Math.PI * dip / dipMax));
		dip++;
		if (dip > dipMax) {
			dip = 0;
			vx = -vx;
		}
	}
}
