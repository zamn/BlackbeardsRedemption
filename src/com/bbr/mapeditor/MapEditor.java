package com.bbr.mapeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class MapEditor extends JPanel implements MouseInputListener {
	private static final long serialVersionUID = 1L; //Don't care about this
	
	private static final String TITLE = "Blackbeard's Redemption Map Editor";
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	private static final int BUTTON_WIDTH = 50;

	private static Rectangle toolbarBounds = new Rectangle(0, 0, 
			BUTTON_WIDTH, HEIGHT);
	private static Rectangle editorBounds = new Rectangle(50, 0, 
			WIDTH - BUTTON_WIDTH, HEIGHT);
	
	private JLabel text;
	
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
		
		Toolbar toolbar = new Toolbar();
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
		setLayout(null);
		
		//Setup text output
		text = new JLabel();
		text.setBounds(800, 0, 100, 50);
        add(text);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getBounds().width, getBounds().height);
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		text.setText("x: " + e.getX() + " y: " + e.getY());
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
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
