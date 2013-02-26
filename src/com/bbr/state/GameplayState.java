package com.bbr.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.core.Pirate;
import com.bbr.core.Player;
import com.bbr.core.Zone;
import com.bbr.gui.Button;
import com.bbr.resource.Art;

public class GameplayState extends BasicGameState {
	protected int stateID = -1;

	protected boolean lost = false;
	protected Image gameOver, gameOverReturn;

	protected Zone zone;
	protected Player p;

	public int getID() { return stateID; }
	public GameplayState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.getGraphics().setBackground(new Color(128,128,128));
		gameOver = new Image("res/red-pirate-small.png");
		gameOverReturn = new Image("res/red-pirate-small.png");
		Art.load();
		zone = new Zone();
		p = new Pirate(zone, 400, 400);
		zone.addEntity(p);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		zone.draw(g);
		p.draw(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		zone.dt();
	}

	public void keyPressed(int key, char c) {
		p.keyPressed(key);
	}

	public void keyReleased(int key, char c) {
		p.keyReleased(key);
	}

	public void mousePressed(int button, int mouseX, int mouseY) {
	}

	public void buttonClicked(Button b) throws SlickException {
	}
}
