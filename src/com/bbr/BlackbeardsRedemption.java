package com.bbr;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BlackbeardsRedemption extends BasicGame {

	public BlackbeardsRedemption() {
		super("Hello World");
	}

	public void init(GameContainer gc) throws SlickException {
	}

	public void update(GameContainer gc, int delta) throws SlickException {
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Hello World", 100, 100);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new BlackbeardsRedemption());
		app.setDisplayMode(1280, 720, false);
		app.start();
	}
}
