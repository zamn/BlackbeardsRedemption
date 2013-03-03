package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.projectile.Projectile.ExplosiveProjectile;

public class Missile extends ExplosiveProjectile {
	public Missile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		vy = -8;

		explosionDamage = damage;
		explosionSize = (sx + sy)*5;
	}
}