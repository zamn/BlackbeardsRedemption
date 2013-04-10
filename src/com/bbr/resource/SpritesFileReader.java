package com.bbr.resource;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.SlickException;

import com.bbr.core.Sprite;

// File reader for sprites file
public class SpritesFileReader extends SequentialFileReader {
	public static final Pattern REGEX_SPRITE_NAME = Pattern.compile("(\\w+)"); // name
	public static final Pattern REGEX_DEFAULT_FRAME = Pattern.compile("([^\\.]+\\.[a-zA-Z]+)"); // filename
	public static final Pattern REGEX_SPECIFIC_FRAME = Pattern.compile("(\\w+)\\s+([^\\.]+\\.[a-zA-Z]+)"); // category filename
	public static final Pattern REGEX_SPECIFIC_DELAY = Pattern.compile("(\\w+)\\s+(\\d*\\.?\\d*)"); // category delay
	//private String curCategory = null;
	private Sprite curSprite = null;
	private String curName = null; // sprite name

	public SpritesFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	protected void processLine(String curLine, int lineNumber) {
		Matcher spriteNameMatcher = REGEX_SPRITE_NAME.matcher(curLine);
		Matcher defaultFrameMatcher = REGEX_DEFAULT_FRAME.matcher(curLine);
		Matcher specificFrameMatcher = REGEX_SPECIFIC_FRAME.matcher(curLine);
		Matcher specificDelayMatcher = REGEX_SPECIFIC_DELAY.matcher(curLine);

		if (curSprite == null) {
			curSprite = new Sprite();
		}

		if (specificFrameMatcher.matches()) {
			try {
				System.out.println(curName+":"+specificFrameMatcher.group(1)+"="+specificFrameMatcher.group(2));
				curSprite.addFrame(specificFrameMatcher.group(1), Art.loadImage(specificFrameMatcher.group(2)));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (specificDelayMatcher.matches()) {
			float delay = Utility.getFloat(specificDelayMatcher.group(2), -1);
			curSprite.setDelay(specificDelayMatcher.group(1), (int)(Settings.valueInt("fps")*delay));
		} else if (defaultFrameMatcher.matches()) {
			try {
				System.out.println(curName+":"+defaultFrameMatcher.group(1));
				curSprite.addFrame(Art.loadImage(defaultFrameMatcher.group(1)));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (spriteNameMatcher.matches()) {
			if (curName != null) {
				Art.sprites.put(curName, curSprite);
				curSprite = new Sprite();
			}
			curName = spriteNameMatcher.group(1);
		}
		
		/*if (curLine.indexOf('.') > 0) { // has delim and text before the delim, is file name
			int delimPos = curLine.indexOf(' '); // delim between image "name" and image path
			if (delimPos == -1) {
				if (curCategory == null) {
					Utility.printWarning("Loaded a sprite image without a category on line " + lineNumber + " of " + file);
				} else {
					try {
						curSprite.addFrame(Art.loadImage(curLine));
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (curCategory == null) {
					Utility.printError("Syntax error: Sprite image \"" + curLine + "\" not named in " + Art.SPRITE_LIST + " on line " + lineNumber + " of " + file);
				} else {
					float delay = Utility.getFloat(curLine.substring(delimPos+1), -1);
					if (delay <= 0) {
						delay = Utility.getInt(curLine.substring(delimPos+1), -1);
					}
					if (delay <= 0) {
						try {
							curSprite.addFrame(curLine.substring(0,delimPos), Art.loadImage(curLine.substring(delimPos+1)));
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
				Art.sprites.put(curCategory, curSprite);
			}
			curCategory = curLine;
			curSprite = Art.getSprite(curCategory);
			if (curSprite == null) {
				curSprite = new Sprite();
			}
		}
		if (curCategory != null) {
			Art.sprites.put(curCategory, curSprite);
		}*/
	}
	protected void endOfFile() {
		if (curName != null) {
			Art.sprites.put(curName, curSprite);
			curSprite = null;
		}
	}
}