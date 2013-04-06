package com.bbr.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbr.core.Zone;
import com.bbr.enemy.GhostPirate;
import com.bbr.enemy.Snake;
import com.bbr.entity.Entity;
import com.bbr.entity.terrain.Platform;
import com.bbr.gui.BbrGameState;
import com.bbr.level.Level;
import com.bbr.level.LevelHandler;
import com.bbr.main.BlackbeardsRedemption;
import com.bbr.player.Pirate;
import com.bbr.player.Player;

public class GameplayState extends BbrGameState implements LevelHandler {
	protected boolean lost = false;

	protected Zone zone;
	protected Level curLevel;
	protected Player p;
	protected Image backgroundTest;

	public GameplayState() throws SlickException {
		super(BlackbeardsRedemption.GAMEPLAYSTATE);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		backgroundTest = new Image("res/desert-background.png");
		gc.getGraphics().setBackground(new Color(128,128,128));
		zone = new Zone(this);

		curLevel = Level.getFirstLevel();
		curLevel.loadInto(zone);
		p = zone.getPlayer();
		// testInit(zone);
	}
	// Hardcoded level, remove later and use level text files
	public void testInit(Zone zone) {
		// Player
		p = new Pirate(zone, 300, 300);
		zone.addEntity(p);
		zone.follow(p);
		// Enemy test
		Entity e = new Snake(zone, 400, 150);
		zone.addEntity(e);
		zone.addEntity(new GhostPirate(zone, 280, 100));
		// Terrain test
		e = new Platform(zone, 300, 400);
		e.setXsize(e.getXsize() * 90);
		zone.addEntity(e);
		e = new Platform(zone, 500, (400 - e.getYsize()));
		e.setXsize(e.getXsize() * 3);
		zone.addEntity(e);
	}

	public void nextLevel() {
		Level.getNextLevel(curLevel);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//backgroundTest.draw(-zone.getXscroll()+25, -zone.getYscroll()+37);
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
