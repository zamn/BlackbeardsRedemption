package com.bbr.mapeditor;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.bbr.mapeditor.gui.SwingButton;
import com.bbr.mapeditor.gui.SwingMenu;

public class Toolbar extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L; //Don't care about this

	public enum Tools { SPAWN, SPIKE, PLATFORM, FALL_PLATFORM, EXIT };
	private static final String[] imagePaths = {
		"res/ui/editor/spawn.png", "res/ui/editor/spike.png", 
		"res/levels/level1/platform/platform.png", 
		"res/terrain/dirt-platform-red.png", "res/ui/editor/exitIcon.png" 
	};
	private static final String GLOW_PATH = "res/ui/editor/yellow_glow.png";

	private SwingMenu toolbar;
	private Tools currentTool = Tools.SPAWN;
	private BufferedImage selectedGlow;

	public Toolbar() {
		super();

		//Setup JPanel
		setPreferredSize(null);
		addMouseListener(this);

		//Create toolbar
		ArrayList<SwingButton> buttons = new ArrayList<SwingButton>();
		for(int i = 0; i < imagePaths.length; i++) {
			buttons.add(new SwingButton(imagePaths[i],
					Tools.values()[i].ordinal()));
		}
		toolbar = new SwingMenu(buttons);

		//Load glow
		try {
			selectedGlow = ImageIO.read(new File(GLOW_PATH));
		} catch (IOException e) {
			throw new RuntimeException("Incorrect glow path", e);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getBounds().width, getBounds().height);
		toolbar.drawAt(0, 0, SwingMenu.Alignments.EXACT, g);
		if(selectedGlow != null) {
			SwingButton currentButton = 
					toolbar.getMenu().get(currentTool.ordinal());
			g.drawImage(selectedGlow, 
					currentButton.getX(), currentButton.getY(), null);
		}
	}

	public Tools getCurrentTool() {
		return currentTool;
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		SwingButton clicked = toolbar.buttonClicked(e.getX(), e.getY());
		if(clicked == null) {
			return;
		}

		int id = clicked.getID();
		currentTool = Tools.values()[id];
		getParent().repaint(); //Affects MapEditor.java
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
}
