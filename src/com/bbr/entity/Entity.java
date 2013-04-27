package com.bbr.entity;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.bbr.core.Sprite;
import com.bbr.core.Zone;
import com.bbr.entity.terrain.FallingPlatform;
import com.bbr.entity.terrain.Ground;
import com.bbr.entity.terrain.Platform;
import com.bbr.entity.terrain.RisingPlatform;
import com.bbr.resource.Art;
import com.bbr.resource.Settings;
import com.bbr.resource.Tuple;
import com.bbr.resource.Utility;

// represents a game entity with position and image
public abstract class Entity {
	public static final int TERMINAL_VELOCITY = 10; // Max due to gravity
		
	protected static final Random rand = new Random(System.currentTimeMillis());
	// Spatial
	protected Zone container;
	protected int sx, sy; // use setters to change, hitbox derives from this
	protected float px, py; // use setters to change, hitbox derives from this
	protected float vx=0, vy=0;
	protected Rectangle2D.Float hitbox = new Rectangle2D.Float(); // derived from px,py sx,sy
	protected boolean terrainCollidable = true;
	protected boolean onPlatform = false;
	// Graphical
	protected Sprite sprite;
	protected boolean flipHorizontal = false; // facing right by default
	protected boolean tiledHorizontally = false, tiledVertically = false;
	protected String type = "rawr";

	public Entity(Zone container, String type, float xpos, float ypos) {
		this.container = container;
		this.type = type;
		setXpos(xpos);
		setYpos(ypos);
		loadSprite();
		autoResize();
	}

	private void loadSprite() {
		if (type != null && !type.equals("")) {
			sprite = Art.getSprite(this, type);
			if (sprite == null)
				System.err.println("Cannot find specified sprite for: " + this);
		}
		else {
			sprite = Art.getSprite(this);
		}
	}
	protected void autoResize() { // resize to match image size
		if (sprite == null) return;
		autoResize(sprite.getFrame());
	}
	protected void autoResize(Image frame) { // resize to match image size
		setXsize(frame.getWidth());
		setYsize(frame.getHeight());
	}

	protected Image getFrameToDraw() { // override to draw different frames
		return sprite.getFrame();
	}
	public void draw(Graphics g) {
		Image toDraw = getFrameToDraw();
		int imageWidth = toDraw.getWidth();
		int imageHeight = toDraw.getHeight();
		if (tiledHorizontally || tiledVertically) {
			imageWidth = imageWidth > sx ? sx : imageWidth;
			imageHeight = imageHeight > sy ? sy : imageHeight;
			//goes through and draws all ground sprites until it reaches the necessary width
			int cropWidth, cropHeight; // for cropping
			int drawWidth, drawHeight; // for cropping
			for (int i = 0; i < sx/imageWidth + (sx%imageWidth==0 ? 0 : 1); i++) {
				for (int j = 0; j < sy/imageHeight + (sy%imageHeight==0 ? 0 : 1); j++) {
					cropWidth = imageWidth; drawWidth = imageWidth;
					cropHeight = imageHeight; drawHeight = imageHeight;
					if (i == sx/imageWidth && sx%imageWidth != 0) {
						drawWidth = sx%imageWidth; // horizontal leftover
						if (tiledHorizontally) {
							cropWidth = drawWidth;
						}
					}
					if (j == sy/imageHeight && sy%imageHeight != 0) {
						drawHeight = sy%imageHeight; // vertical leftover
						if (tiledVertically) {
							cropHeight = drawHeight;
						}
					}

					toDraw.draw(px+imageWidth*(i)-this.container.getXscroll(),
							py+imageHeight*(j)-this.container.getYscroll(),
							px+imageWidth*(i)-this.container.getXscroll()+drawWidth,
							py+imageHeight*(j)-this.container.getYscroll()+drawHeight,
							0, 0, cropWidth, cropHeight);
				}
			}
		} else { // sprite flipping and offsets added for non-tiling
			float offsetX = 0, offsetY = 0;
			int drawSizeX = (flipHorizontal?-sx:sx);
			int drawSizeY = sy;

			Tuple<Integer, Integer> offsets = sprite.getOffsets(toDraw);
			if (offsets != null) {
				offsetX = offsets.left;
				offsetY = offsets.right;
				drawSizeX = (flipHorizontal?-imageWidth:imageWidth);
				drawSizeY = imageHeight;
			}
			float drawStartX = px-this.container.getXscroll()+(flipHorizontal?sx:0)+offsetX;
			float drawStartY = py-this.container.getYscroll()+offsetY;
			
			toDraw.draw(drawStartX, drawStartY, drawSizeX, drawSizeY);
//			toDraw.draw(px-this.container.getXscroll()+(flipHorizontal?sx:0),
//					py-this.container.getYscroll(), (flipHorizontal?-sx:sx), sy);
		}
		if (Settings.valueBoolean("showHitbox")) { // shows entity hitbox, not frame drawbox
			g.setColor(Color.red);
			g.drawRect(px-this.container.getXscroll(), py-this.container.getYscroll(), sx, sy);
		}
	}
	protected void preDt() { }
	public void dt() {	
		/*if ((this instanceof Ground || this instanceof Platform) && !(this instanceof FallingPlatform))
			return;*/
		preDt();
		if (vx < 0) {
			flipHorizontal = true;
		} else if (vx > 0) {
			flipHorizontal = false;
		}
		setXpos(px + vx);
		if(container.collidesWithRightOf(this) != null){
			Utility.log("Collider Right: "+container.collidesWithRightOf(this).getXpos()+" Player: "+(this.getXpos()+this.getXsize()));
			this.setXpos(container.collidesWithRightOf(this).getXpos() - this.getXsize());
		}
		if(container.collidesWithLeftOf(this) != null){
			Utility.log("Collider Left: "+(container.collidesWithLeftOf(this).getXpos() + container.collidesWithLeftOf(this).getXsize())+" Player: "+this.getXpos());
			this.setXpos(container.collidesWithLeftOf(this).getXpos() + container.collidesWithLeftOf(this).getXsize());
		}
		
		
		// acceleration due to gravity
		else if (terrainCollidable && vy < TERMINAL_VELOCITY){
			vy++;
		}
		setYpos(py + vy);
		// prevent falling through platforms
		onPlatform = (container.collidesWithBottomOf(this) != null);
		if (container.collidesWithBottomOf(this) != null){
			vy = 0;
			//Utility.log("Collider Bottom: "+container.collidesWithBottomOf(this).getYpos() +" Player: "+this.getYpos()+this.getYsize());
			this.setYpos(container.collidesWithBottomOf(this).getYpos() - this.getYsize());
		}
		if (container.collidesWithTopOf(this) != null){
			vy = 0;
			Utility.log("Collider Top: "+container.collidesWithTopOf(this).getYpos() + container.collidesWithTopOf(this).getYsize()+" Player: "+this.getYpos());
			this.setYpos(container.collidesWithTopOf(this).getYpos() + container.collidesWithTopOf(this).getYsize());
		}
		
		if(this.toString().equals("Pirate")) {
			//System.out.println("Original x " + getXpos());
		}
		
		postDt();
	}
	protected void postDt() { }

	// Rectangle collision check
	public boolean collidesWith(Entity other) {
		return hitbox.intersects(other.hitbox);
	}
	public boolean futureCollidesWith(Entity other, float xmod, float ymod) {
		Rectangle2D.Float temp = (Float) other.hitbox.clone();
		temp.setFrame(temp.getX() + xmod, temp.getY()+ymod, temp.getWidth(), temp.getHeight());
		return temp.intersects(this.hitbox);
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
