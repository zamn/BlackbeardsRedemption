package com.bbr.enemy;

import java.util.Random;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.projectile.SwordAttack;
import com.bbr.entity.projectile.Projectile;

public class SenorRat extends Enemy {
	public int getBaseHealth() { return 2000; }
	
	protected Random rand;
	protected float startX;
	protected int counter;

	public SenorRat(Zone zone, float x, float y) {
		super(zone, x, y);
		vx = 2;
		terrainCollidable = false;
		rand = new Random();
		startX = x;
		counter = 0;
	}
	
	public void preDt(){
		counter ++;
		if(Math.abs(startX-px) > 400)
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
		Projectile dagger = new SwordAttack(this, px+sx/2 - 5, py - 20);
		container.addEntity(dagger);
	}
	
	private void throwDagger(){
		Projectile dagger = new SwordAttack(this, px+sx/2 - 5, py - 20);
		dagger.multDmg(2);
		container.addEntity(dagger);
	}
	
	private void hurlInsult(){
		Projectile insult = new SwordAttack(this, px+sx/2 - 5, py - 20);
		container.addEntity(insult);
	}
	
	private void spit(){
		Projectile spit = new SwordAttack(this, px+sx/2 - 5, py - 20);
		container.addEntity(spit);
	}
	
	
}
