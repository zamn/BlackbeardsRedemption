package com.bbr.main;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.state.GameplayState;
import com.bbr.state.MainMenuState;

public class BlackbeardsRedemption extends StateBasedGame {
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 600;
	public static final boolean FULL_SCREEN = false;
	public static final int MAX_FPS = 30;

	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;

	public BlackbeardsRedemption() {
		super("Blackbeards Redemption");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new BlackbeardsRedemption());
		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, FULL_SCREEN);
		app.setTargetFrameRate(MAX_FPS);
		app.setShowFPS(false);
		app.start();
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		//this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
	}
}
