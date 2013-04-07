package com.bbr.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.bbr.core.Sprite;
import com.bbr.entity.Entity;

public final class Art {
	private static final String SPRITE_LIST = "data/spritelist.txt";
	private static final File IMAGE_LIST = new File("data/imagelist.txt");
	private static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private static HashMap<String, Image> images = new HashMap<String, Image>();

	private static boolean loaded = false;
	//
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
		if (loaded) {
			Utility.printWarning("Loading art files after already having loaded art files.");
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
	private static Image loadImage(String imagename) throws SlickException {
		System.out.println("loading "+ imagename);
		Image loaded = new Image(imagename);
		return loaded;
	}

	public static Sprite getSprite(Entity entity) {
		return sprites.get(entity.getClass().getSimpleName());
	}
	public static Sprite getSprite(String spriteName) {
		return sprites.get(spriteName);
	}
	public static Image getImage(String imageName) {
		return images.get(imageName);
	}
	// File reader for images file
	protected static class ImagesFileReader extends SequentialFileReader {
		private String curCategory = null;
		public ImagesFileReader(File file) {
			super(file);
			cleanLines = true;
			ignoreBlankLines = true;
		}

		protected void processLine(String curLine, int lineNumber) {
			if (curLine.indexOf('.') > 0) { // has delim and text before the delim, is file name
				if (curCategory == null) {
					Utility.printWarning("Loaded an image without a name on line " + lineNumber +".");
				} else {
					if (images.get(curCategory) != null) {
						Utility.printError("Duplicate image for \"" + curCategory + "\" on line " + lineNumber +".");
					}
					try {
						images.put(curCategory, loadImage(curLine));
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			} else {
				curCategory = curLine;
			}
		}
	}
	// File reader for sprites file
	protected static class SpritesFileReader extends SequentialFileReader {
		private String curCategory = null;
		private Sprite curSprite = null;
		public SpritesFileReader(File file) {
			super(file);
			cleanLines = true;
			ignoreBlankLines = true;
		}

		protected void processLine(String curLine, int lineNumber) {
			if (curLine.indexOf('.') > 0) { // has delim and text before the delim, is file name
				int delimPos = curLine.indexOf(' '); // delim between image "name" and image path
				if (delimPos == -1) {
					if (curCategory == null) {
						Utility.printWarning("Loaded a sprite image without a category on line " + lineNumber + " of " + file);
					} else {
						try {
							curSprite.addFrame(loadImage(curLine));
						} catch (SlickException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (curCategory == null) {
						Utility.printError("Syntax error: Sprite image \"" + curLine + "\" not named in " + SPRITE_LIST + " on line " + lineNumber + " of " + file);
					} else {
						float delay = Utility.getFloat(curLine.substring(delimPos+1), -1);
						if (delay <= 0) {
							try {
								curSprite.addFrame(curLine.substring(0,delimPos), loadImage(curLine.substring(delimPos+1)));
							} catch (SlickException e) {
								e.printStackTrace();
							}
						} else {
							curSprite.setDelay(curLine.substring(0,delimPos), (long)(Settings.valueInt("fps")*delay));
						}
					}
				}
			} else {
				if (curCategory != null) {
					sprites.put(curCategory, curSprite);
				}
				curCategory = curLine;
				curSprite = getSprite(curCategory);
				if (curSprite == null) {
					curSprite = new Sprite();
				}
			}
			if (curCategory != null) {
				sprites.put(curCategory, curSprite);
			}
		}
	}
}
