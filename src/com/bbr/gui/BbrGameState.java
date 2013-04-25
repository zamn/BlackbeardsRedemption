package com.bbr.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

// Provides helper methods
// State Id should be the corresponding constant in BlackbeardsRedemption
public abstract class BbrGameState extends BasicGameState {
	protected int stateId = -1; // negative ids are invalid
	@Override
	public int getID() { return stateId; }
	
	public BbrGameState(int stateId) 
			throws IllegalArgumentException {
		if(stateId < 0) {
			throw new IllegalArgumentException(
					"State ID must be non-negative.");
		}
		this.stateId = stateId;
	}

	protected List<Button> buttons = new ArrayList<Button>();
	
	@Override
	public void mousePressed(int button, int mouseX, int mouseY) {
		for(Button b : buttons) {
			if(b.checkClick(mouseX, mouseY)) {
				try {
					buttonClicked(b);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// no-op optional override instead of 
	//ButtonListener interface as design choice
	public void buttonClicked(Button b) throws SlickException { }
	
	public void drawButtons(Graphics g) {
		for (Button b : buttons) {
			b.draw(g);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
			throws SlickException {
		drawButtons(g);
	}
}
