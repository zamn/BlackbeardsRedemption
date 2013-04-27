package com.bbr.enemy;

import java.util.ArrayList;
import java.util.List;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.Entity;

public class SnakeSpawner extends Enemy {
	float StartX, StartY;
	long startTime;
	List<Entity> spawn = new ArrayList<Entity>();
	
	public SnakeSpawner(Zone container, float xpos, float ypos) {
		super(container, xpos, ypos);
		this.StartX = this.px;
		this.StartY = this.py;
		this.startTime = System.nanoTime() - 10000000000l; //10 seconds
	}
	
	public int getBaseHealth() { return 200; }
	
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
				container.addEntity(s);
				spawn.add(s);
			}
		}
	}
}
