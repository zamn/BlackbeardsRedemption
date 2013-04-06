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

	public SenorRat(Zone zone, float x, float y, int defaultHealth) {
		super(zone, x, y, defaultHealth);
		vx = 1;
		terrainCollidable = false;
		rand = new Random();
	}
	
	public void preDt(){
		vy = (float)(Math.sin(2*Math.PI * dip / dipMax));
		dip++;
		if (dip > dipMax) {
			dip = 0;
			vx = -vx;
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
		
	}
	
	private void throwDagger(){
		Projectile dagger = new Missile(this, px+sx/2 - 5, py - 20);
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
