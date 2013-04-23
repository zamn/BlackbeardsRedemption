package com.bbr.save;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class SaveFile{
	public static void save(String name, Object obj)
		throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		if (name == null) {
			throw new NullPointerException("Filename is null");
		}

		try {
			fos = new FileOutputStream(new File(name+".sav"));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file "+name+".sav");
		} catch (IOException e) {
			throw new IOException("Error saving to "+name+".sav");
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					throw new RuntimeException("This should never happen", e);
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch(IOException e) {
					throw new RuntimeException("This should never happen", e);
				}
			}
		}
	}
	
	public static Object load(String name) 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Object obj;
		
		if(name == null) {
			throw new NullPointerException("Filename is null");
		}
		
		try {
			fis = new FileInputStream(new File(name+".sav"));
			ois = new ObjectInputStream(fis);
			obj = ois.readObject();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file"+name+".sav");
		} catch (IOException e) {
			throw new IOException("Error reading "+name+".sav");
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					throw new RuntimeException("This should never happen", e);
				}
			}
			if(fis != null) {
				try {
					fis.close();
				} catch(IOException e) {
					throw new RuntimeException("This should never happen", e);
				}
			}
		}	
		return obj;
	}
}
