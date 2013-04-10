package com.bbr.resource;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.SlickException;

// File reader for images file
public class ImagesFileReader extends SequentialFileReader {
	public static final Pattern REGEX_IMAGE_NAME = Pattern.compile("(\\w+)"); // name
	public static final Pattern REGEX_IMAGE_FILE = Pattern.compile("([^\\.]+\\.[a-zA-Z]+)"); // filename
	private String curCategory = null;

	public ImagesFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	protected void processLine(String curLine, int lineNumber) {
		Matcher imageNameMatcher = REGEX_IMAGE_NAME.matcher(curLine);
		Matcher imageFileMatcher = REGEX_IMAGE_FILE.matcher(curLine);

		if (imageFileMatcher.matches()) {
			if (curCategory == null) {
				Utility.printWarning("Loaded an image without a name on line " + lineNumber +".");
			} else {
				if (Art.images.get(curCategory) != null) {
					Utility.printError("Duplicate image for \"" + curCategory + "\" on line " + lineNumber +".");
				}
				try {
					Art.images.put(curCategory, Art.loadImage(imageFileMatcher.group(1)));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		} else if (imageNameMatcher.matches()) {
			curCategory = imageNameMatcher.group(1);
		}
		/*if (curLine.indexOf('.') > 0) { // has delim and text before the delim, is file name
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
		}*/
	}
}