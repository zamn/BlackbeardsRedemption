package com.bbr.enemy;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;

public class SnakeSpawnerR extends SnakeSpawner {
	public SnakeSpawnerR(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
	}
	
	public void preDt() {
		if(this.StartX != this.px) this.px = this.StartX;
		if(this.StartY != this.py) this.py = this.StartY;
		if((System.nanoTime() - this.startTime) >= 10000000000l) { //10 seconds
			int numSnakes = 0;
			for(Entity e : spawn) {
				if(this.container.hasEntity(e)) {
					numSnakes++;	
				}
				else {
					spawn.remove(e);
				}
			}
			if(numSnakes < 3) {
				this.startTime = System.nanoTime();
				Snake s = new Snake(this.container, this.px, this.py, this);
				s.setXvel(-1 * s.getXvel());
				container.addEntity(s);
				spawn.add(s);
			}
		}
	}
}
