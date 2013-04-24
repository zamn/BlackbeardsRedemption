package com.bbr.mapeditor.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.bbr.resource.Utility;

/**
 * This class simplifies the process of creating and using a button.
 * The button is created with an image representation 
 * and can tell if it has been clicked on.
 * Collision detection currently only works with rectangular shaped buttons.
 * @author K.R.S.
 * @version 14-June-2012
 */
public class SwingButton {
	//Default image used if no image is available
	private static BufferedImage error;
	private static final int DEFAULT_IMAGE_WIDTH = 50;
	private static final int DEFAULT_IMAGE_HEIGHT = 50;
	private static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

	static {
		error = new BufferedImage(DEFAULT_IMAGE_WIDTH, 
				DEFAULT_IMAGE_HEIGHT, DEFAULT_IMAGE_TYPE);
		error.getGraphics().setColor(new Color(100, 200, 150));
		FontMetrics fm = error.getGraphics().getFontMetrics();
		error.getGraphics().drawString("Error", 0, fm.getHeight());
	}
	
	/**
	 * Setting x and y to Integer.MIN_VALUE guarantees that they cannot be
	 * clicked on before they are drawn to screen as there is no way to
	 * set x and y position outside of drawing the button
	 */
	private int x_pos = Integer.MIN_VALUE, y_pos = Integer.MIN_VALUE;
	private int id;
	private BufferedImage img;

	/**
	 * Create button and try to load image at specified filepath.
	 * If the image cannot be loaded, a default image is used instead so that
	 * the button is still usable.
	 * NOTE: Buttons currently only work with rectangular images
	 * @param path path to button image
	 * @param id unique button id
	 */
	public SwingButton(String path, int id) {
		this.id = id;
		
		try {
			img = ImageIO.read(new File(path));
		} catch (Exception e) {
			//Make new deep copy of error
			img = new BufferedImage(error.getColorModel(),
					error.copyData(null), error.isAlphaPremultiplied(), null);
			Utility.printError("Failed to load image at + " + path);
		}
	}

	/**
	 * Create button with already loaded image.
	 * If the image is null, a default image is used instead so that the
	 * button is still usable.
	 * NOTE: Buttons currently only work with rectangular images.
	 * @param img image to represent the button
	 * @param id unique button id
	 */
	public SwingButton(BufferedImage img, int id) {
		this.id = id;
		
		if(img != null) {
			this.img = img;
		} else {
			//Make new deep copy of error
			img = new BufferedImage(error.getColorModel(),
					error.copyData(null), error.isAlphaPremultiplied(), null);
			Utility.printError("Tried to create button with null image");
		}
	}

	//Draws with top left corner of the image at the specified position
	public void drawAt(int x, int y, Graphics g) {
		x_pos = x;
		y_pos = y;
		g.drawImage(img, x_pos, y_pos, null);
	}

	//Draws with center of the image at the specified position
	public void drawCenteredAt(int x, int y, Graphics g) {
		drawAt(x - img.getWidth() / 2, y - img.getHeight() / 2, g);
	}

	public boolean isClicked(int x, int y) {
		//If in rectangle boundary
		//@TODO Make this work with buttons that are not rectangular
		if(x >= x_pos && x <= x_pos + img.getWidth() 
				&& y >= y_pos && y <= y_pos + img.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getX() {
		return x_pos;
	}
	
	public int getY() {
		return y_pos;
	}

	public int getWidth() {
		return img.getWidth();
	}

	public int getHeight() {
		return img.getHeight();
	}

	public BufferedImage getImage() {
		return img;
	}
	
	public int getID() {
		return id;
	}
}