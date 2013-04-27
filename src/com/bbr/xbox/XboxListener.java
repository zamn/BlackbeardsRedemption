package com.bbr.xbox;

import org.newdawn.slick.Input;

import com.bbr.entity.player.Player;
import com.bbr.entity.player.Player.Action;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class XboxListener extends Thread {

	private Controller joystick = null;
	private Player p = null;
	private Component[] comps;
	private int xAxis, yAxis, aButton, xButton;

	public static final int NUM_COMPASS_DIRS = 9;
	public static final int NW = 0;
	public static final int NORTH = 1;
	public static final int NE = 2;
	public static final int WEST = 3;
	public static final int NONE = 4;
	public static final int EAST = 5;
	public static final int SW = 6;
	public static final int SOUTH = 7;
	public static final int SE = 8;
	private static final int DELAY = 50;
	private static final int JUMP_DELAY = 600;
	private static final int ATTACK_DELAY = 10;

	public XboxListener(Controller joystick, Player p) {
		this.joystick = joystick;
		this.p = p;
		comps = joystick.getComponents();

		xAxis = getIndex(comps, Component.Identifier.Axis.X, "X Axis");
		yAxis = getIndex(comps, Component.Identifier.Axis.Y, "Y Axis");
		aButton = getIndex(comps, Component.Identifier.Button._0, "Button 0");
		xButton = getIndex(comps, Component.Identifier.Button._2, "Button 2");
	}
	
	public void updatePlayer(Player n) {
		p = n;
	}

	public void poll() {
		joystick.poll();
	}

	public int getXYStickDIR() {
		return getCompassDir(xAxis, yAxis);
	}

	public int getCompassDir(int xA, int yA) {
		float xCoord = comps[xA].getPollData();
		float yCoord = comps[yA].getPollData();

		int xc = Math.round(xCoord);
		int yc = Math.round(yCoord);

		if ((yc == -1) && (xc == -1))
			return NW;
		else if ((yc == -1) && (xc == 0))
			return NORTH;
		else if ((yc == -1) && (xc == 1))
			return NE;
		else if ((yc == 0) && (xc == -1))
			return WEST;
		else if ((yc == 0) && (xc == 0))
			return NONE;
		else if ((yc == 0) && (xc == 1))
			return EAST;
		else if ((yc == 1) && (xc == -1))
			return SW;
		else if ((yc == 1) && (xc == 0))
			return SOUTH;
		else if ((yc == 1) && (xc == 1))
			return SE;
		else
			return NONE;
	}

	public boolean buttonPressed(int button) {
		return comps[button].getPollData() == 1;
	}

	public int getIndex(Component[] comps, Component.Identifier id, String name) {
		for (int i = 0; i < comps.length; i++) {
			if (comps[i].getIdentifier() == id)
				return i;
		}
		return -1;
	}

	public void run() {
		while (true) {
			poll();
			int dir = getCompassDir(xAxis, yAxis);

			if (dir != NONE) {
				boolean xPress = false;
				
				switch (dir) {
				case NONE:
					break;
				case EAST:
				case SE:
					xPress = buttonPressed(xButton);
					if (buttonPressed(xButton))
						p.keyPressed(Input.KEY_X);
					p.keyPressed(Input.KEY_RIGHT);
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					if (buttonPressed(xButton))
						p.keyReleased(Input.KEY_X);
					p.keyReleased(Input.KEY_RIGHT);
					break;
				case NE:
					xPress = buttonPressed(xButton);
					if (xPress)
						p.keyPressed(Input.KEY_X);
					p.keyPressed(Input.KEY_UP);
					p.keyPressed(Input.KEY_RIGHT);
					try {
						Thread.sleep(JUMP_DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					if (xPress)
						p.keyReleased(Input.KEY_X);
					p.keyReleased(Input.KEY_UP);
					p.keyReleased(Input.KEY_RIGHT);
					break;
				case NORTH:
					xPress = buttonPressed(xButton);
					if (xPress)
						p.keyPressed(Input.KEY_X);
					p.keyPressed(Input.KEY_UP);
					try {
						Thread.sleep(JUMP_DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					if (xPress)
						p.keyReleased(Input.KEY_X);
					p.keyReleased(Input.KEY_UP);
					break;
				case NW:
					xPress = buttonPressed(xButton);
					if (xPress)
						p.keyPressed(Input.KEY_X);
					p.keyPressed(Input.KEY_UP);
					p.keyPressed(Input.KEY_LEFT);
					try {
						Thread.sleep(JUMP_DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					if (xPress)
						p.keyReleased(Input.KEY_X);
					p.keyReleased(Input.KEY_UP);
					p.keyReleased(Input.KEY_LEFT);
					break;
				case WEST:
				case SW:
					xPress = buttonPressed(xButton);
					if (xPress)
						p.keyPressed(Input.KEY_X);
					p.keyPressed(Input.KEY_LEFT);
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					if (xPress)
						p.keyReleased(Input.KEY_X);
					p.keyReleased(Input.KEY_LEFT);
					break;
				}
			} else {
				if (buttonPressed(xButton)) {
					p.keyPressed(Input.KEY_X);
					try {
						Thread.sleep(ATTACK_DELAY);
					} catch (InterruptedException e) {
						System.out.println("Oops: " + e);
					}
					p.keyReleased(Input.KEY_X);
				}
				if (buttonPressed(aButton)) {
					p.keyPressed(Input.KEY_R);
					p.keyReleased(Input.KEY_R);
				}
			}
			
		}
	}

}
