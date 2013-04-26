package com.bbr.resource;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.SlickException;

// File reader for images file
public class SongFileReader extends SequentialFileReader {
	public static final Pattern REGEX_SONG_NAME = Pattern.compile("(\\w+)"); // name
	public static final Pattern REGEX_SONG_FILE = Pattern.compile("([^\\.]+\\.[a-zA-Z]+)"); // filename
	private String curCategory = null;

	public SongFileReader(File file) {
		super(file);
		cleanLines = true;
		ignoreBlankLines = true;
	}

	@Override
	protected void processLine(String curLine, int lineNumber) {
		Matcher imageNameMatcher = REGEX_SONG_NAME.matcher(curLine);
		Matcher imageFileMatcher = REGEX_SONG_FILE.matcher(curLine);

		if (imageFileMatcher.matches()) {
			if (curCategory == null) {
				Utility.printWarning("Loaded a song without a name on line " + lineNumber +".");
			} else {
				if (Song.songs.get(curCategory) != null) {
					Utility.printError("Duplicate song for \"" + curCategory + "\" on line " + lineNumber +".");
				}
				try {
					Song.songs.put(curCategory, Song.loadSong(imageFileMatcher.group(1)));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		} else if (imageNameMatcher.matches()) {
			curCategory = imageNameMatcher.group(1);
		}
	}
}