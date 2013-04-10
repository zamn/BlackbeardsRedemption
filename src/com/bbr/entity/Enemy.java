package com.bbr.entity;

import com.bbr.core.Zone;

public abstract class Enemy extends Unit {
	public Enemy(Zone zone, float x, float y) {
		super(zone, x, y);
	}
}
