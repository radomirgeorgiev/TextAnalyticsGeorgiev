package de.unidue.langtech.teaching.pp.project.clustering;

import java.util.LinkedList;

public class DocumentWords {

	private LinkedList<WordFrequency> wordsLinkedList;

	public DocumentWords() {
		wordsLinkedList = new LinkedList<WordFrequency>();
	}

	public void addWords(String word_, double data_) {
		addWords(new WordFrequency(word_, data_));
	}

	public void addWords(WordFrequency newWords) {
		if (wordsLinkedList.isEmpty()) {
			wordsLinkedList.add(newWords);
		} else if (wordsLinkedList.getLast().compareTo(newWords) < 0) {
			wordsLinkedList.addLast(newWords);
		} else {
			int index = 0;
			while ((index < wordsLinkedList.size()) && (wordsLinkedList.get(index).compareTo(newWords)) < 0) {
				index++;
				if (index >= wordsLinkedList.size()) {
					wordsLinkedList.addLast(newWords);
				} else if (wordsLinkedList.get(index).compareTo(newWords) == 0) {
					wordsLinkedList.set(index, newWords);
				} else {
					wordsLinkedList.set(index, newWords);
				}
			}
		}
	}

	public double getWordValue(String word_) {
		int index = wordsLinkedList.indexOf(new WordFrequency(word_, 0.0));
		if (index < 0) {
			return 0.0;
		} else {
			return wordsLinkedList.get(index).getData();
		}
	}

	public double getWordValue(int index_) {
		if ((index_ < 0) || index_ >= wordsLinkedList.size()) {
			System.out.println("Error!");
		}
		return wordsLinkedList.get(index_).getData();

	}

	public String getWordByIndex(int index_) {
		if ((index_ < 0) || index_ >= wordsLinkedList.size()) {
			System.out.println("Error!");
		}
		return wordsLinkedList.get(index_).getWord();
	}

	public void scaleData(int index_, double scale_) {
		if ((index_ < 0) || index_ >= wordsLinkedList.size()) {
			System.out.println("Error!");
		}
		WordFrequency temp = wordsLinkedList.get(index_);
		wordsLinkedList.set(index_, new WordFrequency(temp.getWord(), temp.getData() * scale_));
	}

	public void removeWord(int index_) {
		if ((index_ < 0) || index_ >= wordsLinkedList.size()) {
			System.out.println("Error!");
		}
		wordsLinkedList.remove(index_);
	}

	public void removeWord(String word_) {
		int index = wordsLinkedList.indexOf(new WordFrequency(word_, 0.0));
		if (index >= 0)
			wordsLinkedList.remove(index);
	}

	public double findSimilarity(DocumentWords other) {

		int index1 = 0;
		int index2 = 0;
		double result = 0.0;
		while ((index1 < wordsLinkedList.size()) && (index2 < other.wordsLinkedList.size())) {

			int compare = wordsLinkedList.get(index1).compareTo(other.wordsLinkedList.get(index2));
			if (compare < 0)
				index1++;
			else if (compare > 0)
				index2++;
			else {
				result += (wordsLinkedList.get(index1).getData() * other.wordsLinkedList.get(index2).getData());
				index1++;
				index2++;
			}
		}
		return result;
	}

	public void normalize() throws Exception {

		double similarity = findSimilarity(this);

		similarity = Math.sqrt(similarity);
		similarity = 1.0 / similarity;
		int index;
		for (index = 0; index < size(); index++)
			scaleData(index, similarity);
	}

	public int size() {
		return wordsLinkedList.size();
	}
    public boolean isEmpty()
    {
        return wordsLinkedList.size() == 0;
    }

    public String toString()
    {
        return wordsLinkedList.toString();
    }

}
