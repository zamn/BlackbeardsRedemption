package com.bbr.entity;

import com.bbr.core.Zone;

public class Unit extends Entity {
	public static final int BASE_COLLISION_DAMAGE = 25;
	protected int health = 0;
	protected int collisionDamage = BASE_COLLISION_DAMAGE;
	public Unit(Zone zone, float x, float y) {
		super(zone, x, y);
	}

	public void hitBy(Entity attacker, int damage) {
		health -= damage;
	}

	public int getHealth() { return health; }
	public int getCollisionDamage() { return collisionDamage; }

	public void setHealth(int newHealth) { health = newHealth; }
	public void setCollisionDamage(int newDamage) { collisionDamage = newDamage; }
}
