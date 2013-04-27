package com.bbr.enemy;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;

public class Snake extends Enemy {
	@Override
	public int getBaseHealth() { return 200; }

	protected float startX;
	protected int hitDelay;
	protected SnakeSpawner parent = null;

	protected float lastX, lastY;
	
	public Snake(Zone zone, float x, float y) {
		super(zone, x, y);
		flipHorizontal = true;
		vx = -1;
		terrainCollidable = true;
		startX = x;
	}
	
	public Snake(Zone zone, float x, float y, SnakeSpawner spawner) {
		super(zone, x, y);
		flipHorizontal = true;
		vx = -1;
		terrainCollidable = true;
		startX = x;
		parent = spawner;
	}
	
	@Override
	public Image getFrameToDraw() {
		if (Math.abs(vx) > 0.01) {
//			Utility.log("Snake move");
			return sprite.getFrame("snakeMove");
		}
//		Utility.log("Snake normal");
		return super.getFrameToDraw();
	}

	@Override
	public void preDt() {
		if(vx < 0 && container.collidesWithLeftOf(this) != null){
			vx = -vx;
		}
		else if(vx > 0 && container.collidesWithRightOf(this) != null) {
		//if(Math.abs(px - startX) > 300){
		//if (Math.abs(lastX - px) < 0.1 && Math.abs(lastY - py) < 0.1) {
			vx = -vx;
		}
		
		if (container.getPlayer().collidesWith(this) && hitDelay <=0) {
			container.getPlayer().hitBy(this, 100);
			hitDelay = 25;
		}
		if (hitDelay > 0)
			hitDelay--;
		
		lastX = px;
		lastY = py;
	}
	
	public void die() {
		if(parent != null) {
			parent.spawn.remove(this);
		}
		super.die();
	}
}
