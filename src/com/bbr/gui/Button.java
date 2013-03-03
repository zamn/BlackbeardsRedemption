package com.bbr.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Button implements Drawable {
	protected Rectangle buttonShape;
	protected Image image;
	protected int posX,posY;
	protected int sizeX,sizeY;

	public Button(Image image, int x, int y) {
		buttonShape = new Rectangle(x, y, image.getWidth(), image.getHeight());
		this.posX = x;
		this.posY = y;
		setImage(image);
		autoResize();
	}

	public void setImage(Image image) {
		this.image = image;
		autoResize();
	}
	public void autoResize() {
		if (image != null) changeSize(image.getWidth(), image.getHeight());
	}
	public void changeSize(int newWidth, int newHeight) {
		sizeX = newWidth;
		sizeY = newHeight;
		buttonShape.setBounds(posX, posY, newWidth, newHeight);
	}

	public void draw(Graphics g) {
		image.draw(posX, posY, sizeX, sizeY);
	}

	public boolean checkClick(int mouseX, int mouseY) {
		return buttonShape.contains(mouseX, mouseY);
	}
}
