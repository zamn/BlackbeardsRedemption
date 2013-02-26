package com.bbr.core;

// explosions deal more damage to units in center than units near its edge
// graphics ought to be circle shaped to match damage calculation
// TODO add decay duration?
public class Explosion extends Projectile {
	protected int maxSize; // grows? up to this size
	protected int maxDmg; // maximum damage possible
	//
	public Explosion(Entity owner, float xcenter, float ycenter,
			int maxSize, int maxDmg) {
		super(owner, xcenter - maxSize/2, ycenter - maxSize/2);
		//
		addAttribute(Projectile.ATT_PIERCE); // all explosions are multitarget
		this.maxSize = maxSize;
		this.maxDmg = maxDmg; // damage near center is max
		sx = maxSize;
		sy = maxSize;
	}
	protected void preDt() { // shrink
		resizeInPlace(sx - (maxSize/40)); // shrinks to nothing in 1 second
		if (sx < 0) {
			sx = 0; sy = 0;
		}
	}
	protected void postDt() { // remove explosion if decayed
		if (sx <= 0 || sy <= 0) {
			container.removeEntity(this);
		}
	}
	protected void hit(Entity collided) {
		adjustDamage(collided); // first adjust damage
		super.hit(collided); // then no need to reimplement hit code
	}
	protected void resizeInPlace(int newSize) { // doesn't change center pos
		float xc = px + sx/2; float yc = py + sy/2;
		sx = newSize; sy = newSize;
		px = xc - newSize/2;
		py = yc - newSize/2;
	}
	// lazy algo: check distance from center
	//   damage may seem weird when comparing large & small fliers
	// ex: If the center of the flyer is on one of the four corners
	// TODO better algo that checks for max damage possible,
	//    making large fliers take more reasonable damage
	//  -OR-
	//  make it so it checks the area that is being hit,
	//   and adjust damage so that larger fliers can take
	//   beyond max damage
	protected void adjustDamage(Entity collided) {
		double exc = px + sx/2; // explosion x center
		double eyc = py + sy/2; // explosion y center
		double cxc = collided.getXpos() + collided.getXsize() / 2;
		double cyc = collided.getYpos() + collided.getYsize() / 2;
		//
		double dist = Math.pow(exc - cxc, 2) + Math.pow(eyc - cyc, 2);
		dist = Math.sqrt(dist);
		double maxDist = sx/2; // sx should equal sy
		// damage scales linearly with distance
		damage = (int)(maxDmg * (maxDist - dist)/maxDist);
		if (dist > maxDist) { // circle not rectangle, false positive collision
			// TODO make it not even trigger a call to super.hit()
			damage = 0;
		}
	}
}
