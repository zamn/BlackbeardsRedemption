package com.bbr.entity.particle;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

import com.bbr.core.Zone;
import com.bbr.entity.Entity;
import com.bbr.resource.Settings;

/**
 * @todo Fill in Javadoc
 *
 * @version 0.8 / 26 April 2013
 */
public class Particle extends Entity {
	private boolean active = false;
	private Point p;
	private int age;
	private int lifespan;
	private int xVel, yVel;

	public Particle(Zone z, Point p, int lifespan, int xVel, int yVel) {
		super(z, null, (int)(p.getX()), (int)(p.getY()));
		this.p = p;
		this.xVel = xVel;
		this.yVel = yVel;
		this.lifespan = lifespan;
	}

	public void activate() {
		active = true;
		age = 0;
	}

	public void check() {
		Dimension screenSize = new Dimension(Settings.valueInt("windowWidth"), Settings.valueInt("windowHeight"));
		if(age > lifespan || p.getX() < 0 || p.getY() < 0 || p.getY() > screenSize.height) {
			active = false;
		}
	}

	public void draw(Graphics g) {
		if (active) {
			super.draw(g);
			age++;
			check();
		}
	}

	public void preDt() {
		if(isActive()) {
			vx += xVel;
			vy += yVel;		
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setX(float x) {
		p.setX(x);
	}

	public void setY(float y) {
		p.setY(y);
	}

	public boolean isActive() {
		return active;
	}
}
