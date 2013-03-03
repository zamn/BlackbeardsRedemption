package com.bbr.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.core.Zone;
import com.bbr.enemy.GhostPirate;
import com.bbr.entity.Entity;
import com.bbr.gui.BbrGameState;
import com.bbr.main.BlackbeardsRedemption;
import com.bbr.player.Pirate;
import com.bbr.player.Player;

public class GameplayState extends BbrGameState {
	protected boolean lost = false;

	protected Zone zone;
	protected Player p;

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
		Entity e = new GhostPirate(zone, 200, 200);
		zone.addEntity(e);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
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
