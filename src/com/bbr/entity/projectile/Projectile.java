package com.bbr.entity.projectile;

import com.bbr.entity.Entity;
import com.bbr.entity.Unit;

public abstract class Projectile extends Entity {
	protected Entity owner;
	// Targetting (used in collision checking)
	protected enum TargetType {ALL, ENEMY, PLAYER}
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

	@Override
	public void dt() {
		if (duration == 0) {
			container.removeEntity(this);
			return;
		} else if (duration > 0) {
			duration--;
		}
		preDt();
		setXpos(px + vx);
		setYpos(py + vy);
		checkCollision();
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
		case PLAYER:
			collided = container.getPlayerCollided(this);
			break;
		}
		return collided;
	}
	protected void hit(Entity collided) {
		if (collided instanceof Unit) {
			collided.hitBy(this.owner, damage);
			if (!hasAttribute(ATT_PIERCE)) {
				container.removeEntity(this);
			}
			// No longer need this
//			if (this instanceof ExplosiveProjectile) {
//				((ExplosiveProjectile)this).explode();
//			}
		}
	}
	public void boostDmg(int boost) {
		damage += boost;
	}
	public void multDmg(double boost) {
		damage = (int)(damage * boost);
	}
}
