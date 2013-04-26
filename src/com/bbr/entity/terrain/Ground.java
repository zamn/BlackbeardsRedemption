package com.bbr.entity.terrain;

import org.newdawn.slick.Graphics;

import com.bbr.core.Zone;
import com.bbr.resource.Utility;


public class Ground extends Platform {

	public Ground(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		terrainCollidable = false;
		tiledHorizontally = true;
	}
	
	@Override
	public void draw(Graphics g) {
		Utility.log("Ground draw");
		super.draw(g);
	}
}
