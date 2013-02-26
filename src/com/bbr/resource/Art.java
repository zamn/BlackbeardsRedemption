package com.bbr.resource;


import java.awt.Component;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.bbr.core.Flyer;

// TODO: load fonts
public final class Art {
	private static final File IMAGE_LIST = new File("data/imagelist.txt");
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
	public static void load() { // Not in a static block due to use of MediaTracker 
		if (loaded) {
			Utility.printWarning("Loading art files after already having loaded art files.");
		}
		loadImages();
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
	private static Image loadImage(String imagename) throws SlickException {
		Image loaded = new Image(imagename);
		return loaded;
	}
	//
	public static Image getImage(Flyer flyer) {
		return images.get(flyer.getClass().getSimpleName());
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
		//
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				curCategory = curLine;
			}
		}
	}
}
