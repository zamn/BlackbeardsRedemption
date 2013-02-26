package com.bbr.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JOptionPane;

// Stores user settings read from settings file
// Case-insensitive (stored as ALLCAPS) for key (sensitive for value)
// Note: DO NOT DIRECTLY put(key, value) W/O ALLCAPPING KEY FIRST!
public final class Settings {
	public static final File SETTINGS_FILE = new File("data/settings.txt");
	private static HashMap<String, String> settings = new HashMap<String, String>();
	private enum VariableType {STRING, BOOLEAN, INTEGER}; // STRING = no restriction
	private static SequentialFileReader settingsReader;
	//
	private Settings() { }
	//
	static {
		loadSettings();
	}
	//
	public static String value(String key) {
		return settings.get(key.toUpperCase());
	}
	public static boolean valueBoolean(String key) {
		return Boolean.parseBoolean(value(key));
	}
	public static int valueInt(String key) {
		return Integer.parseInt(value(key));
	}
	//
	private static void loadSettings() {
		settingsReader = new SettingsFileReader(SETTINGS_FILE);
		try {
			settingsReader.readFile();
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Settings file missing from " + SETTINGS_FILE + "! Game cannot load and will now exit. Perhaps the data folder is missing?",
					"Missing Settings File", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		//
		/*Scanner reader = null;
		try {
			reader = new Scanner(new File(SETTINGS_FILEPATH));
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Settings file missing from " + SETTINGS_FILEPATH + "! Game cannot load and will now exit. Perhaps the data folder is missing?",
					"Missing Settings File", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		// process settings file
		int lineNumber = 0; // for display in error messages
		String curLine;
		String curKey, curValue;
		while (reader.hasNextLine()) {
			curLine = Utility.cleanTextString(reader.nextLine());
			lineNumber++;
			//
			if (curLine.length() > 0) {
				if (curLine.contains("=")) {
					curKey = curLine.substring(0, curLine.indexOf('=')).trim().toUpperCase();
					curValue = curLine.substring(curLine.indexOf('=') + 1).trim(); // Notice: case NOT discarded
					settings.put(curKey, curValue);
				} else {
					Utility.printError("Setting \"" + curLine + "\" on line " + lineNumber + " lacks a value, ignoring line.");
				}
			}
		}*/
		verifySettings();
	}
	// helper of loadSettings(), verify type of settings, ex: boolean, int
	private static void verifySettings() {
		verify("fps", "integer", "50");
		verify("screen_width", "integer", "800");
		verify("screen_height", "integer", "600");
	}
	/**
	 * Ensures that the value for the given <code>key</code> matches the given variable <code>type</code>.<br>
	 * If the value for the <code>key</code> is of another type, or the value is <code>null</code>,
	 *  the value is set to <code>defaultValue</code>.<br> 
	 * <br>
	 * Gotchas (no handholding provided for these)<br>
	 * - type = "String" only checks to see if the key has a value<br>
	 * - make sure <code>defaultValue</code> is of type <code>type</code>
	 */
	private static void verify(String key, String type, String defaultValue) {
		key = key.toUpperCase();
		String value = settings.get(key);
		//
		if (value == null) {
			settings.put(key, defaultValue);
		} else {
			switch (VariableType.valueOf(type.toUpperCase())) {
			case STRING: break; // No check necessary
			case BOOLEAN:
				if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
					settings.put(key, defaultValue);
				}
				break;
			case INTEGER:
				try {
					Integer.parseInt(value);
				} catch (NumberFormatException ex) {
					settings.put(key, defaultValue);
				}
				break;
			default:
				Utility.printError("Programmer hardcoded error: Type \"" + type + "\" not supported in verify(String,String,String).");
				break;
			}
		}
	}
	// File reader for settings file
	protected static class SettingsFileReader extends SequentialFileReader {
		public SettingsFileReader(File file) {
			super(file);
			cleanLines = true;
			ignoreBlankLines = true;
		}
		//
		protected void processLine(String curLine, int lineNumber) {
			String curKey, curValue;
			if (curLine.contains("=")) {
				curKey = curLine.substring(0, curLine.indexOf('=')).trim().toUpperCase();
				curValue = curLine.substring(curLine.indexOf('=') + 1).trim(); // Notice: case NOT discarded
				settings.put(curKey, curValue);
			} else {
				Utility.printError("Setting \"" + curLine + "\" on line " + lineNumber + " lacks a value, ignoring line.");
			}
		}
	}
}
