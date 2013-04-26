package com.bbr.enemy;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.resource.Utility;

public class Snake extends Enemy {
	@Override
	public int getBaseHealth() { return 200; }

	protected float startX;
	protected int hitDelay;
	
	public Snake(Zone zone, float x, float y) {
		super(zone, x, y);
		flipHorizontal = true;
		vx = -1;
		terrainCollidable = true;
		startX = x;
	}
	
	@Override
	public Image getFrameToDraw() {
		if (Math.abs(vx) > 0.01) {
			Utility.log("Snake move");
			return sprite.getFrame("snakeMove");
		}
		Utility.log("Snake normal");
		return super.getFrameToDraw();
	}

	@Override
	public void preDt() {
		if(Math.abs(px - startX) > 300){
			//vx = -vx;
		}
		
		if (container.getPlayer().collidesWith(this) && hitDelay <=0) {
			container.getPlayer().hitBy(this, 100);
			hitDelay = 25;
		}
		if (hitDelay > 0)
			hitDelay--;
		
	}
}
