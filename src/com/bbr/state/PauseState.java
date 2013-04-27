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
import com.bbr.resource.Settings;

public class PauseState extends BbrGameState {
	private static final int TITLE_X = 150, TITLE_Y = 50;

	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 400;
	private static final int BUTTON_X = 
			Settings.valueInt("windowWidth") / 2 - BUTTON_WIDTH / 2;
	private static final int BUTTON_Y = 300;
	
	private static final int START_X = BUTTON_X, START_Y = BUTTON_Y;

	private static final int EXIT_X = BUTTON_X, EXIT_Y = BUTTON_Y
			+ BUTTON_HEIGHT;
	
	protected Image imageTitle;

	protected Button resumeButton;
	protected Button resumeButton_m;
	protected Button exitButton;
	protected Button exitButton_m;

	protected boolean goingToStart = false;
	protected boolean goingToExit = false;
	private int mouseX = Integer.MIN_VALUE, mouseY = Integer.MIN_VALUE;

	public PauseState() throws SlickException {
		super(BlackbeardsRedemption.States.PAUSE.ordinal());
		imageTitle = Art.getImage("TitleImage");

		resumeButton = new Button(Art.getImage("StartButton"), START_X, START_Y);
		buttons.add(resumeButton);
		
		exitButton = new Button(Art.getImage("ExitButton"), EXIT_X, EXIT_Y);
		buttons.add(exitButton);
		
		resumeButton_m = new Button(Art.getImage("StartButton_mouse"), 
				START_X, START_Y);
		
		exitButton_m = new Button(Art.getImage("ExitButton_mouse"), 
				EXIT_X, EXIT_Y);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);

		imageTitle.draw(TITLE_X, TITLE_Y);
		
		if(mouseX > BUTTON_X && mouseX < BUTTON_X + BUTTON_WIDTH) {
			if(mouseY > START_Y && mouseY < START_Y + BUTTON_HEIGHT) {
				resumeButton_m.draw(g);
			}
			if(mouseY > EXIT_Y && mouseY < EXIT_Y + BUTTON_HEIGHT) {
				exitButton_m.draw(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (goingToStart) {
			goingToStart = false;
			GameplayState.getPlayer().resetKeys();
			sbg.enterState(BlackbeardsRedemption.States.GAME.ordinal());
		}
		
		if(goingToExit) {
			gc.exit();
		}
	}

	@Override
	public void buttonClicked(Button b) throws SlickException {
		if (b == resumeButton) {
			goingToStart = true;
		}
		if (b == exitButton) {
			goingToExit = true;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oly, int newx, int newy) {
		mouseX = newx;
		mouseY = newy;
	}
}
