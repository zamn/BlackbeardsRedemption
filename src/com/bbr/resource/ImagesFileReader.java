package com.bbr.resource;

import java.io.File;

import org.newdawn.slick.SlickException;

// File reader for images file
public class ImagesFileReader extends SequentialFileReader {
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
				if (Art.images.get(curCategory) != null) {
					Utility.printError("Duplicate image for \"" + curCategory + "\" on line " + lineNumber +".");
				}
				try {
					Art.images.put(curCategory, Art.loadImage(curLine));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		} else {
			curCategory = curLine;
		}
	}
}