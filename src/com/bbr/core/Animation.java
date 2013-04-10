package com.bbr.core;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.bbr.resource.Settings;

public class Animation {
	// Time-based animation handling - to be deprecated
	public static final int ANIMATION_FRAME_DELAY = Settings.valueInt("fps") * 10; // time between frames in ms
	protected long lastTime = 0; // in ms
	protected long delay = ANIMATION_FRAME_DELAY;
	// New tick-based/frame-based animation handling
	protected static TickHandler tickHandler;
	protected long startTick = 0;
	protected long tickDelay = Settings.valueInt("fps");

	protected ArrayList<Image> frames = new ArrayList<Image>();
	protected int curFrameIndex = -1; // will be advanced to 0 in getCurrentFrame

	public static void setFrameHandler(TickHandler handler) {
		tickHandler = handler;
	}

	public void setDelay(long delay) {
//		this.delay = delay;
		this.tickDelay = delay;
	}
	public void addFrame(Image frame) {
		frames.add(frame);
	}
	public Image getCurrentFrame() {
		if (frames.size() == 0) return null;
		if (tickHandler == null) { // to be deprecated
			if (System.currentTimeMillis() - lastTime >= delay || lastTime == 0) {
				curFrameIndex++;
				curFrameIndex %= frames.size(); // loop back to start
				lastTime = System.currentTimeMillis();
			}
			return frames.get(curFrameIndex);
		} else {
			long frameIndex = (tickHandler.getTickCount() - startTick) / tickDelay;
			frameIndex = frameIndex % frames.size();
			// casting to int results in no change since frames.size() is an int
			return frames.get((int)frameIndex);
		}
	}
	public void restart() {
		if (tickHandler != null) {
			startTick = tickHandler.getTickCount();
		}
	}
}