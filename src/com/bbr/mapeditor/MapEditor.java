package com.bbr.mapeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.bbr.mapeditor.Toolbar.Tools;
import com.bbr.resource.Utility;

public class MapEditor extends JPanel
		implements MouseInputListener, KeyListener {
	private static final long serialVersionUID = 1L; //Don't care about this

	private static final String TITLE = "Blackbeard's Redemption Map Editor";

	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private static final int BUTTON_WIDTH = 50;
	private static final int OFFSET_INCREMENT = 25; //Scroll speed

	private static Rectangle toolbarBounds = new Rectangle(0, 0, 
			BUTTON_WIDTH, HEIGHT);
	private static Rectangle editorBounds = new Rectangle(50, 0, 
			WIDTH - BUTTON_WIDTH, HEIGHT);

	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private HashMap<Point, BufferedImage> levelPreview = 
			new HashMap<Point, BufferedImage>();

	private static Toolbar toolbar;
	Tools currentTool = Tools.SPAWN;

	private JLabel text;
	private String level = "";
	private int mouseX = Integer.MIN_VALUE, mouseY = Integer.MAX_VALUE;
	private int offset = 0;

	public static void main(String argv[]) {
		//Setup JFrame
		JFrame window = new JFrame(TITLE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(WIDTH, HEIGHT));
		window.setResizable(false);
		window.setLayout(null);

		//Setup components and assign bounds
		MapEditor medit = new MapEditor();
		medit.setBounds(editorBounds);

		toolbar = new Toolbar();
		toolbar.setBounds(toolbarBounds);

		//Add components
		window.add(medit);
		window.add(toolbar);

		window.setVisible(true);
	}

	public MapEditor() {
		super();

		//Setup JPanel
		setPreferredSize(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setLayout(null);

		//Setup text output
		text = new JLabel();
		text.setBounds(800, 0, 100, 50);
		add(text);

		//Setup images
		//@TODO Temporarily hardcoded until there's time to fix it
		try {
			images.add(ImageIO.read(new File("res/blackbeard/standing.png")));
			images.add(ImageIO.read(new File("res/levels/level1/spike.png")));
			images.add(ImageIO.read(new File(
					"res/levels/level1/platform/platform.png")));
			images.add(ImageIO.read(new File(
					"res/terrain/dirt-platform-red.png")));
			images.add(ImageIO.read(new File("res/terrain/exit.png")));
		} catch(IOException e) {
			Utility.printError("Failed to load image");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getBounds().width, getBounds().height);

		for(Point p : levelPreview.keySet()) {
			if(isInView(p)) {
				g.drawImage(levelPreview.get(p), 
						p.x - offset, p.y + HEIGHT, null);
			}
		}

		currentTool = toolbar.getCurrentTool();
		g.drawImage(images.get(currentTool.ordinal()), mouseX, mouseY, null);
	}
	
	private boolean isInView(Point p) {
		if(p.x < offset || p.y > 0) {
			return false;
		}
		
		if(p.x > offset + WIDTH || p.y < 0 - HEIGHT) {
			return false;
		}
		
		return true;
	}

	private void updateMouse(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		text.setText("x: " + (mouseX + offset) + " y: " + (mouseY - HEIGHT));
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMouse(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMouse(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int gameX = e.getX() + offset;
		int gameY = e.getY() - HEIGHT;
		
		requestFocusInWindow();
		
		levelPreview.put(new Point(gameX, gameY), 
				images.get(currentTool.ordinal())); //add element to level

		switch(currentTool) {
		case SPAWN:
			level += "spawn at " + gameX + "," + gameY + "\n";
			break;
		case SPIKE:
			level += "spike at " + gameX + "," + (gameY - HEIGHT) + "\n";
			break;
		case PLATFORM:
			level += "platform at " + gameX + "," + gameY + "\n";
			break;
		case FALL_PLATFORM:
			level += "fallingplatform at " + gameX + "," + gameY + "\n";
			break;
		case EXIT:
			level += "exit at " + gameX + "," + gameY + "\n";
			break;
		default:
			throw new RuntimeException("Switch in " +
					"MapEditor.mouseClicked() is missing cases");
		}

		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(offset >= OFFSET_INCREMENT) {
				offset -= OFFSET_INCREMENT;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			offset += OFFSET_INCREMENT;
		}
		
		text.setText("x: " + (mouseX + offset) + " y: " + (mouseY - HEIGHT));
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public boolean isFocusable() {
		return true;
	}
}
