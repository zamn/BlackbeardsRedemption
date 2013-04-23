package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.resource.Settings;

public class SwordAttack extends Projectile {
	public SwordAttack(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		vx = owner.isFacingRight() ? 15 : -15;
		vx += 1.2*owner.getXvel();

		duration = Settings.valueInt("fps")/5;
		terrainCollidable = false;
	}
}