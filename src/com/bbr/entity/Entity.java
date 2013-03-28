package com.bbr.entity;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.resource.Art;

// represents a game entity with position and image
public abstract class Entity {
	protected static final Random rand = new Random(System.currentTimeMillis());
	// Spatial
	protected Zone container;
	protected int sx, sy;
	protected float px, py;
	protected float vx=0, vy=0;
	protected boolean terrainCollidable = true;
	// Graphical
	protected Image image;
	protected boolean flipHorizontal = false; // facing right by default
	protected boolean tiledHorizontally = false, tiledVertically = false;

	public Entity(Zone container, float xpos, float ypos) {
		this.container = container;
		px = xpos;
		py = ypos;
		loadSprite();
		autoResize();
	}

	private void loadSprite() {
		image = Art.getImage(this);
	}
	protected void autoResize() { // resize to match image size
		if (image == null) return;
		sx = image.getWidth();
		sy = image.getHeight();
	}

	protected Image getFrameToDraw() { // override to draw different frames
		return image;
	}
	public void draw(Graphics g) {
		Image toDraw = getFrameToDraw();
		//toDraw.draw(px-this.container.getXscroll(), py-this.container.getYscroll(), sx, sy);
		toDraw.draw(px-this.container.getXscroll()+(flipHorizontal?sx:0),
				py-this.container.getYscroll(), (flipHorizontal?-sx:sx), sy);
	}
	protected void preDt() { }
	public void dt() {
		preDt();
		if (vx < 0) {
			flipHorizontal = true;
		} else if (vx > 0) {
			flipHorizontal = false;
		}
		px += vx;
		py += vy;
		postDt();
	}
	protected void postDt() { }

	// Rectangle collision check
	public boolean collidesWith(Entity other) {
		if (((other.px<=this.px+this.sx&&other.px+other.sx>=this.px+this.sx)||
				(other.px<=this.px&&other.px+other.sx>=this.px)||
				(other.px>=this.px&&other.px+other.sx<=this.px+this.sx))&&
				((other.py<=this.py&&other.py+other.sy>=this.py)||
						(other.py>=this.py&&other.py+other.sy<=this.py+this.sy))) {
			return true;
		}
		return false;
	}
	public void hitBy(Entity attacker, int damage) { }

	public Zone getZone() { return container; }
	public int getXsize() { return sx; }
	public int getYsize() { return sy; }
	public float getXpos() { return px; }
	public float getYpos() { return py; }
	public float getXvel() { return vx; }
	public float getYvel() { return vy; }
	public boolean isTerrainCollidable() { return terrainCollidable; }

	public void setZone(Zone zone) { container = zone; }
	public void setXsize(int newxsize) { sx = newxsize; }
	public void setYsize(int newysize) { sy = newysize; }
	public void setXpos(float newxpos) { px = newxpos; }
	public void setYpos(float newypos) { py = newypos; }
	public void setXvel(float newxvel) { vx = newxvel; }
	public void setYvel(float newyvel) { vy = newyvel; }
	public void setTerrainCollidable(boolean collidability) { terrainCollidable = collidability; }

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
