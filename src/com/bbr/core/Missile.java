package com.bbr.core;

import com.bbr.core.Projectile.ExplosiveProjectile;

public class Missile extends ExplosiveProjectile {
	public Missile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TARGET_ENEMY;
		vy = -8;
		//
		explosionDamage = damage;
		explosionSize = (sx + sy)*5;
	}
}
