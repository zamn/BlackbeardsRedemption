package examples;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BlankGame extends BasicGame {
	public static final String WINDOW_TITLE = "Slick2D Example - Blank Game";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final boolean FULL_SCREEN = false;

	public BlankGame() {
		super(WINDOW_TITLE);
	}

	public void init(GameContainer gc) throws SlickException {
	}
	public void update(GameContainer gc, int delta) throws SlickException {
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new BlankGame());
		app.setDisplayMode(WINDOW_WIDTH,WINDOW_HEIGHT, FULL_SCREEN);
		app.start();
	}
}
