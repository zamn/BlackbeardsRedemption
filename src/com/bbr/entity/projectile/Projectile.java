package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.Unit;
import com.bbr.player.Player;
import com.bbr.resource.Settings;

public abstract class Projectile extends Entity {
	protected Entity owner;
	// Targetting (used in collision checking)
	protected enum TargetType {ALL, ENEMY}
	protected TargetType targetting = TargetType.ALL;
	// Attributes
	protected static final short ATT_PIERCE = 1 << 0; // hits multiple targets
	protected static final short ATT_LASER = 1 << 1; // hits first target
	protected short attributes = 0; // bit set
	protected int duration = -1; // -1 = never decays

	protected int damage;

	public Projectile(Entity owner, float xpos, float ypos) {
		super(owner.getZone(), xpos, ypos);
		this.owner = owner;
	}

	public void addAttribute(short attributeFlag) {
		attributes |= attributeFlag;
	}
	public void removeAttribute(short attributeFlag) {
		if (hasAttribute(attributeFlag)) attributes ^= attributeFlag;
	}
	public boolean hasAttribute(short attributeFlag) {
		return (attributes & attributeFlag) != 0;
	}

	public void dt() {
		preDt();
		px += vx;
		py += vy;
		checkCollision();
		if (duration == 0) {
			container.removeEntity(this);
		} else if (duration > 0) {
			duration--;
		}
		postDt();
	}
	// Collision
	protected Entity checkCollision() {
		Entity collided = getCollided();
		if (collided != null) hit(collided);
		return collided;
	}
	protected Entity getCollided() {
		Entity collided = null;
		switch (targetting) {
		case ALL:
			collided = container.getUnitCollided(this);
			break;
		case ENEMY:
			collided = container.getEnemyCollided(this);
			break;
		}
		return collided;
	}
	protected void hit(Entity collided) {
		if (collided instanceof Unit) {
			collided.hitBy(this.owner, damage);
			if (collided instanceof Player && ((Player)collided).getHealth() <= 0 && !Settings.valueBoolean("undying")) {
				return; // let player realize the cause of their loss
			}
			if (this instanceof ExplosiveProjectile) {
				((ExplosiveProjectile)this).explode();
			}
			if (!hasAttribute(ATT_PIERCE)) {
				container.removeEntity(this);
			}
		}
	}
	public void boostDmg(int boost) {
		damage += boost;
	}
	public void multDmg(double boost) {
		damage = (int)(damage * boost);
	}

	public static abstract class ExplosiveProjectile extends Projectile {
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
}
