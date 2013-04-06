package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.projectile.Projectile.ExplosiveProjectile;
import com.bbr.player.Player;
import com.bbr.resource.Settings;

public class Missile extends ExplosiveProjectile {
	public Missile(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 25;
		targetting = TargetType.ENEMY;
		if (owner instanceof Player)
			vx = owner.isFacingRight() ? 10 : -10;

		explosionDamage = damage;
		duration = Settings.valueInt("fps")/10;
	}
}