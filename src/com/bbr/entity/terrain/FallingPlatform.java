package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.player.Player;

public class FallingPlatform extends Platform {
	public FallingPlatform(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
	}
	
	public void preDt() {
		super.preDt();
		Player p = container.getPlayer();
		if(this.futureCollidesWith(p, 0, p.getYvel()))
			this.terrainCollidable = true;
		
	}
	
}
