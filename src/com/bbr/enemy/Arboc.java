package com.bbr.enemy;

import java.util.Random;

import com.bbr.core.Zone;
import com.bbr.entity.Enemy;
import com.bbr.entity.projectile.Projectile;
import com.bbr.entity.projectile.SwordAttack;

public class Arboc extends Enemy {
	@Override
	public int getBaseHealth() { return 200; }

	protected int level;
	protected Random rand;
	protected int counter;
	protected float startX;
	protected int hitDelay;
	protected float startY;
	
	public Arboc(Zone zone, float x, float y) {
		super(zone, x, y);
		vx = 10;
		vy = 5;
		terrainCollidable = false;
		rand = new Random();
		level = 1;
		counter = 0;
		startX = x;
		startY = y;
		hitDelay = 25;
	}
	
	@Override
	public void preDt(){
		counter ++;
		if(Math.abs(startX-px) > 200)
			vx = -vx;
		if(counter > 40){
			attack();
			counter = 0;
		}
		
		//move up and down
		if (Math.abs(startY-py) > 200)
			vy = -vy;
		
		//on collision, damage the player
		if (container.getPlayer().collidesWith(this) && hitDelay <=0) {
			container.getPlayer().hitBy(this, 100);
			hitDelay = 25;
		}
		if (hitDelay > 0)
			hitDelay--;
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
		Projectile venom = new SwordAttack(this, px+sx/2 - 5, py - 20);
		container.addEntity(venom);
	}
	
	private void tailWhip(){
		
	}

	private void bite(){
		
	}
	
	private void lasers(){
		Projectile laser = new SwordAttack(this, px+sx/2 - 5, py - 20);
		laser.multDmg(2);
		container.addEntity(laser);
	}
	
	private void stare(){
		Projectile stare = new SwordAttack(this, px+sx/2 - 5, py - 20);
		container.addEntity(stare);
	}
	
}
