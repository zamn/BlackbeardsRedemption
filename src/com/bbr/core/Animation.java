package com.bbr.core;

import java.util.ArrayList;

import org.newdawn.slick.Image;

public class Animation {
	protected ArrayList<Image> frames = new ArrayList<Image>();
	protected int curFrameIndex = -1; // will be advanced to 0 in getCurrentFrame
	public long lastTime = 0; // in ms
	public long delay = Sprite.ANIMATION_FRAME_DELAY;

	public void addFrame(Image frame) {
		frames.add(frame);
	}
	public Image getCurrentFrame() {
		if (frames.size() == 0) return null;
		if (System.currentTimeMillis() - lastTime >= delay || lastTime == 0) {
			curFrameIndex++;
			curFrameIndex %= frames.size(); // loop back to start
			lastTime = System.currentTimeMillis();
		}
		return frames.get(curFrameIndex);
	}
}