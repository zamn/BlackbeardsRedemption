package com.bbr.entity;

import org.newdawn.slick.Graphics;

import com.bbr.core.Zone;
import com.bbr.resource.Utility;

public abstract class Enemy extends Unit {
	public Enemy(Zone zone, float x, float y) {
		super(zone, null, x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		Utility.log("Enemy draw");
		super.draw(g);
	}
}
