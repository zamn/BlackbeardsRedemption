package com.bbr.entity.terrain;

import com.bbr.core.Zone;
import com.bbr.entity.player.Player;

public class RisingPlatform extends Platform {
	boolean rising = false;
	
	public RisingPlatform(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		this.terrainCollidable = true;
	}
	
	public void preDt() {
		super.preDt();
		Player p = container.getPlayer();
		if(this.futureCollidesWith(p, 0, (float)0.5)) {
			rising = true;
			this.terrainCollidable = false;
			this.setYvel(-1);
			//this.vy -= 4;
		}
		else if(rising && !(this.futureCollidesWith(p, 0, p.getYvel()))) {
			rising = false;
			this.terrainCollidable = true;
			this.setYvel(0);
		}
		if(rising && this.getYpos() <= 418) {
			this.setYvel(0);
		}
	}
	
	public boolean getRising() { return rising; }
}
