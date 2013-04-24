package com.bbr.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Button implements Drawable {
	protected Rectangle bounds;
	protected Image image;
	protected int posX,posY;
	protected int sizeX,sizeY;

	public Button(Image image, int x, int y) {
		posX = x;
		posY = y;
		setImage(image);
	}

	public void setImage(Image image) {
		this.image = image;

		if(image != null) {
			sizeX = image.getWidth();
			sizeY = image.getHeight();
		}
	}

	@Override
	public void draw(Graphics g) {
		if(image != null) {
			image.draw(posX, posY, sizeX, sizeY);
			bounds = new Rectangle(posX, posY, sizeX, sizeY);
		}
	}

	public boolean checkClick(int mouseX, int mouseY) {
		if(bounds == null) {
			return false;
		}

		return bounds.contains(mouseX, mouseY);
	}
}
