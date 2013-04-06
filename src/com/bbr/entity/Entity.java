package com.bbr.entity;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.resource.Art;
import com.bbr.resource.Settings;

// represents a game entity with position and image
public abstract class Entity {
	public static final int TERMINAL_VELOCITY = 10; // Gravity

	protected static final Random rand = new Random(System.currentTimeMillis());
	// Spatial
	protected Zone container;
	protected int sx, sy;
	protected float px, py;
	protected float vx=0, vy=0;
	protected Rectangle2D.Float hitbox = new Rectangle2D.Float(); // derived from px,py sx,sy
	protected boolean terrainCollidable = true;
	protected boolean onPlatform = false;
	// Graphical
	protected Image image;
	protected boolean flipHorizontal = false; // facing right by default
	protected boolean tiledHorizontally = false, tiledVertically = false;

	public Entity(Zone container, float xpos, float ypos) {
		this.container = container;
		setXpos(xpos);
		setYpos(ypos);
		loadSprite();
		autoResize();
	}

	private void loadSprite() {
		image = Art.getImage(this);
	}
	protected void autoResize() { // resize to match image size
		if (image == null) return;
		setXsize(image.getWidth());
		setYsize(image.getHeight());
	}

	protected Image getFrameToDraw() { // override to draw different frames
		return image;
	}
	public void draw(Graphics g) {
		Image toDraw = getFrameToDraw();
		//toDraw.draw(px-this.container.getXscroll(), py-this.container.getYscroll(), sx, sy);
		if (tiledHorizontally || tiledVertically) {
			int width = toDraw.getWidth();
			width = width > sx ? sx : width;
			int height = toDraw.getHeight();
			height = height > sy ? sy : height;
			//goes through and draws all ground sprites until it reaches the necessary width
			int cropWidth, cropHeight; // for cropping
			int drawWidth, drawHeight; // for cropping
			for (int i = 0; i < sx/width + (sx%width==0 ? 0 : 1); i++) {
				for (int j = 0; j < sy/height + (sy%height==0 ? 0 : 1); j++) {
					cropWidth = width; drawWidth = width;
					cropHeight = height; drawHeight = height;
					if (i == sx/width && sx%width != 0) {
						drawWidth = sx%width; // horizontal leftover
						if (tiledHorizontally) {
							cropWidth = drawWidth;
						}
					}
					if (j == sy/height && sy%height != 0) {
						drawHeight = sy%height; // vertical leftover
						if (tiledVertically) {
							cropHeight = drawHeight;
						}
					}

					toDraw.draw(px+width*(i)-this.container.getXscroll(),
							py+height*(j)-this.container.getYscroll(),
							px+width*(i)-this.container.getXscroll()+drawWidth,
							py+height*(j)-this.container.getYscroll()+drawHeight,
							0, 0, cropWidth, cropHeight);
				}
			}
		} else { // sprite flipping added for non-tiling
			toDraw.draw(px-this.container.getXscroll()+(flipHorizontal?sx:0),
					py-this.container.getYscroll(), (flipHorizontal?-sx:sx), sy);
		}
		if (Settings.valueBoolean("showHitbox")) {
			g.setColor(Color.red);
			g.drawRect(px-this.container.getXscroll(), py-this.container.getYscroll(), sx, sy);
		}
	}
	protected void preDt() { }
	public void dt() {
		preDt();
		if (vx < 0) {
			flipHorizontal = true;
		} else if (vx > 0) {
			flipHorizontal = false;
		}
		setXpos(px + vx);
		setYpos(py + vy);

		onPlatform = container.getPlatformBelow(this) != null;
		if (onPlatform) vy = 0;
		if (terrainCollidable && !onPlatform && vy < TERMINAL_VELOCITY) vy++; // TODO gravity
		postDt();
	}
	protected void postDt() { }

	// Rectangle collision check
	public boolean collidesWith(Entity other) {
//		if (((other.px<=this.px+this.sx&&other.px+other.sx>=this.px+this.sx)||
//				(other.px<=this.px&&other.px+other.sx>=this.px)||
//				(other.px>=this.px&&other.px+other.sx<=this.px+this.sx))&&
//				((other.py<=this.py&&other.py+other.sy>=this.py)||
//						(other.py>=this.py&&other.py+other.sy<=this.py+this.sy))) {
		return hitbox.intersects(other.hitbox);
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
	public boolean isOnPlatform() { return onPlatform; }
	public boolean isFacingRight() { return !flipHorizontal; }

	public void setZone(Zone zone) { container = zone; }
	public void setXsize(int newxsize) { sx = newxsize; hitbox.width = sx; }
	public void setYsize(int newysize) { sy = newysize; hitbox.height = sy; }
	public void setXpos(float newxpos) { px = newxpos; hitbox.x = px; }
	public void setYpos(float newypos) { py = newypos; hitbox.y = py; }
	public void setXvel(float newxvel) { vx = newxvel; }
	public void setYvel(float newyvel) { vy = newyvel; }
	public void setTerrainCollidable(boolean collidability) { terrainCollidable = collidability; }
	public void setOnPlatform(boolean newState) { onPlatform = newState; }

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
