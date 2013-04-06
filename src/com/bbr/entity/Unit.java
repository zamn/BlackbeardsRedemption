package com.bbr.entity;

import com.bbr.core.Zone;

public class Unit extends Entity {
	public static final int BASE_COLLISION_DAMAGE = 25;
	protected int health = 0; 
	protected int collisionDamage = BASE_COLLISION_DAMAGE;
	public Unit(Zone zone, float x, float y, int defaultHealth) {
		super(zone, x, y);
		health = defaultHealth;
	}
	public void hitBy(Entity attacker, int damage) {
		if(damage > 0)
			health -= damage;
	}

	public int getHealth() {
		return health;
	}
	public int getCollisionDamage() {
		return collisionDamage;
	}
	public void heal(int damageHealed){
		if(damageHealed > 0)
			health += damageHealed;
	}
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	public boolean isDead(){
		return health <= 0;
	}
	public void setCollisionDamage(int newDamage) {
		collisionDamage = newDamage;
	}
}