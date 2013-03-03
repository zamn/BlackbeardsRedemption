package com.bbr.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.gui.BbrGameState;
import com.bbr.gui.Button;
import com.bbr.main.BlackbeardsRedemption;
import com.bbr.resource.Art;

public class MainMenuState extends BbrGameState {
	protected Image imageTitle;
	protected Button buttonStart;
	protected boolean goingToStart = false;

	public MainMenuState() throws SlickException {
		super(BlackbeardsRedemption.MAINMENUSTATE);
		imageTitle = Art.getImage("TitleImage");
		buttonStart = new Button(Art.getImage("StartButton"), 100, 400);
		buttons.add(buttonStart);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { }

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);
		imageTitle.draw(150, 50);
		g.drawString("Click the bottom pirate to start!", 100, 500);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (goingToStart) {
			goingToStart = false;
			sbg.enterState(BlackbeardsRedemption.GAMEPLAYSTATE);
		}
	}

	public void buttonClicked(Button b) throws SlickException {
		if (b == buttonStart) {
			goingToStart = true;
		}
	}
}
