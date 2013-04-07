package com.bbr.resource;

public final class Utility {
	private Utility() { }

	public static void printWarning(Object warning) {
		System.out.println("[WAR] " + warning);
	}
	public static void printError(Object error) {
		System.err.println("[ERR] " + error);
	}
	/**
	 * Removes comments and trims a <code>String</code>.<br>
	 * Intended to be used on <code>String</code>s parsed from text data files.
	 */
	public static String cleanTextString(String fileText) {
		if (fileText.contains("//")) {
			return fileText.substring(0, fileText.indexOf("//")).trim();
		}
		return fileText;
	}
	public static int getInt(String number, int failVal) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException ex) {
			return failVal;
		}
	}
	public static float getFloat(String number, float failVal) {
		try {
			return Float.parseFloat(number);
		} catch (NumberFormatException ex) {
			return failVal;
		}
	}
}
