package de.unidue.langtech.teaching.pp.project.entityDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Klasse StopwordRemover.
 */
public class StopwordRemover {

	private static String fileName = "src/test/resources/collection/stopwordsLong";

	private List<String> wordList = null;

	/**
	 * Etstelle Instanz von StopwordRemover Liest ein Dokument von gegebenen
	 * Ordner und speichert die Daten in einer Kette
	 *
	 * @throws Exception
	 */
	public StopwordRemover() throws Exception {
		wordList = new ArrayList<String>();
		try {
			String tempList = FileUtils.readFileToString(new File(fileName));
			if (tempList != null) {
				String[] words = tempList.split("\r\n");
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

	/**
	 * Prüfe ob ein Wort in der gegebene Liste ein Stopwort ist
	 *
	 * @param word
	 * @return gibt true zurück, wenn wahr ist, sonst false
	 */
	public boolean isStopWord(String word) {
		if (wordList == null) {
			return false;
		} else {
			return wordList.contains(word);
		}
	}

}
