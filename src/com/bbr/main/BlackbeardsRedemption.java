package com.bbr.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.resource.Art;
import com.bbr.resource.Settings;
import com.bbr.state.GameplayState;
import com.bbr.state.MainMenuState;

public class BlackbeardsRedemption extends StateBasedGame {
	public enum States {MENU, GAME, SETTINGS, CREDITS};

	public BlackbeardsRedemption() {
		super("Blackbeards Redemption");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = 
				new AppGameContainer(new BlackbeardsRedemption());
		app.setDisplayMode(Settings.valueInt("windowWidth"), 
				Settings.valueInt("windowHeight"), 
				Settings.valueBoolean("fullScreen"));
		app.setTargetFrameRate(Settings.valueInt("fps"));
		app.setShowFPS(false);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) 
			throws SlickException {
		Art.load();
		
		/* Note: States MUST be added in the same order 
		 * as they appear in the enum BlackbeardsRedemption.States 
		 */
		this.addState(new MainMenuState());
		this.addState(new GameplayState());
		this.enterState(States.MENU.ordinal());
	}
}
