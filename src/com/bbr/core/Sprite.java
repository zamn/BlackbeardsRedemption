package com.bbr.core;

import java.util.HashMap;

import org.newdawn.slick.Image;

public class Sprite {
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();

	public void setDelay(String name, long delay) {
		Animation anim = animations.get(name);
		if (anim == null) {
			anim = new Animation();
		}
		anim.setDelay(delay);
	}
	public void addFrame(Image frame) {
		addFrame("normal", frame);
	}
	public void addFrame(String name, Image frame) {
		Animation anim = animations.get(name);
		if (anim == null) {
			anim = new Animation();
			anim.addFrame(frame);
			animations.put(name, anim);
		} else {
			anim.addFrame(frame);
		}
	}

	public Image getFrame() {
		return getFrame("normal");
	}
	public Image getFrame(String animationName) {
		return animations.get(animationName).getCurrentFrame();
	}
}
