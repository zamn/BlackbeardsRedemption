package com.bbr.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.core.Zone;
import com.bbr.enemy.GhostPirate;
import com.bbr.entity.Entity;
import com.bbr.entity.terrain.Platform;
import com.bbr.gui.BbrGameState;
import com.bbr.main.BlackbeardsRedemption;
import com.bbr.player.Pirate;
import com.bbr.player.Player;

public class GameplayState extends BbrGameState {
	protected boolean lost = false;

	protected Zone zone;
	protected Player p;
	protected Image backgroundTest;

	public GameplayState() throws SlickException {
		super(BlackbeardsRedemption.GAMEPLAYSTATE);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.getGraphics().setBackground(new Color(128,128,128));
		zone = new Zone();
		// Player
		p = new Pirate(zone, 300, 300);
		zone.addEntity(p);
		zone.follow(p);
		// Enemy test
		Entity e = new GhostPirate(zone, 200, 150);
		zone.addEntity(e);
		// Terrain test
		e = new Platform(zone, 300, 400);
		e.setXsize(e.getXsize() * 10);
		zone.addEntity(e);

		backgroundTest = new Image("res/desert-background.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		backgroundTest.draw(-zone.getXscroll()+25, -zone.getYscroll()+37);
		//backgroundTest.draw();
		zone.draw(g);
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
}
