package de.unidue.langtech.teaching.pp.project.clustering;

public class DocumentFrequency {

	private double frequency;
	private int documentIndex;
	
	public DocumentFrequency(double frequency_, int documentIndex_){
		this.frequency=frequency_;
		this.documentIndex=documentIndex_;
	}

	public double getFrequency() {
		return frequency;
	}

	public int getDocumentIndex() {
		return documentIndex;
	}

    public String toString()
    {
        return String.valueOf(documentIndex) + ":" + String.valueOf(frequency);
    }
	
}
