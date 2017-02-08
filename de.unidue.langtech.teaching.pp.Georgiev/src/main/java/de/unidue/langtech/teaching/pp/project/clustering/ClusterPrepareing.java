package de.unidue.langtech.teaching.pp.project.clustering;

import java.util.HashMap;

public class ClusterPrepareing {

	public HashMap<String, WordCounter> getWordFrequencies(String text) throws Exception {
		HashMap<String, WordCounter> results = new HashMap<String, WordCounter>();

		String[] temp = text.split(" ");
		for (int i = 0; i < temp.length; i++) {
			WordCounter count = results.get(temp[i].toLowerCase());
			if (count == null) {
				count = new WordCounter();
				results.put(temp[i].toLowerCase(), count);
			}
			count.increment();
		}

		return results;
	}
}
