package com.bbr.core;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.bbr.resource.Art;
import com.bbr.resource.Utility;

// represents a game entity with position and image
public abstract class Flyer {
	private static final String MISSING_IMAGE = ".noimage";
	protected static final Random rand = new Random(System.currentTimeMillis());
	//
	protected Zone container;
	protected int sx, sy;
	protected float px, py;
	protected float vx=0, vy=0;
	// Graphical
	protected Image image;
	protected boolean flipHorizontal = false;
	protected boolean tiledHorizontally = false, tiledVertically = false;
	//
	public Flyer(Zone container, float xpos, float ypos) {
		this.container = container;
		px = xpos;
		py = ypos;
		loadSprite();
		autoResize();
	}
	////////////////////////////////////////
	//               Sprite               //
	////////////////////////////////////////
	private void loadSprite() {
		image = Art.getImage(this);
	}
	protected void autoResize() { // resize to match image size
		if (image == null) return;
		sx = image.getWidth();
		sy = image.getHeight();
	}
	///////////////////////////////////////////////
	//               Vital Methods               //
	///////////////////////////////////////////////
	protected Image getFrameToDraw() { // override to draw different frames
		return image;
	}
	public void draw(Graphics g) {
		Image toDraw = getFrameToDraw();
		g.drawImage(toDraw, (px-this.container.getXscroll()+(flipHorizontal?sx:0)),
				(py-this.container.getYscroll()));
	}
	protected void preDt() { }
	public void dt() {
		preDt();
		px += vx;
		py += vy;
		postDt();
	}
	protected void postDt() { }

	public boolean collidesWith(Flyer other) {
		if (((other.px<=this.px+this.sx&&other.px+other.sx>=this.px+this.sx)||
				(other.px<=this.px&&other.px+other.sx>=this.px)||
				(other.px>=this.px&&other.px+other.sx<=this.px+this.sx))&&
				((other.py<=this.py&&other.py+other.sy>=this.py)||
						(other.py>=this.py&&other.py+other.sy<=this.py+this.sy))) {
			return true;
		}
		return false;
	}
	public void hitBy(Flyer attacker, int damage) { }

	public Zone getZone() { return container; }
	public int getXsize() { return sx; }
	public int getYsize() { return sy; }
	public double getXpos() { return px; }
	public double getYpos() { return py; }
	public double getXvel() { return vx; }
	public double getYvel() { return vy; }

	public void setZone(Zone zone) { container = zone; }
	public void setXsize(int newxsize) { sx = newxsize; }
	public void setYsize(int newysize) { sy = newysize; }
	public void setXpos(float newxpos) { px = newxpos; }
	public void setYpos(float newypos) { py = newypos; }
	public void setXvel(float newxvel) { vx = newxvel; }
	public void setYvel(float newyvel) { vy = newyvel; }

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
