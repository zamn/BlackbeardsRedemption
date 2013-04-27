package com.bbr.entity.terrain;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.entity.player.Player;
import com.bbr.entity.projectile.Projectile;
import com.bbr.particle.Particle;
import com.bbr.resource.Settings;

public class FallingPlatform extends Platform {
	private static final int PARTICLE_DENSITY = 50;
	private Random rand = new Random();
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	
	protected int fallingCount = 0;
	protected boolean falling = false;
	
	public FallingPlatform(Zone container, String type, float xpos, float ypos) {
		super(container, type, xpos, ypos);
		for(int i = 0; i < PARTICLE_DENSITY; i++) {
			particles.add(new Particle(getZone(), 
						new Point(getXpos() + getXsize() / 2, getYpos() + getYsize() / 2), rand.nextInt(10),
						(int)(Math.pow(-1, rand.nextInt(2))) * (rand.nextInt(2) + 1),
						(int)(Math.pow(-1, rand.nextInt(2))) * (rand.nextInt(2) + 1)));
		}
	}

	@Override
	public Image getFrameToDraw() {
		if (falling) {
			return sprite.getFrame("danger");
		}
		return super.getFrameToDraw();
	}
	
	@Override
	public void preDt() {
		super.preDt();
		Player p = container.getPlayer();
		Entity e = container.getCollided(this);

				
		if(!falling  && (this.futureCollidesWith(p, 0, p.getYvel()) || e instanceof Projectile)) {
			falling = true;
			for(Particle particle : particles) {
				particle.activate();
				getZone().addEntity(particle);
			}
		}
		if (falling)
			fallingCount++;
		if (fallingCount > Settings.valueInt("fps")/2)
			this.terrainCollidable = true;
	}	
}
