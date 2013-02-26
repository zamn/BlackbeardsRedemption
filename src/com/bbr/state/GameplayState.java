package com.bbr.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.gui.Button;
import com.bbr.resource.Art;

public class GameplayState extends BasicGameState {
	protected int stateID = -1;

	protected boolean lost = false;
	protected Image gameOver, gameOverReturn;

	public int getID() { return stateID; }
	public GameplayState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.getGraphics().setBackground(new Color(128,128,128));
		gameOver = new Image("res/red-pirate-small.png");
		gameOverReturn = new Image("res/red-pirate-small.png");
		Art.load();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	}

	public void keyPressed(int key, char c) {
	}

	public void mousePressed(int button, int mouseX, int mouseY) {
	}

	public void buttonClicked(Button b) throws SlickException {
	}
}
