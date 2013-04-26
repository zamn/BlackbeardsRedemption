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
	public static final Pattern REGEX_POS_TYPE = Pattern.compile("([a-zA-Z0-9]+) ([a-zA-Z0-9]+) at ([0-9]+),([0-9]+)"); // object word at position,position
	public static final Pattern REGEX_SIZE_POS_TYPE = Pattern.compile("([a-zA-Z0-9]+) ([a-zA-Z0-9]+) ([0-9]+)x([0-9]+) at ([0-9]+),([0-9]+)"); // object word size at position
	public static final Pattern REGEX_BACKGROUND = Pattern.compile("Background ([a-zA-Z0-9]+)");
	public static final Pattern REGEX_MUSIC = Pattern.compile("Music ([a-zA-Z0-9]+)");
	protected Level level = new Level();

	public LevelFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	public Level getLevel() { return level; }

	@Override
	protected void processLine(String curLine, int lineNumber) {
		Matcher sizePosMatcher = REGEX_SIZE_POS.matcher(curLine);
		Matcher spawnMatcher = REGEX_PLAYER_START.matcher(curLine);
		Matcher posMatcher = REGEX_POS.matcher(curLine);
		Matcher backgroundMatcher = REGEX_BACKGROUND.matcher(curLine);
		Matcher posTypeMatcher = REGEX_POS_TYPE.matcher(curLine);
		Matcher sizePosTypeMatcher = REGEX_SIZE_POS_TYPE.matcher(curLine);
		Matcher musicMatcher = REGEX_MUSIC.matcher(curLine);

		String entityName;
		int px, py;
		int sx, sy;

		if(musicMatcher.matches()){
			Utility.log(musicMatcher.group(1));
			Utility.log(musicMatcher.group(1));
			level.setMusic(musicMatcher.group(1));
		} else if(backgroundMatcher.matches()){
			level.setBackground(backgroundMatcher.group(1));
		} else if (spawnMatcher.matches()) {
			px = Utility.getInt(spawnMatcher.group(1), -1);
			py = Utility.getInt(spawnMatcher.group(2), -1);
			level.setSpawnPoint(px, py);
		} else if (posMatcher.matches()) {
			entityName = posMatcher.group(1);
			px = Utility.getInt(posMatcher.group(2), -1);
			py = Utility.getInt(posMatcher.group(3), -1);
			level.addEntityEvent(entityName, null, px, py);
		} else if (sizePosMatcher.matches()) {
			entityName = sizePosMatcher.group(1);
			sx = Utility.getInt(sizePosMatcher.group(2), -1);
			sy = Utility.getInt(sizePosMatcher.group(3), -1);
			px = Utility.getInt(sizePosMatcher.group(4), -1);
			py = Utility.getInt(sizePosMatcher.group(5), -1);
			level.addEntityEvent(entityName, null, sx, sy, px, py);
		} else if (sizePosTypeMatcher.matches()) {
			entityName = sizePosTypeMatcher.group(1);
			sx = Utility.getInt(sizePosTypeMatcher.group(3), -1);
			sy = Utility.getInt(sizePosTypeMatcher.group(4), -1);
			px = Utility.getInt(sizePosTypeMatcher.group(5), -1);
			py = Utility.getInt(sizePosTypeMatcher.group(6), -1);
			level.addEntityEvent(entityName, sizePosTypeMatcher.group(2), sx, sy, px, py);
		} else if (posTypeMatcher.matches()) {
			entityName = posTypeMatcher.group(1);
			px = Utility.getInt(posTypeMatcher.group(3), -1);
			py = Utility.getInt(posTypeMatcher.group(4), -1);
			level.addEntityEvent(entityName, posTypeMatcher.group(2), px, py);
		}
	}
}
