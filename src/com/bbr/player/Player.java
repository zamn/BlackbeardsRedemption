package com.bbr.player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.newdawn.slick.Input;

import com.bbr.core.Zone;
import com.bbr.entity.Unit;

public abstract class Player extends Unit {
	// Scoring
	protected long score = 0;
	// Firing
	protected int fireDelay = 10;
	protected int fireCooldown = 0;
	// Special Ability
	protected int specialDelay = 120;
	protected int specialCooldown = 0;
	// Movement
	protected float moveSpeed = 5;
	protected float jumpSpeed = 15;
	protected double hasteFactor = 0;
	protected int hasteDuration = 0;
	protected double slowFactor = 0;
	protected int slowDuration = 0;
	protected int snareDuration = 0;
	public enum Action {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, ACT_FIRE, ACT_SPECIAL};
	public static final int[] DEFAULT_KEYS = {Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_X, Input.KEY_Z};
	// Controls
	private ArrayList<Action> controlAction = new ArrayList<Action>();
	private ArrayList<Integer> controlKey = new ArrayList<Integer>();
	private ArrayList<Boolean> controlHeld = new ArrayList<Boolean>();
	// Controls Modification
	protected boolean preventMovement = false; // Prevents player from controlling movement
	protected boolean preventFiring = false; // Prevents player from controlling firing

	public Player(Zone container, float xpos, float ypos, int defaultHealth) {
		super(container, xpos, ypos, defaultHealth);
		health = 100;
		for (int i = 0; i < Action.values().length; i++) {
			controlAction.add(Action.values()[i]);
			controlKey.add(DEFAULT_KEYS[i]);
			controlHeld.add(false);
		}
	}
	////////////////////////////////////////////////
	//               Keyboard Input               //
	////////////////////////////////////////////////
	public void keyPressed(int key) {
		Action action = actionOf(key);
		if (action == null) return;
		holdKey(action);
	}
	public void keyReleased(int key) {
		Action action = actionOf(key);
		if (action == null) return;
		releaseKey(action);

		switch (action) {
		case MOVE_UP:
			if (vy < 0) { vy = 0; }
			break;
		case MOVE_DOWN:
			if (vy > 0) { vy = 0; }
			break;
		case MOVE_LEFT:
			if (vx < 0) { vx = 0; }
			break;
		case MOVE_RIGHT:
			if (vx > 0) { vx = 0; }
			break;
		case ACT_FIRE:
			break;
		case ACT_SPECIAL:
			break;
		}
	}
	public void keyTyped(KeyEvent ke) { }

	protected void holdKey(Action action) {
		controlHeld.set(action.ordinal(), true);
	}
	protected void releaseKey(Action action) {
		controlHeld.set(action.ordinal(), false);
	}
	protected boolean keyHeld(Action action) {
		return controlHeld.get(action.ordinal());
	}
	protected Action actionOf(int keyCode) {
		int index = controlKey.indexOf(keyCode);
		if (index != -1) {
			return controlAction.get(index);
		}
		return null;
	}

	protected void preDt() { // handle shooting and movement
		super.preDt();
		System.out.println((container.getPlayer().getHealth()));
		if (!preventFiring) { // also stops firing cooldown
			if (keyHeld(Action.ACT_FIRE)) {
				if (fireCooldown <= 0) {
					fireCooldown = fireDelay;
					fireProjectile();
				}
			}
			if (fireCooldown > 0) fireCooldown--;
		}
		if (keyHeld(Action.ACT_SPECIAL)) {
			if (specialCooldown <= 0) {
				specialCooldown = specialDelay;
				useSpecial();
			}
		}
		if (specialCooldown > 0) specialCooldown--;

		if (!preventMovement) {
			if (keyHeld(Action.MOVE_UP) && onPlatform) { // TODO and not midair
				vy = -jumpSpeed; this.moved();
			}
//			else if (keyHeld(Action.MOVE_DOWN)) {
//				vy = moveSpeed; this.moved();
//			}
			if (keyHeld(Action.MOVE_LEFT)) {
				vx = -moveSpeed; this.moved();
			} else if (keyHeld(Action.MOVE_RIGHT)) {
				vx = moveSpeed; this.moved();
			}

			applyMovementModifiers();
		}
	}
	protected void postDt() {
		// prevent moving out of bounds
		if (px < 0) px = 0;
		if (py < 0) py = 0;
		// haste/slow/snare decay
		if (hasteDuration > 0) hasteDuration--;
		if (slowDuration > 0) slowDuration--;
		if (snareDuration > 0) snareDuration--;
	}
	// apply Haste/Slow/Snare should be applied in that order whenever player tries to move
	protected void applyMovementModifiers() {
		applyHaste();
		applySlow();
		applySnare();
	}
	protected void applyHaste() {
		if (hasteDuration > 0) {
			vx *= hasteFactor;
			vy *= hasteFactor;
		}
	}
	protected void applySlow() {
		if (slowDuration > 0) {
			vx /= slowFactor;
			vy /= slowFactor;
		}
	}
	protected void applySnare() {
		if (snareDuration > 0) { // immobilize player if snared
			vx = 0;
			vy = 0;
		}
	}
	// Player actions
	protected void moved() { } // Called whenever player tries to move
	protected abstract void fireProjectile();
	protected abstract void useSpecial();
	///////////////////////////////////////
	//               Score               //
	///////////////////////////////////////
	public void addScore(long points) { score += points; }
	public void setScore(long points) { score = points; }
	public long getScore() { return score; }
	///////////////////////////////////////
	//               Speed               //
	///////////////////////////////////////
	public void speedUp(double factor, int tickDuration) {
		// TODO allow stacking instead of overwrite
		if (factor == 0) tickDuration = 0; // avoid div by 0
		hasteFactor = factor;
		hasteDuration = tickDuration;
	}
	public void slowDown(double factor, int tickDuration) {
		// TODO allow stacking instead of overwrite
		if (factor == 0) tickDuration = 0; // avoid div by 0
		slowFactor = factor;
		slowDuration = tickDuration;
	}
	public void snare(int tickDuration) { // non-stacking
		snareDuration = Math.max(snareDuration, tickDuration);
	}
}
