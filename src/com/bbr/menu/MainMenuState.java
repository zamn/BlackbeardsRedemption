package com.bbr.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState{
	int stateID = -1;
	MainMenuState(int stateID){
		this.stateID = stateID;
	}
	@Override
	public int getID(){
		return stateID;
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
	}
}
