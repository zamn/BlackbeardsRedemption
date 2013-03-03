package com.bbr.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.resource.Art;
import com.bbr.resource.Settings;
import com.bbr.state.GameplayState;
import com.bbr.state.MainMenuState;

// Runner
public class BlackbeardsRedemption extends StateBasedGame {
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;

	public BlackbeardsRedemption() {
		super("Blackbeards Redemption");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new BlackbeardsRedemption());
		app.setDisplayMode(Settings.valueInt("windowWidth"), Settings.valueInt("windowHeight"), Settings.valueBoolean("fullScreen"));
		app.setTargetFrameRate(Settings.valueInt("fps"));
		app.setShowFPS(false);
		app.start();
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		Art.load();
		this.addState(new MainMenuState());
		this.addState(new GameplayState());
		this.enterState(GAMEPLAYSTATE);
	}
}
