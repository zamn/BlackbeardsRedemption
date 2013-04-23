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

public class MainMenuState extends BbrGameState {
	private static final int START_X = 400, START_Y = 400;
	private static final int TITLE_X = 150, TITLE_Y = 50;
	private static final String MENU_MUSIC_PATH = 
			"res/audio/music/menu_music.aiff";
	private static final int FADE_TIME = 5000; //Time(ms) to fade out menu music
	
	private static Music menuMusic;
	protected Image imageTitle;
	protected Button buttonStart;
	protected boolean goingToStart = false;

	public MainMenuState() throws SlickException {
		super(BlackbeardsRedemption.States.MENU.ordinal());
		imageTitle = Art.getImage("TitleImage");
		buttonStart = new Button(Art.getImage("StartButton"), START_X, START_Y);
		buttons.add(buttonStart);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		menuMusic = new Music(MENU_MUSIC_PATH);
	    menuMusic.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		
		imageTitle.draw(TITLE_X, TITLE_Y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (goingToStart) {
			goingToStart = false;
			menuMusic.fade(FADE_TIME, 0, true);
			sbg.enterState(BlackbeardsRedemption.States.GAME.ordinal());
		}
	}

	@Override
	public void buttonClicked(Button b) throws SlickException {
		if (b == buttonStart) {
			goingToStart = true;
		}
	}
}
