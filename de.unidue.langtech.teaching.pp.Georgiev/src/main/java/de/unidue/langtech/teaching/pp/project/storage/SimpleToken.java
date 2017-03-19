package de.unidue.langtech.teaching.pp.project.storage;

/**
 * Klasse SimpleToken hat das Ziel für jeden Token zu bestimmen zu welchem Satz
 * er genau gehoert
 */
public class SimpleToken extends Component {

	private static final long serialVersionUID = 1L;

	private int tokenNumber;

	private int sentenceNumber;

	private int tokenID;

	private String tokenText;

	/**
	 * Erstellt Instanz von SimpleToken
	 *
	 * @param tokenNumber_
	 * @param sentenceNumber_
	 * @param tokenID_
	 * @param tokenText_
	 */
	public SimpleToken(int tokenNumber_, int sentenceNumber_, int tokenID_, String tokenText_) {

		this.tokenNumber = tokenNumber_;
		this.sentenceNumber = sentenceNumber_;
		this.tokenID = tokenID_;
		this.tokenText = tokenText_;

	}

	/**
	 * Holt tokenNumber
	 *
	 * @return gibt tokenNumber zurück
	 */
	public int getTokenNumber() {
		return tokenNumber;
	}

	/**
	 * Holt sentenceNumber
	 *
	 * @return gibt sentenceNumber zurück
	 */
	public int getSentenceNumber() {
		return sentenceNumber;
	}

	/**
	 * Holt tokenID
	 *
	 * @return gibt tokenID zurück
	 */
	public int getTokenID() {
		return tokenID;
	}

	/**
	 * Holt tokenText.
	 *
	 * @return gibt tokenText zurück
	 */
	public String getTokenText() {
		return tokenText;
	}

}
