package de.unidue.langtech.teaching.pp.project.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EntityReader {

    private List<String> lines;
    private File file;
    private String inputFolder = "/Volumes/Macintosh HD/Users/biciani/Desktop/SemEval_Task4_data/corpus_1/list_target_entities_corpus_1.txt";
    
    
    public List<String> readEntities() throws IOException{
    	file = new File(inputFolder);
    	return setLines(FileUtils.readLines(file));
    }

	public List<String> getLines() {
		return lines;
	}

	public List<String> setLines(List<String> lines) {
		this.lines = lines;
		return lines;
	}    
    
}
