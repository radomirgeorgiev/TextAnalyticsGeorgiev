package de.unidue.langtech.teaching.pp.project.languageDetector;

public class Language {

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	private String language;
	private double value;

	public Language(String language_, double value_) {
		this.language = language_;
		this.value = value_;
	}

}
