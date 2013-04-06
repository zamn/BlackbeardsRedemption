package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;

public class Spike extends Entity{

	public Spike(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		// TODO Auto-generated constructor stub
		terrainCollidable = false;
		tiledHorizontally = true;
	}
	
	
	//basically whenever the player collides with these he dies
	public void preDt() {
		if (container.getPlayer().collidesWith(this)) 
			container.getPlayer().hitBy(this, container.getPlayer().getHealth());
	}

}
