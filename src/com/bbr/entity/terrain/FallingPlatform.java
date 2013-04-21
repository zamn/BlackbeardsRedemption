package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.player.Player;

public class FallingPlatform extends Platform {
	protected int falling_count = 0;
	protected boolean falling = false;
	public FallingPlatform(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
	}
	
	public void preDt() {
		super.preDt();
		Player p = container.getPlayer();
		if(!(falling)  && this.futureCollidesWith(p, 0, p.getYvel()))
			falling = true;
		if (falling)
			falling_count++;
		if (falling_count > 17)
			this.terrainCollidable = true;
		
		
	}
	
}
