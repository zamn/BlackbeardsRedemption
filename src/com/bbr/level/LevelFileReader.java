package com.bbr.level;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbr.resource.SequentialFileReader;
import com.bbr.resource.Utility;

public class LevelFileReader extends SequentialFileReader {
	public static final Pattern REGEX_PLAYER_START = Pattern.compile("spawn at ([0-9]+),([0-9]+)");
	public static final Pattern REGEX_POS = Pattern.compile("([a-zA-Z0-9]+) at ([0-9]+),([0-9]+)");
	public static final Pattern REGEX_SIZE_POS = Pattern.compile("([a-zA-Z0-9]+) ([0-9]+)x([0-9]+) at ([0-9]+),([0-9]+)");

	protected Level level = new Level();

	public LevelFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
		// TODO keep track of a level and edit it
	}

	protected void processLine(String curLine, int lineNumber) {
		// TODO Auto-generated method stub
		int px, py;
		int sx, sy;
		Matcher spawnMatcher = REGEX_PLAYER_START.matcher(curLine);
		Matcher posMatcher = REGEX_POS.matcher(curLine);
		Matcher sizePosMatcher = REGEX_SIZE_POS.matcher(curLine);
		if (spawnMatcher.matches()) {
			px = Utility.getInt(spawnMatcher.group(1), -1);
			py = Utility.getInt(spawnMatcher.group(2), -1);
			if (px >= 0 && py >= 0) {
				level.setSpawnPoint(px, py);
			} // TODO else error
		} else if (posMatcher.matches()) {
		}
	}
}
