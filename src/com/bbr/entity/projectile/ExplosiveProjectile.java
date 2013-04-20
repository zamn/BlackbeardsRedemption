package com.bbr.entity.projectile;

import com.bbr.entity.Entity;

public class ExplosiveProjectile extends Projectile {
	protected int explosionSize;
	protected int explosionDamage;

	public ExplosiveProjectile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		explosionSize = 0;
		explosionDamage = 0;
	}
	public void explode() {
		Explosion e = new Explosion(owner, px+sx/2, py+sy/2,
				explosionSize, explosionDamage);
		container.addEntity(e);
	}
}
