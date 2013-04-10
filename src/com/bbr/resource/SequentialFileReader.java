package com.bbr.resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// simplifies file parsing
public abstract class SequentialFileReader {
	protected Scanner fileReader;
	protected File file;
	protected boolean processed = false;
	// these settings should always default to false for consistency
	//  and by default false should have no effect on line processing
	// which is why it is ignoreBlankLines instead of processBlankLines
	protected boolean cleanLines = false; // trims and removes comments
	protected boolean ignoreBlankLines = false;
	//
	public SequentialFileReader(File file) {
		this.file = file;
	}
	public boolean readFile() throws FileNotFoundException {
		if (processed) return false;
		// process file
		fileReader = new Scanner(file);
		processFile();
		fileReader.close();
		processed = true;
		return true;
	}
	private void processFile() {
		int lineNumber = 0;
		String curLine;
		while (fileReader.hasNextLine()) {
			lineNumber++;
			curLine = fileReader.nextLine();
			if (cleanLines) {
				curLine = Utility.cleanTextString(curLine);
			}
			if (curLine.length() > 0 || !ignoreBlankLines) {
				processLine(curLine, lineNumber);
			}
		}
		endOfFile();
		//System.out.println();
	}
	protected abstract void processLine(String curLine, int lineNumber);
	protected void endOfFile() { } // called when reached end of file
	//
	public File getFile() {
		return file;
	}
}
