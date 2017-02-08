package de.unidue.langtech.teaching.pp.project.clustering;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class StopwordRemover {

	private static String fileName = "src/test/resources/collection/stopwords";
	private static StopwordRemover stopWords = null;
	private Set<String> wordList = null;

	public StopwordRemover() throws Exception {
		wordList = new HashSet<String>();
		try {
			String tempList = FileUtils.readFileToString(new File(fileName));
			if (tempList != null) {
				String[] words = tempList.split(",");
				if (words.length > 0) {
					for (int i = 0; i < words.length; i++) {
						wordList.add(words[i]);
					}
				} else {
					System.out.println("Error!");
				}
			} else {
				System.out.println("Error!");
			}
		} catch (Exception e) {
			wordList.clear();
		}
	}
	
	public boolean isStopWord(String word){
		if(wordList == null){
			return false;
		} else {
			return (wordList.contains(word));
		}
	}

	public static StopwordRemover getStopWordsFromFile() throws Exception {
		if (stopWords == null) {
			try {
				stopWords = new StopwordRemover();
			} catch (Exception e) {
				stopWords = null;
				throw e;
			}
		}
		return stopWords;
	}

}
