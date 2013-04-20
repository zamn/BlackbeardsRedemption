package com.bbr.entity;

import com.bbr.core.Zone;
public abstract class Unit extends Entity {
	public static final int BASE_COLLISION_DAMAGE = 25;
	protected int health = 0; 
	protected int collisionDamage = BASE_COLLISION_DAMAGE;
	//private HealthController healthBar;
	public Unit(Zone zone, float x, float y) {
		super(zone, x, y);
		health = getBaseHealth();
	}
	public abstract int getBaseHealth();

	public void dt() {
		if (isDead()) {
			die();
			return;
		} else {
			super.dt();
		}
	}
	public void die() {
		container.removeEntity(this);
	}

	public void hitBy(Entity attacker, int damage) {
//		System.out.println(this + " was hit by: " + attacker + " for: " + damage);
		if(damage > 0)
			health -= damage;
	}
	public void heal(int damageHealed){
		if(damageHealed > 0)
			health += damageHealed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	public boolean isDead(){
		return health <= 0;
	}

	public int getCollisionDamage() {
		return collisionDamage;
	}
	public void setCollisionDamage(int newDamage) {
		collisionDamage = newDamage;
	}
}
