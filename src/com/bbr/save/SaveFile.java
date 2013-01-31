package com.bbr.save;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class SaveFile{
	public static void save(String name, Object obj) throws Exception{
		FileOutputStream fos = null;;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream( new File(name+".sav"));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Error saving "+name);
		} catch (IOException e){
			throw new IOException("Error saving "+name);
		} finally{
			oos.close();
			fos.close();
		}
			
	}
	public static Object load(String name) throws Exception{
		FileInputStream fis = null;
		ObjectInputStream iss = null;
		Object obj;
		try{
			fis = new FileInputStream(new File(name+".sav"));
			iss = new ObjectInputStream(fis);
			obj=iss.readObject();
		} catch (FileNotFoundException e){
			throw new FileNotFoundException("\""+name+"\" not found");
		} catch (IOException e){
			throw new IOException("Error getting "+name+"object");
		} finally{
			fis.close();
			iss.close();
		}	
		return obj;
	}
}
