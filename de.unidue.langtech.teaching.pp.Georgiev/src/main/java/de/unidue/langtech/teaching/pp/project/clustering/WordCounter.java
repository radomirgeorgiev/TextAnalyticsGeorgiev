package de.unidue.langtech.teaching.pp.project.clustering;

public class WordCounter {

	private int wordCounter;
	public WordCounter() {
		wordCounter = 0;
	}
	public void increment(){
		wordCounter++;
	}
	public int getCount(){
		return wordCounter;
	}
	public String toString(){
		return String.valueOf(wordCounter);
	}

}
