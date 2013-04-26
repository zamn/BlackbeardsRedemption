package com.bbr.entity.player;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.projectile.Projectile;
import com.bbr.entity.projectile.SwordAttack;
import com.bbr.resource.Settings;
import com.bbr.resource.Utility;
public class Pirate extends Player {
	@Override
	public int getBaseHealth() { return 1000; }
	public static final int BASE_FIREDELAY = Settings.valueInt("fps")/2;
	public static final int BASE_MOVESPEED = 10;

	protected int attackingFrames = 0; // for animation

	public Pirate(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		fireDelay = BASE_FIREDELAY;
		moveSpeed = BASE_MOVESPEED;
	}

	@Override
	protected void fireProjectile() { // sword slash!
		attackingFrames = Settings.valueInt("fps")/2;
		Projectile fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py);
//		container.addEntity(fired);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy /3);
		container.addEntity(fired);
		fired = new SwordAttack(this, px + (flipHorizontal ? 0 : sx), py + sy *2/3);
//		container.addEntity(fired);
	}
	@Override
	public Image getFrameToDraw() {
		if (attackingFrames > 0) {
			Utility.log("Pirate attack");
			return sprite.getFrame("attack");
		} else if (vy < 0) {
			Utility.log("Pirate jump");
			return sprite.getFrame("jump");
		} else if (Math.abs(vx) > 0.01) {
			Utility.log("Pirate move");
			return sprite.getFrame("move");
		}
		Utility.log("Pirate normal");
		return super.getFrameToDraw();
	}

	@Override
	protected void preDt() {
		if (attackingFrames > 0) {
			attackingFrames--;
		}
		if (attackingFrames == 0) { // change hitbox back
			float oldXpos = px + sx;
			float oldHeight = sy;
			setYpos(py - sy + oldHeight);
			if (flipHorizontal) {
				setXpos(oldXpos - sx);
			}
		}
		super.preDt();
	}
}
