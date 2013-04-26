package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;

public class Spike extends Entity{
	float StartX, StartY;
	
	public Spike(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		// TODO Auto-generated constructor stub
		terrainCollidable = false;
		tiledHorizontally = true;
		this.StartX = this.px;
		this.StartY = this.py;
	}
	
	
	//basically whenever the player collides with these he dies
	@Override
	public void preDt() {
		if(this.StartX != this.px) this.px = this.StartX;
		if(this.StartY != this.py) this.py = this.StartY;
		if (container.getPlayer().collidesWith(this)) 
			container.getPlayer().hitBy(this, 100);
	}

}
