package examples;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MoveGame extends BasicGame {
	public static final String WINDOW_TITLE = "Slick2D Example - Simple Game";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final boolean FULL_SCREEN = false;

	protected Image imagePlayer;
	public static final int MOVE_SPEED = 3; // per frame
	public static final int MIN_X = 0, MIN_Y = 0, MAX_X = WINDOW_WIDTH, MAX_Y = WINDOW_HEIGHT;
	protected int playerX = WINDOW_WIDTH/2, playerY = WINDOW_HEIGHT/2;

	public MoveGame() throws SlickException {
		super(WINDOW_TITLE);
	}

	public void init(GameContainer gc) throws SlickException {
		imagePlayer = new Image("res/red-pirate-small.png");
		gc.getGraphics().setBackground(Color.gray);
	}
	public void update(GameContainer gc, int delta) throws SlickException {
		// Input handling
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_UP)) {
			playerY -= MOVE_SPEED;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			playerY += MOVE_SPEED;
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			playerX -= MOVE_SPEED;
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerX += MOVE_SPEED;
		}
		// Physics: Bounds check
		if (playerX < MIN_X) playerX = MIN_X;
		if (playerY < MIN_Y) playerY = MIN_Y;
		if (playerX + imagePlayer.getWidth() > MAX_X) playerX = MAX_X - imagePlayer.getWidth();
		if (playerY + imagePlayer.getHeight() > MAX_Y) playerY = MAX_Y - imagePlayer.getHeight();
	}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		imagePlayer.draw(playerX, playerY);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MoveGame());
		app.setDisplayMode(WINDOW_WIDTH,WINDOW_HEIGHT, FULL_SCREEN);
		app.setTargetFrameRate(30); // because 7k fps makes precise movement difficult
		app.start();
	}
}
