package de.unidue.langtech.teaching.pp.project.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

/**
 * Klasse EntityReader.
 */
public class EntityReader {

	 private List<String> lines;
	    private File file;
	    private String input = "data/corpus_";
		private String storageFolder = "src/test/resources/storage/";
		private String corpusID;
	
	/**
	 * Read entities. Liest Entities von InputFolder
	 * 
	 * @return list
	 * @throws wirft
	 *             IOException Signals, dass I/O exception vorgekommen ist.
	 */
	    public List<String> readEntities() throws IOException{
	    	loadCorpusID();
	    	String inputFolder = input+corpusID+"/list_target_entities_corpus_"+corpusID+".txt";
	    	file = new File(inputFolder);
	    	return setLines(FileUtils.readLines(file));
	    }
	/**
	 * Gets the lines. Gibt die Zeilen eines Dokumentes
	 * 
	 * @return gibt die Zeile zurück
	 */
	public List<String> getLines() {
		return lines;
	}

	/**
	 * Sets the lines. Setzt eine Zeile im Dokument
	 * 
	 * @param lines
	 * @return gibt die Zeile zurück
	 */
	public List<String> setLines(List<String> lines) {
		this.lines = lines;
		return lines;
	}
	
	private void loadCorpusID() {
		String fileName = "corpusID";
		File file = new File(storageFolder + fileName);
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNext()){
			corpusID = s.next();
		}
	}

}
