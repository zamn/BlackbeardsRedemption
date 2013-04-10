package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.projectile.GhostRock;
import com.bbr.resource.Settings;

public class GhostPirate extends Enemy {
	public int getBaseHealth() { return 150; }

	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	protected static final int FIRE_COOLDOWN = Settings.valueInt("fps") * 3;
	protected int fireCooldown = FIRE_COOLDOWN;
	public GhostPirate(Zone zone, float x, float y) {
		super(zone, x, y);
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
	public void postDt() {
		if (fireCooldown > 0) fireCooldown--;
		if (fireCooldown <= 0) {
			container.addEntity(new GhostRock(this, px + (flipHorizontal ? -40 - sx : 40 + sx) + vx, py));
			fireCooldown = FIRE_COOLDOWN;
		}
	}
}
