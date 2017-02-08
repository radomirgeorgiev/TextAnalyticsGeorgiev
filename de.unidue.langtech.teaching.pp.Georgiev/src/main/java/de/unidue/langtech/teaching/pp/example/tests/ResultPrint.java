package de.unidue.langtech.teaching.pp.example.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class ResultPrint {

	
	private static String resultFolder = "src/test/resources/result/";
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		try(Stream<Path> paths = Files.walk(Paths.get(resultFolder))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            System.out.println("------>"+filePath.getFileName()+"<-------");
		            readTimeLine(filePath.getFileName().toString());
		        }
		    });
		} 
		
	}
	
	public static void readTimeLine(String file) {
		HashMap<String, List<String>> tempHM = new HashMap<String, List<String>>();
		
		try {
			FileInputStream fileIn = new FileInputStream(resultFolder + file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			tempHM = (HashMap<String, List<String>>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("File not Found\n");
			c.printStackTrace();

		}
		
		for (Entry<String, List<String>> entry : tempHM.entrySet()) {
		    String key = entry.getKey();
		    List<String> value = (List<String>) entry.getValue();
		    for(String str : value){
		    	System.out.println(key + " " + str);
		    }
		    // ...
		}
	}

}
