package com.bbr.core;

import java.util.Collection;
import java.util.HashMap;

import org.newdawn.slick.Image;

public class Sprite {
	public static final String DEFAULT_FRAME = "normal";
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private String name = ""; 

	public void setDelay(String name, int delay) {
		Animation anim = animations.get(name);
		if (anim == null) {
			anim = new Animation();
		}
		anim.setDelay(delay);
	}
	public void addFrame(Image frame) {
		name = frame.getResourceReference();
		name = name.substring(name.lastIndexOf("/")+1, name.indexOf("."));
		addFrame(DEFAULT_FRAME, frame);
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
	
	public String getName() {
		return name;
	}

	protected void restartOtherAnimations() {
		restartOtherAnimations(DEFAULT_FRAME);
	}
	protected void restartOtherAnimations(String curAnimation) {
		Animation cur = animations.get(curAnimation);
		Collection<Animation> anims = animations.values();
		for (Animation anim : anims) {
			if (anim != cur) {
				anim.restart();
			}
		}
	}

	public Image getFrame() {
		return getFrame(DEFAULT_FRAME);
	}
	public Image getFrame(String animationName) {
		restartOtherAnimations(animationName);
		return animations.get(animationName).getCurrentFrame();
	}
	
	public String toString() {
		return name;
	}
}
