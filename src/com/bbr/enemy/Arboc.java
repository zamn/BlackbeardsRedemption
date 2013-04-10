package com.bbr.enemy;

import java.util.Random;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.projectile.Missile;
import com.bbr.entity.projectile.Projectile;

public class Arboc extends Enemy {
	public int getBaseHealth() { return 2000; }

	protected int level;
	protected Random rand;
	protected int counter;
	protected float startx;
	
	public Arboc(Zone zone, float x, float y) {
		super(zone, x, y);
		vx = 1;
		terrainCollidable = false;
		rand = new Random();
		level = 1;
		counter = 0;
		startx = x;
	}
	
	public void preDt(){
		counter ++;
		if(Math.abs(startx-px) > 200)
			vx = -vx;
		if(counter > 90){
			attack();
			counter = 0;
		}
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void attack(){
		if(level == 1)
			fightOne();
		else if(level == 2)
			fightTwo();
		else
			fightThree();
	}
	
	//Level one abilities.
	private void fightOne(){
		int r = rand.nextInt(3);
		if(r == 0)
			venom();
		else if(r == 1)
			lasers();
		//else if(r == 2)
		//	bite();
	}
	
	//Level two abilities.
	private void fightTwo(){
		int r = rand.nextInt(4);
		if(r == 0)
			venom();
		else if(r == 1)
			tailWhip();
		else if(r == 2)
			bite();
		else if(r == 3)
			lasers();
	}
	
	//Level three abilities.
	private void fightThree(){
		int r = rand.nextInt(5);
		if(r == 0)
			venom();
		else if(r == 1)
			tailWhip();
		else if(r == 2)
			bite();
		else if(r == 3)
			lasers();
		else if(r == 4)
			stare();
	}
	
	//Spits venom.
	private void venom(){
		Projectile venom = new Missile(this, px+sx/2 - 5, py - 20);
		container.addEntity(venom);
	}
	
	private void tailWhip(){
		
	}

	private void bite(){
		
	}
	
	private void lasers(){
		Projectile laser = new Missile(this, px+sx/2 - 5, py - 20);
		laser.multDmg(2);
		container.addEntity(laser);
	}
	
	private void stare(){
		Projectile stare = new Missile(this, px+sx/2 - 5, py - 20);
		container.addEntity(stare);
	}
	
}
