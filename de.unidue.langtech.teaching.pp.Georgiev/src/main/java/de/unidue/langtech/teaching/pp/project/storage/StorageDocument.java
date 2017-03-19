package de.unidue.langtech.teaching.pp.project.storage;

import java.util.List;

/**
 * Klasse StorageDocument.
 */
public class StorageDocument extends Component {

	private static final long serialVersionUID = 1L;

	private List<SimpleToken> listOfSimpleTokens;

	private String docText;

	/**
	 * Erstellt Instanz von StorageDocument.
	 *
	 * @param docText_
	 * @param listOfSimpleTokens_
	 */
	public StorageDocument(String docText_, List<SimpleToken> listOfSimpleTokens_) {
		this.docText = docText_;
		this.listOfSimpleTokens = listOfSimpleTokens_;
	}

	/**
	 * Holt eine listOfSimpleTokens.
	 *
	 * @return gibt die Liste listOfSimpleTokens zurück
	 */
	public List<SimpleToken> getListOfSimpleTokens() {
		return listOfSimpleTokens;
	}

	/**
	 * Gets the doc text.
	 *
	 * @return the doc text
	 */
	public String getDocText() {
		return docText;
	}

}
