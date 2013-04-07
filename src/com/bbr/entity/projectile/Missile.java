package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.projectile.Projectile.ExplosiveProjectile;
import com.bbr.resource.Settings;

public class Missile extends ExplosiveProjectile {
	public Missile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		vx = owner.isFacingRight() ? 10 : -10;
		vx += 1.2*owner.getXvel();

		explosionDamage = damage;
		duration = Settings.valueInt("fps")/10;
	}
}