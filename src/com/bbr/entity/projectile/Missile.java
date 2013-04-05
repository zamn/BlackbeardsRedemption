package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.projectile.Projectile.ExplosiveProjectile;
import com.bbr.resource.Settings;

public class Missile extends ExplosiveProjectile {
	public Missile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		vy = -8;

		explosionDamage = damage;
		explosionSize = (sx + sy)*3;
		duration = Settings.valueInt("fps");
	}
}