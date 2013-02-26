package com.bbr.core;

import com.bbr.resource.Settings;

public abstract class Projectile extends Entity {
	protected Entity owner;
	// Targetting (used in collision checking)
	protected static final short TARGET_ALL = 0;
	protected static final short TARGET_ENEMY = 1;
	protected short targetting = TARGET_ALL; // NOT bit set
	// Attributes
	protected static final short ATT_PIERCE = 1 << 0; // hits multiple targets
	protected static final short ATT_LASER = 1 << 1; // hits first target
	protected short attributes = 0; // bit set
	//
	protected int damage;
	//
	public Projectile(Entity owner, float xpos, float ypos) {
		super(owner.getZone(), xpos, ypos);
		this.owner = owner;
	}
	//
	public void addAttribute(short attributeFlag) {
		attributes |= attributeFlag;
	}
	public void removeAttribute(short attributeFlag) {
		if (hasAttribute(attributeFlag)) attributes ^= attributeFlag;
	}
	public boolean hasAttribute(short attributeFlag) {
		return (attributes & attributeFlag) != 0;
	}
	//
	public void dt() {
		preDt();
		px += vx;
		py += vy;
		checkCollision();
		postDt();
	}
	///////////////////////////////////////////
	//               Collision               //
	///////////////////////////////////////////
	protected Entity checkCollision() {
		Entity collided = getCollided();
		if (collided != null) hit(collided);
		return collided;
	}
	protected Entity getCollided() {
		Entity collided = null;
		if (targetting == TARGET_ALL) {
			collided = container.getUnitCollided(this);
		} else if (targetting == TARGET_ENEMY) {
			collided = container.getEnemyCollided(this);
		}
		return collided;
	}
	protected void hit(Entity collided) {
		if (collided instanceof Unit) {
			collided.hitBy(this.owner, damage);
			if (collided instanceof Player && ((Player)collided).health <= 0 && !Settings.valueBoolean("undying")) {
				return; // let player realize the cause of their loss
			}
			if (this instanceof ExplosiveProjectile) {
				((ExplosiveProjectile)this).explode();
			}
			//
			if (!hasAttribute(ATT_PIERCE)) {
				container.removeEntity(this);
			}
		}
	}
	//
	public void boostDmg(int boost) {
		damage += boost;
	}
	public void multDmg(double boost) {
		damage = (int)(damage * boost);
	}
	//
	//
	//
	//
	//
	public static abstract class ExplosiveProjectile extends Projectile {
		protected int explosionSize;
		protected int explosionDamage;
		//
		public ExplosiveProjectile(Entity owner, float xpos, float ypos) {
			super(owner, xpos, ypos);
			explosionSize = 300; // TODO should this be mandatory part of constructor instead?
			explosionDamage = 0;
		}
		public void explode() {
			Explosion e = new Explosion(owner, px+sx/2, py+sy/2,
					explosionSize, explosionDamage);
			container.addEntity(e);
		}
	}
}
