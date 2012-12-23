package menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class SaveFile {
	private static HashMap<String, String> localData;
	private static String filename;
	public static void setSave(int num){
		filename="save"+num+".txt";
		localData=new HashMap<String, String>();
	}
	public static void set(String key, String data) throws IOException{
		if(filename==null)
			throw new IOException();
		if(key.indexOf("=") != -1 || data.indexOf("=") != -1 )
			throw new IOException();
		localData.put(key, data);
	}
	public static String get(String key) throws IOException{
		if(filename==null)
			throw new IOException();
		return localData.get(key);
	}
	public static String returnString() throws IOException{
		if(filename==null)
			throw new IOException();
		return localData.toString();
	}
	public static void save() throws IOException{
		if(filename==null)
			throw new IOException();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			Iterator<String> keys = localData.keySet().iterator();		
			while(keys.hasNext()){
				String current = keys.next();
				String nextLine = current+"="+localData.get(current);
				writer.write(nextLine+"\n", 0, nextLine.length()+1);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error Saving: "+filename);
			e.printStackTrace();
		}
	}
	public static void loadSave() throws IOException{
		if(filename==null)
			throw new IOException();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while(reader.ready()){
				String currentLine=reader.readLine();
				int equalsSign=currentLine.indexOf("=");
				String key=currentLine.substring(0, equalsSign);
				int endLine=currentLine.length();
				String data = currentLine.substring(equalsSign+1, endLine);
				localData.put(key, data);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Tried getting a save file that doesn't exist");
		} catch (IOException e) {
			System.out.println("Error Reading: "+filename);
			System.out.println("Error Thrown: "+e.toString());
			e.printStackTrace();
		}
	}
}
