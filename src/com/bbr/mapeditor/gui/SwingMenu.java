package com.bbr.mapeditor.gui;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A collection of {@code Button}s that simplifies 
 * the process of using multiple buttons in a menu.
 * @author K.R.S.
 * @version 14-June-2012
 */
public class SwingMenu {
	/**
	 * All possible alignments to display the menu with
	 * @TODO document different formatting options
	 */
	public enum Alignments { LEFT, CENTER, RIGHT, EXACT }; 

	private ArrayList<SwingButton> menu = new ArrayList<SwingButton>();
	private int width, height;

	public SwingMenu(ArrayList<SwingButton> menu) {
		setMenu(menu);
	}

	/**
	 * Aligns all buttons in the menu according to the selected
	 * {@code Alignments} then displays them.
	 * 
	 * @param x x coordinate to display at
	 * @param y y coordinate to display at
	 * @param align format to align the buttons relative to each other and the
	 * x and y coordinates
	 * @param g destination to draw to
	 */
	public void drawAt(int x, int y, Alignments align, Graphics g) {
		for(SwingButton button : menu) {
			if(align == Alignments.LEFT) {
				//Vertically center entire menu, horizontally left justify image
				button.drawAt(x, y - this.getHeight() / 2, g);
			} else if(align == Alignments.CENTER) {
				//Vertically center entire menu, horizontally center image
				button.drawAt(x - button.getWidth() / 2, 
						y - this.getHeight() / 2, g);
			} else if(align == Alignments.RIGHT) {
				//Vertically center entire menu, 
				//horizontally right justify image
				button.drawAt(x - button.getWidth(),
						y - this.getHeight() / 2, g); 
			} else if(align == Alignments.EXACT) {
				button.drawAt(x, y, g); //Display at exact coordinates
			}

			y += button.getHeight();
		}
	}

	/**
	 * Determines which button in this menu has been clicked on, if any.
	 * @param x x-coordinate of click
	 * @param y y-coordinate of click
	 * @return if a button has been clicked on, 
	 * a reference to that {@code MultiButton}, else null
	 */
	public SwingButton buttonClicked(int x, int y) {
		for(SwingButton button : menu) {
			if(button.isClicked(x, y)) {
				return button;
			}
		}

		return null;
	}

	public ArrayList<SwingButton> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<SwingButton> menu) {
		this.menu = menu;
		calcDimensions(); //Changing the menu changes the dimensions
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	//Finds bounding box dimensions for the menu
	//@TODO make this work for horizontal menus too
	private void calcDimensions() {
		width = 0;
		height = 0;

		for(SwingButton button : menu) {
			if(button.getWidth() > width) {
				width = button.getWidth();
			}

			height += button.getHeight();
		}
	}
}
