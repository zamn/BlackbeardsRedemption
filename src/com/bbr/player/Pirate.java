package com.bbr.player;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.projectile.SwordAttack;
import com.bbr.entity.projectile.Projectile;
import com.bbr.resource.Settings;
public class Pirate extends Player {
	public int getBaseHealth() { return 1000; }
	public static final int BASE_FIREDELAY = Settings.valueInt("fps")/2;
	public static final int BASE_SPECIALDELAY = Settings.valueInt("fps");
	public static final int BASE_MOVESPEED = 10;
	// Special Ability: Charge
	protected static final float CHARGE_FACTOR = 2.5f;
	protected static final int CONTROL_LOCK_DURATION = Settings.valueInt("fps")/2; // minimum charge time before controls unlocked
	protected int chargeTime = 0;
	protected boolean charging = false;

	protected int attackingFrames = 0; // for animation

	public Pirate(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		fireDelay = BASE_FIREDELAY;
		specialDelay = BASE_SPECIALDELAY;
		moveSpeed = BASE_MOVESPEED;
	}

	protected void fireProjectile() { // sword slash!
		attackingFrames = Settings.valueInt("fps")/2;
		Projectile fired = new SwordAttack(this, px, py);
		container.addEntity(fired);
	}
	public Image getFrameToDraw() {
		if (Math.abs(vx) > 0.01) {
			return sprite.getFrame("move");
		}
		else if (attackingFrames > 0) {
			return sprite.getFrame("attack");
		}
		return super.getFrameToDraw();
	}
	// Rush Attack!
	protected void preDt() {
		if (attackingFrames > 0)
			attackingFrames--;
		if (charging) {
			if (chargeTime < CONTROL_LOCK_DURATION) {
				chargeTime++;
			} else { // allow controls
				this.preventMovement = false;
			}
		}
		super.preDt();
	}
	protected void moved() {
		stopCharging();
	}
	protected void stopCharging() {
		if (charging) {
			charging = false;
		}
	}
	protected void useSpecial() { // Rush: charges forward
		chargeTime = 0;
		if (vy != 0 || (vx == 0 && vy == 0)) {
			vy = moveSpeed * CHARGE_FACTOR * (vy <= 0 ? -1 : 1); // charge backwards if moving backwards
		}
		if (vx != 0) {
			vx = moveSpeed * CHARGE_FACTOR * (vx <= 0 ? -1 : 1);
		}
		applyMovementModifiers();
		this.preventMovement = true;
		charging = true;
	}
}
