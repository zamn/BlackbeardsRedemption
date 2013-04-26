package com.bbr.entity.terrain;

import com.bbr.core.Zone;

public class FakeSpike extends Spike {

	public FakeSpike(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
	}
	
	public void preDt() {
		if(this.StartX != this.px) this.px = this.StartX;
		if(this.StartY != this.py) this.py = this.StartY;
	}
}
