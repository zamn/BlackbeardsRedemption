package com.bbr.entity.projectile;

import java.util.List;

import com.bbr.entity.Enemy;
import com.bbr.entity.Entity;
import com.bbr.entity.Unit;
import com.bbr.entity.terrain.BreakablePlatform;
import com.bbr.entity.terrain.FallingPlatform;
import com.bbr.player.Player;
import com.bbr.resource.Settings;

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
			if(collided == null) {
				List<Entity> platforms = container.getTerrainCollided(this);
				for(Entity e : platforms) {
					if(e instanceof BreakablePlatform)
						container.removeEntity(e);
				}
			}
			break;
		case ENEMY:
			collided = container.getEnemyCollided(this);
			System.out.print("Attacking...\n");
			if(collided == null) {
				List<Entity> platforms = container.getTerrainCollided(this);
				for(Entity e : platforms) {
					String type = (e instanceof BreakablePlatform) ? "Breakable" : "";
					if(!(e instanceof BreakablePlatform))
						type = (e instanceof FallingPlatform) ? "Falling" : "Normal";
					System.out.print("Platform: " + type + "\n");
					if(e instanceof BreakablePlatform)
						container.removeEntity(e);
				}
			} else System.out.print("Hit enemy!\n");
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
			if (collided instanceof Player && ((Player)collided).getHealth() <= 0 && !Settings.valueBoolean("undying")) {
				return; // let player realize the cause of their loss
			}
			if (collided instanceof Enemy && ((Enemy)collided).getHealth() <= 0)
				container.removeEntity(collided);
			// No longer need this
//			if (this instanceof ExplosiveProjectile) {
//				((ExplosiveProjectile)this).explode();
//			}
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
