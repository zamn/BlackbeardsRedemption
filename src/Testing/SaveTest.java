package Testing;

import java.io.IOException;

import menu.SaveFile;

import org.junit.Test;

import junit.framework.TestCase;

public class SaveTest extends TestCase {
	@Test public void test1(){
		SaveFile.setSave(1);
		try {
			SaveFile.set("one", "two");
			SaveFile.set("three", "four");
			SaveFile.save();
			String before=SaveFile.returnString();
			SaveFile.loadSave();
			assertEquals(before, SaveFile.returnString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
