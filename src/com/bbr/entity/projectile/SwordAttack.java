package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.resource.Settings;

public class SwordAttack extends Projectile {
	public SwordAttack(Entity owner, float xpos, float ypos) {
		super(owner, xpos, ypos);
		damage = 100;
		targetting = TargetType.ENEMY;
		vx = owner.isFacingRight() ? 10 : -10;
		vx += 1.2*owner.getXvel();

		duration = Settings.valueInt("fps")/10;
	}
}