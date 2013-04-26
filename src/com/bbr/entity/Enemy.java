package com.bbr.entity;

import org.newdawn.slick.Graphics;

import com.bbr.core.Zone;

public abstract class Enemy extends Unit {
	public Enemy(Zone zone, float x, float y) {
		super(zone, null, x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		System.out.println("Enemy draw");
		super.draw(g);
	}
}
