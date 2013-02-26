package com.bbr.state;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.gui.Button;
import com.bbr.main.BlackbeardsRedemption;

public class MainMenuState extends BasicGameState {
	protected int stateID = -1;

	protected List<Button> buttons = new ArrayList<Button>();

	protected Image imageTitle;
	protected Button buttonStart;
	protected boolean goingToStart = false;

	public int getID() { return stateID; }
	public MainMenuState(int stateID) throws SlickException {
		this.stateID = stateID;
		imageTitle = new Image("res/red-pirate-small.png");
		buttonStart = new Button(new Image("res/red-pirate-small.png"), 100,
				400);
		buttons.add(buttonStart);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		imageTitle.draw(150, 50);
		buttonStart.draw(g);
		g.drawString("Click the bottom pirate to start!", 100, 500);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (goingToStart) {
			goingToStart = false;
			sbg.enterState(BlackbeardsRedemption.GAMEPLAYSTATE);
		}
	}

	public void mousePressed(int button, int mouseX, int mouseY) {
		for (Button b : buttons) {
			if (b.checkClick(mouseX, mouseY)) {
				try {
					buttonClicked(b);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void buttonClicked(Button b) throws SlickException {
		if (b == buttonStart) {
			goingToStart = true;
		}
	}
}
