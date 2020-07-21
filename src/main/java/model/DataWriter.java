package main.java.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
	
	File file; 
	
	public DataWriter() {
		createFile(); 
	}
	
	public void createFile() {
		try {
			file = new File("collectedData.txt");
			file.createNewFile(); 
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void writeToFile(DataObject data) {
		 if(file==null) {
			 System.out.println("Data konnte nicht abgespeichert werden!"); 
			 return;
		 }
		 try {
			  //append to the file dont overwrite!
		      FileWriter myWriter = new FileWriter(file, true);
		      myWriter.write(data.toString());
		      myWriter.close();
		 }catch (IOException e) {
		      System.out.println("Data konnte nicht in die Datei geschrieben werden.");
		      e.printStackTrace();
		 }
	}
}
