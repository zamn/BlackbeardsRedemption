package com.bbr.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.gui.BbrGameState;
import com.bbr.gui.Button;
import com.bbr.main.BlackbeardsRedemption;
import com.bbr.resource.Art;
import com.bbr.resource.Settings;
import com.bbr.resource.Song;

public class MainMenuState extends BbrGameState {
	private static final int TITLE_X = 150, TITLE_Y = 50;

	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 400;
	private static final int BUTTON_X = 
			Settings.valueInt("windowWidth") / 2 - BUTTON_WIDTH / 2;
	private static final int BUTTON_Y = 300;
	
	private static final int START_X = BUTTON_X, START_Y = BUTTON_Y;

	private static final int HELP_X = BUTTON_X, HELP_Y = BUTTON_Y
			+ BUTTON_HEIGHT;

	private static final int SETTINGS_X = BUTTON_X, SETTINGS_Y = BUTTON_Y
			+ BUTTON_HEIGHT * 2;

	private static final int CREDITS_X = BUTTON_X, CREDITS_Y = BUTTON_Y
			+ BUTTON_HEIGHT * 3;

	private static final int EXIT_X = BUTTON_X, EXIT_Y = BUTTON_Y
			+ BUTTON_HEIGHT * 4;

	private static final String MENU_MUSIC_NAME = "Menu";

	private static Music menuMusic;
	protected Image imageTitle;

	protected Button startButton;
	protected Button helpButton;
	protected Button settingsButton;
	protected Button creditsButton;
	protected Button exitButton;
	protected Button startButton_m;
	protected Button helpButton_m;
	protected Button settingsButton_m;
	protected Button creditsButton_m;
	protected Button exitButton_m;

	protected boolean goingToStart = false;
	protected boolean goingToExit = false;
	private int mouseX = Integer.MIN_VALUE, mouseY = Integer.MIN_VALUE;

	public MainMenuState() throws SlickException {
		super(BlackbeardsRedemption.States.MENU.ordinal());
		imageTitle = Art.getImage("TitleImage");

		startButton = new Button(Art.getImage("StartButton"), START_X, START_Y);
		buttons.add(startButton);

		helpButton = new Button(Art.getImage("HelpButton"), HELP_X, HELP_Y);
		buttons.add(helpButton);

		settingsButton = new Button(Art.getImage("SettingsButton"), SETTINGS_X,
				SETTINGS_Y);
		buttons.add(settingsButton);

		creditsButton = new Button(Art.getImage("CreditsButton"), CREDITS_X,
				CREDITS_Y);
		buttons.add(creditsButton);

		exitButton = new Button(Art.getImage("ExitButton"), EXIT_X, EXIT_Y);
		buttons.add(exitButton);
		
		startButton_m = new Button(Art.getImage("StartButton_mouse"), 
				START_X, START_Y);

		helpButton_m = new Button(Art.getImage("HelpButton_mouse"), 
				HELP_X, HELP_Y);

		settingsButton_m = new Button(Art.getImage("SettingsButton_mouse"), 
				SETTINGS_X, SETTINGS_Y);
		
		creditsButton_m = new Button(Art.getImage("CreditsButton_mouse"), 
				CREDITS_X, CREDITS_Y);

		exitButton_m = new Button(Art.getImage("ExitButton_mouse"), 
				EXIT_X, EXIT_Y);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		menuMusic = Song.getMusic(MENU_MUSIC_NAME);
		menuMusic.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);

		imageTitle.draw(TITLE_X, TITLE_Y);
		
		if(mouseX > BUTTON_X && mouseX < BUTTON_X + BUTTON_WIDTH) {
			if(mouseY > START_Y && mouseY < START_Y + BUTTON_HEIGHT) {
				startButton_m.draw(g);
			}
			if(mouseY > HELP_Y && mouseY < HELP_Y + BUTTON_HEIGHT) {
				helpButton_m.draw(g);
			}
			if(mouseY > SETTINGS_Y && mouseY < SETTINGS_Y + BUTTON_HEIGHT) {
				settingsButton_m.draw(g);
			}
			if(mouseY > CREDITS_Y && mouseY < CREDITS_Y + BUTTON_HEIGHT) {
				creditsButton_m.draw(g);
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
			menuMusic.stop();
			sbg.enterState(BlackbeardsRedemption.States.GAME.ordinal());
		}
		
		if(goingToExit) {
			gc.exit();
		}
	}

	@Override
	public void buttonClicked(Button b) throws SlickException {
		if (b == startButton) {
			goingToStart = true;
		}
		if (b == helpButton) {
			//@Todo implement instuctions screen
		}
		if (b == settingsButton) {
			//@Todo implement settings screen
		}
		if (b == creditsButton) {
			//@Todo imlement credits screen
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
