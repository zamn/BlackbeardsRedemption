package com.bbr.resource;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.SlickException;

import com.bbr.core.Sprite;

// File reader for sprites file
public class SpritesFileReader extends SequentialFileReader {
	public static final Pattern REGEX_SPRITE_NAME = Pattern.compile("(\\w+)"); // name
	public static final Pattern REGEX_FRAME_OFFSETS = Pattern.compile("([^\\.]+\\.[a-zA-Z]+)\\s+offsets:([-]?\\d+),([-]?\\d+)"); // filename hitbox
	public static final Pattern REGEX_DEFAULT_FRAME = Pattern.compile("([^\\.]+\\.[a-zA-Z]+)"); // filename
	public static final Pattern REGEX_SPECIFIC_FRAME = Pattern.compile("(\\w+)\\s+([^\\.]+\\.[a-zA-Z]+)"); // category filename
	public static final Pattern REGEX_SPECIFIC_DELAY = Pattern.compile("(\\w+)\\s+(\\d*\\.?\\d*)"); // category delay

	private Sprite curSprite = null;
	private String curName = null; // sprite name

	public SpritesFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	@Override
	protected void processLine(String curLine, int lineNumber) {
		Matcher spriteNameMatcher = REGEX_SPRITE_NAME.matcher(curLine);
		Matcher frameOffsetsMatcher = REGEX_FRAME_OFFSETS.matcher(curLine);
		Matcher defaultFrameMatcher = REGEX_DEFAULT_FRAME.matcher(curLine);
		Matcher specificFrameMatcher = REGEX_SPECIFIC_FRAME.matcher(curLine);
		Matcher specificDelayMatcher = REGEX_SPECIFIC_DELAY.matcher(curLine);

		if (curSprite == null) {
			curSprite = new Sprite();
		}

		if (frameOffsetsMatcher.matches()) {
			int offsetX = Utility.getInt(frameOffsetsMatcher.group(2), 0);
			int offsetY = Utility.getInt(frameOffsetsMatcher.group(3), 0);
			try {
				curSprite.setOffsets(Art.loadImage(frameOffsetsMatcher.group(1)), offsetX, offsetY);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (specificFrameMatcher.matches()) {
			try {
				if (specificFrameMatcher.group(1).equals(curName)) {
					curSprite = new Sprite();
					curSprite.addFrame(Art.loadImage(specificFrameMatcher.group(2)));
					Art.addSprite(curName, curSprite);
				} 
				else {
					curSprite.addFrame(specificFrameMatcher.group(1), Art.loadImage(specificFrameMatcher.group(2)));
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (specificDelayMatcher.matches()) {
			float delay = Utility.getFloat(specificDelayMatcher.group(2), -1);
			curSprite.setDelay(specificDelayMatcher.group(1), (int)(Settings.valueInt("fps")*delay));
		} else if (defaultFrameMatcher.matches()) {
			try {
				curSprite.addFrame(Art.loadImage(defaultFrameMatcher.group(1)));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (spriteNameMatcher.matches()) {
			if (curName != null) {
				Art.addSprite(curName, curSprite);
//				Utility.log("curName: " + curName + " - sprites: " + Art.getSprites(curName));
				curSprite = new Sprite();
			}
			curName = spriteNameMatcher.group(1);
		}
	}

	@Override
	protected void endOfFile() {
		if (curName != null) {
			Art.addSprite(curName, curSprite);
			curSprite = null;
		}
	}
}