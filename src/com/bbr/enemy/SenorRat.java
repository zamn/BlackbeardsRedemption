package com.bbr.enemy;

import java.util.Random;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.projectile.Missile;
import com.bbr.entity.projectile.Projectile;
import com.bbr.resource.Settings;

public class SenorRat extends Enemy {
	
	protected int dip = 0;
	protected int dipMax = Settings.valueInt("fps")*5;
	protected Random rand;
	protected float startx;
	protected int counter;

	public SenorRat(Zone zone, float x, float y, int defaultHealth) {
		super(zone, x, y, defaultHealth);
		vx = 2;
		terrainCollidable = false;
		rand = new Random();
		startx = x;
		counter = 0;
	}
	
	public void preDt(){
		counter ++;
		if(Math.abs(startx-px) > 400)
			vx = -vx;
		if(counter > 60){
			counter = 0;
			attack();
		}
	}
	
	public void attack(){
		int r = rand.nextInt(4);
		if(r == 0)
			stab();
		else if(r == 1)
			throwDagger();
		else if(r == 2)
			hurlInsult();
		else
			spit();
	}

	private void stab(){
		Projectile dagger = new Missile(this, px+sx/2 - 5, py - 20);
		container.addEntity(dagger);
	}
	
	private void throwDagger(){
		Projectile dagger = new Missile(this, px+sx/2 - 5, py - 20);
		dagger.multDmg(2);
		container.addEntity(dagger);
	}
	
	private void hurlInsult(){
		Projectile insult = new Missile(this, px+sx/2 - 5, py - 20);
		container.addEntity(insult);
	}
	
	private void spit(){
		Projectile spit = new Missile(this, px+sx/2 - 5, py - 20);
		container.addEntity(spit);
	}
	
	
}
