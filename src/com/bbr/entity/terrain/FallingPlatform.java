package com.bbr.entity.terrain;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.entity.player.Player;
import com.bbr.entity.projectile.Projectile;
import com.bbr.resource.Settings;

public class FallingPlatform extends Platform {
	protected int fallingCount = 0;
	protected boolean falling = false;
	public FallingPlatform(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
	}

	@Override
	public Image getFrameToDraw() {
		if (falling) {
			return sprite.getFrame("danger");
		}
		return super.getFrameToDraw();
	}
	
	@Override
	public void preDt() {
		super.preDt();
		Player p = container.getPlayer();
		if(!(falling)  && this.futureCollidesWith(p, 0, p.getYvel()))
			falling = true;
		Entity e = container.getCollided(this);
		if(!(falling) && e instanceof Projectile) {
			falling = true;
		}
		if (falling)
			fallingCount++;
		if (fallingCount > Settings.valueInt("fps")/2)
			this.terrainCollidable = true;
	}	
}
