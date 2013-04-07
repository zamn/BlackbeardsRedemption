package com.bbr.player;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.projectile.Missile;
import com.bbr.entity.projectile.Projectile;
import com.bbr.health.*;
import com.bbr.resource.Settings;
public class Pirate extends Player {
	public static final int BASE_HEALTH = 1000;
	public static final int BASE_FIREDELAY = Settings.valueInt("fps")/2;
	public static final int BASE_SPECIALDELAY = Settings.valueInt("fps");
	public static final int BASE_MOVESPEED = 10;
	// Special Ability: Charge
	protected static final float CHARGE_FACTOR = 2.5f;
	protected static final int CONTROL_LOCK_DURATION = 20; // minimum charge time before controls unlocked
	protected int chargeTime = 0;
	protected int attacking = 0;
	protected boolean charging = false;
	public Pirate(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos, BASE_HEALTH);
		fireDelay = BASE_FIREDELAY;
		specialDelay = BASE_SPECIALDELAY;
		moveSpeed = BASE_MOVESPEED;
	}

	protected void fireProjectile() { // fire the missile!
		stopCharging(); // firing cancels charge
		attacking = Settings.valueInt("fps")/2;
		Projectile fired = new Missile(this, px, py);
		if (charging) fired.setXvel(fired.getXvel() * 5);
		container.addEntity(fired);
	}
	public Image getFrameToDraw() {
		if (Math.abs(vx) > 0.01) {
			return sprite.getFrame("move");
		}
		else if (attacking > 0) {
			return sprite.getFrame("attack");
		}
		return super.getFrameToDraw();
	}
	// Rush Attack!
	protected void preDt() {
		if (attacking > 0)
			attacking--;
		if (charging) {
			if (chargeTime < CONTROL_LOCK_DURATION) {
				chargeTime++;
			} else { // allow controls
				this.preventMovement = false;
			}
		}
		super.preDt();
	}
	protected void moved() { /*stopCharging();*/ }
	protected void stopCharging() {
		if (charging) {
			charging = false;
			vy = 0;
			this.collisionDamage = BASE_COLLISION_DAMAGE;
		}
	}
	protected void useSpecial() { // Rush: charges forward, cannot attack/move for short time, afterwards keys cancel charge
		chargeTime = 0;
		if (vy != 0 || (vx == 0 && vy == 0)) {
			vy = moveSpeed * CHARGE_FACTOR * (vy <= 0 ? -1 : 1); // charge backwards if moving backwards
		}
		if (vx != 0) {
			vx = moveSpeed * CHARGE_FACTOR * (vx <= 0 ? -1 : 1);
		}
		applyMovementModifiers();
		this.collisionDamage /= 3;
		this.preventMovement = true;
		charging = true;
	}

	@Override
	public int getBaseHealth() {
		return Pirate.BASE_HEALTH;
	}
}
