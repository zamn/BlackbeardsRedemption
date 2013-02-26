package com.bbr.menu;

public class MainMenuState extends BasicGameState{
	int stateID = -1;
	MainMenuState(int stateID){
		this.stateID = stateID;
	}
	@Override
	public int getID(){
		return stateID;
	}
}
