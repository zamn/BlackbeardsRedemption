package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.resource.Settings;

public class GhostRock extends Projectile {
	public GhostRock(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ALL;
		vy = 5;

		duration = Settings.valueInt("fps")*3;
		terrainCollidable = false;
	}
}
