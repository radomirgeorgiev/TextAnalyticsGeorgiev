package de.unidue.langtech.teaching.pp.project.clustering;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Temp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		String str = FileUtils.readFileToString(new File("src/test/resources/collection/stopwords"));
		String[] temp = str.split(",");
		
		for(int i=0; i<temp.length;i++){
			if(!temp[i].matches("^[.,;:-?!&`]+$")||temp[i].contains("'"))
			System.out.println(temp[i]);
		}
	}

}
