package com.bbr.mapeditor;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.bbr.mapeditor.Toolbar.Tools;
import com.bbr.resource.Utility;

public class MapEditor extends JPanel
		implements MouseInputListener, KeyListener, ActionListener {
	private static final long serialVersionUID = 1L; //Don't care about this

	private static final String TITLE = "Blackbeard's Redemption Map Editor";
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private static final int BUTTON_WIDTH = 50;
	private static final int OFFSET_INCREMENT = 25; //Scroll speed

	private static Rectangle toolbarBounds = new Rectangle(0, 0, BUTTON_WIDTH, HEIGHT);
	private static Rectangle editorBounds = new Rectangle(50, 0, WIDTH - BUTTON_WIDTH, HEIGHT);
	private static Rectangle textBounds = new Rectangle(800, 0, 100, 50);
	private static Rectangle autoAlignBounds = new Rectangle(800, 15, 100, 50);
	private static Rectangle saveBounds = new Rectangle(800, 600, 100, 50);
	private static Rectangle bgBounds = new Rectangle(695, 600, 105, 50);

	private ArrayList<ArrayList<BufferedImage>> images = new ArrayList<ArrayList<BufferedImage>>();
	private HashMap<Point, BufferedImage> levelPreview = new HashMap<Point, BufferedImage>();
	
	private boolean toggleShift = false;
	private int YVal = 0;

	private static Toolbar toolbar;
	Tools currentTool = Tools.SPAWN;

	private JLabel text;
	private JLabel autoAlign;
	private JButton save;
	private JButton bg;
	private BufferedImage background;
	private String level = "";
	private int mouseX = Integer.MIN_VALUE, mouseY = Integer.MIN_VALUE;
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
		text.setBounds(textBounds);
		add(text);
		
		autoAlign = new JLabel();
		autoAlign.setBounds(autoAlignBounds);
		add(autoAlign);
		autoAlign.setText("Auto Align: OFF");
		
		
		
		//Setup save button
		save = new JButton("Save Level");
		save.setBounds(saveBounds);
		save.setToolTipText("Click to save this level");
		save.addActionListener(this);
		add(save);
		
		//Setup background button
		bg = new JButton("Background");
		bg.setBounds(bgBounds);
		bg.setToolTipText("Click to change background");
		bg.addActionListener(this);
		add(bg);

		//Setup images
		//@TODO Temporarily hardcoded until there's time to fix it
		try {
			ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
			temp.add(ImageIO.read(new File("res/blackbeard/standing.png")));
			images.add(temp);
			
			temp = new ArrayList<BufferedImage>();
			temp.add(ImageIO.read(new File("res/levels/level1/spike.png")));
			images.add(temp);
			
			temp = new ArrayList<BufferedImage>();
			temp.add(ImageIO.read(new File("res/levels/level1/platform/platform.png")));
			images.add(temp);
			
			temp = new ArrayList<BufferedImage>();
			temp.add(ImageIO.read(new File("res/terrain/dirt-platform-red.png")));
			images.add(temp);
			
			temp = new ArrayList<BufferedImage>();
			temp.add(ImageIO.read(new File("res/terrain/exit.png")));
			images.add(temp);
		} catch(IOException e) {
			Utility.printError("Failed to load image");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getBounds().width, getBounds().height);
		
		if (toggleShift)
			g.drawLine(0, YVal, (int)Frame.getFrames()[0].getBounds().getWidth(), YVal);
		// x, y, x, y
		
		if(background != null) {
			g.drawImage(background, 0, 0, null);
		}
		
		for(Point p : levelPreview.keySet()) {
			if(isInView(p)) {
				g.drawImage(levelPreview.get(p), 
						p.x - offset, p.y, null);
			}
		}

		currentTool = toolbar.getCurrentTool();
		int clicked = toolbar.clicked();
		ArrayList<BufferedImage> temp = images.get(currentTool.ordinal());
		g.drawImage(temp.get(clicked % temp.size()), mouseX, mouseY, null);
	}
	
	private boolean isInView(Point p) {
		if(p.x < offset || p.y < 0) {
			return false;
		}
		
		if(p.x > offset + WIDTH || p.y > HEIGHT) {
			return false;
		}
		
		return true;
	}

	private void updateMouse(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		updateText();
		repaint();
	}
	
	private void updateText() {
		text.setText("x: " + (mouseX + offset) + " y: " + mouseY);
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
		int gameY = e.getY();
		requestFocusInWindow();
		
		int newX = gameX;
		
		Iterator<Point> grid = levelPreview.keySet().iterator();
		
		while (grid.hasNext()) {
			Point p = grid.next();
			if (gameX >= p.getX() && gameX <= (p.getX() + levelPreview.get(p).getWidth())) {
				newX = (int)(p.getX() + levelPreview.get(p).getWidth());
			}
			else if (gameX >= (p.getX() - levelPreview.get(p).getWidth()) && gameX <= p.getX())
				newX = (int)(p.getX() - levelPreview.get(p).getWidth());
		}
		
		if (toggleShift) {
			levelPreview.put(new Point(newX, YVal), images.get(currentTool.ordinal()).get(0));
		}
		else
			levelPreview.put(new Point(gameX, gameY), images.get(currentTool.ordinal()).get(0)); //add element to level

		switch(currentTool) {
		case SPAWN:
			level += "spawn at " + gameX + "," + gameY + NEWLINE;
			break;
		case SPIKE:
			level += "spike at " + gameX + "," + gameY + NEWLINE;
			break;
		case PLATFORM:
			level += "platform at " + gameX + "," + gameY + NEWLINE;
			break;
		case FALL_PLATFORM:
			level += "fallingplatform at " + gameX + "," + gameY + NEWLINE;
			break;
		case EXIT:
			level += "exit at " + gameX + "," + gameY + NEWLINE;
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
		
		if (e.getKeyCode() == 16) {
			if (!toggleShift) {
				toggleShift = true;
				YVal = (int) MouseInfo.getPointerInfo().getLocation().getY()-24;
				autoAlign.setText("Auto Align: ON");
			}
			else {
				toggleShift = false;
				autoAlign.setText("Auto Align: OFF");
			}
		}
		
		updateText();
		repaint();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			JFileChooser fc = new JFileChooser();
			PrintWriter fout;
			
			int choice = fc.showSaveDialog(this);
			if(choice == JFileChooser.APPROVE_OPTION) {
				try {
					fout = new PrintWriter(fc.getSelectedFile());
					fout.print(level);
					fout.close();
				} catch (FileNotFoundException e1) {
					throw new RuntimeException("Cannot find selected file", e1);
				}
			}
		}
		if(e.getSource() == bg) {
			String input = JOptionPane.showInputDialog("Background name");
			String line;
			BufferedReader fin;
			
			try {
				fin = new BufferedReader(
						new FileReader("data/imagelist.txt"));
				while((line = fin.readLine()) != null) {
					if(line.equals(input)) {
						line = fin.readLine();
						background = ImageIO.read(new File(line));
						level += "Background " + input + NEWLINE;
						fin.close();
						return;
					}
				}
			} catch (FileNotFoundException e1) {
				throw new RuntimeException("Wrong path to imagelist", e1);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(this, 
					"Could not find requested background image");
		}
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
