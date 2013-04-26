package com.bbr.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.bbr.core.Sprite;
import com.bbr.entity.Entity;

public final class Art {
	public static final String SPRITE_LIST = "data/spritelist.txt";
	public static final File IMAGE_LIST = new File("data/imagelist.txt");
	protected static HashMap<String, ArrayList<Sprite>> sprites = new HashMap<String, ArrayList<Sprite>>();
	protected static HashMap<String, Image> images = new HashMap<String, Image>();

	private static boolean loaded = false;

	private Art() { }
	//
	//	static {
	//		String classJar = Art.class.getResource("/" + Art.class.getName().replace('.', '/') + ".class").toString();
	//		if (classJar.startsWith("jar:")) {
	//			runningInJar = true;
	//		} else {
	//			runningInJar = false;
	//		}
	//	}
	public static void load() { // Not in a static block due to Slick 
		if(loaded) {
			Utility.printWarning(
					"Loading art files after art has already been loaded.");
		}
		
		loadImages();
		loadSprites();
		loaded = true;
	}
	private static void loadImages() {
		ImagesFileReader fileReader = new ImagesFileReader(IMAGE_LIST);
		try {
			fileReader.readFile();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	private static void loadSprites() {
		SpritesFileReader fileReader = new SpritesFileReader(new File(SPRITE_LIST));
		try {
			fileReader.readFile();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addSprite(String spriteName, Sprite sprite) {
		if (sprites.get(spriteName) != null) {
			if (!sprites.get(spriteName).contains(sprite))
				sprites.get(spriteName).add(sprite);
		}
		else {
			ArrayList<Sprite> temp = new ArrayList<Sprite>();
			temp.add(sprite);
			sprites.put(spriteName, temp);
		}	
	}
	
	protected static Image loadImage(String imagename) throws SlickException {
		return new Image(imagename);
	}

	public static Sprite getSprite(Entity entity) {
		return sprites.get(entity.getClass().getSimpleName()).get(0);
	}
	
	public static Sprite getSprite(Entity entity, String type) {
		ArrayList<Sprite> spriteList = sprites.get(entity.getClass().getSimpleName());
		for (int i = 0; i < spriteList.size(); i++) {
			if (spriteList.get(i).getName().equalsIgnoreCase(type))
				return spriteList.get(i);
		}
		return null;
	}
	
	public static Sprite getSprite(String spriteName) {
		return sprites.get(spriteName).get(0);
	}
	
	public static ArrayList<Sprite> getSprites(String spriteName) {
		return sprites.get(spriteName);
	}
	
	public static Image getImage(String imageName) {
		return images.get(imageName);
	}
}
