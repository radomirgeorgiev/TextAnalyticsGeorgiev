package de.unidue.langtech.teaching.pp.project.languageDetector;

/**
 * Klasse Sprache
 */
public class Language {

	private String language;
	private double value;

	/**
	 * Initialisiert neue Sprache .
	 *
	 * @param language_
	 *            die vorgeschlagene Sprache
	 * @param value_
	 *            Kosinus-Wert
	 */
	public Language(String language_, double value_) {
		this.language = language_;
		this.value = value_;
	}

	/**
	 * Die Methode gibt die Sprache zurück
	 *
	 * @return die Sprache
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setzt die Sprache
	 *
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * die Methode gibt den Kosinus-Wert zurück
	 *
	 * @return Kosinus-Wert
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Setzt den Kosinus-Wert
	 *
	 * @param value
	 *            Kosinus-Wert
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
