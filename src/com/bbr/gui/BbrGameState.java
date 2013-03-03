package com.bbr.gui;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;

// Provides helper methods
public abstract class BbrGameState extends BasicGameState {
	protected int stateId = -1; // negative ids are invalid

	public int getID() { return stateId; }
	public BbrGameState(int stateId) throws SlickException, IllegalArgumentException {
		if (stateId < 0) throw new IllegalArgumentException("State ID must be non-negative.");
		this.stateId = stateId;
	}
}
