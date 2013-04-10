package com.bbr.core;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.bbr.resource.Settings;
import com.bbr.resource.Utility;

public class Animation {
	// Tick-based/frame-based animation handling
	protected static TickHandler tickHandler;
	protected long startTick = 0;
	protected long tickDelay = Settings.valueInt("fps");

	protected ArrayList<Image> frames = new ArrayList<Image>();
	protected int curFrameIndex = -1; // will be advanced to 0 in getCurrentFrame

	public static void setFrameHandler(TickHandler handler) {
		tickHandler = handler;
	}

	public void setDelay(long delay) {
		this.tickDelay = delay;
	}
	public void addFrame(Image frame) {
		frames.add(frame);
	}
	public Image getCurrentFrame() {
		if (frames.size() == 0) return null;
		if (tickHandler != null) {
			long frameIndex = (tickHandler.getTickCount() - startTick) / tickDelay;
			frameIndex = frameIndex % frames.size();
			// casting to int results in no change since frames.size() is an int
			return frames.get((int)frameIndex);
		}
		Utility.printError("No tick handler attached to animation. Sprites will only show first frame.");
		return frames.get(0);
	}
	public void restart() {
		if (tickHandler != null) {
			startTick = tickHandler.getTickCount();
		}
	}
}