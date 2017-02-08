package de.unidue.langtech.teaching.pp.project.clustering;

public class WordFrequency implements Comparable<WordFrequency>{

	private String word;
	private double data;
	
	public WordFrequency(String word_, double data_){
		if(word_==null){
			System.out.println("Error!");
		}else if(word_.isEmpty()){
			System.out.println("Error!");
		}else{
			word = word_;
			data = data_;
		}
	}
	
	@Override
	public int compareTo(WordFrequency otherWord) {
		return  word.compareTo(otherWord.word);
	}

	public String getWord() {
		return word;
	}

	public double getData() {
		return data;
	}
	
	public String toString(){
		return this.word + "=" + this.data;
	}

}
