package de.unidue.langtech.teaching.pp.project.storage;

import java.util.List;

/**
 * Die Klasse Pair wird verwendet um die Events in einem Dokument zu bestimmen.
 * Die ist notwaendig, weil im XML-Dateien die Events als Event-Mention in der
 * Form eines Paares von m_id und t_id gegeben sind
 */
public class Pair extends Component {

	private static final long serialVersionUID = 1L;

	private int mID;

	private int tID;

	private List<Integer> tIDs;

	private String nsubj;

	private String obj;

	/**
	 * Erstellt Instanz Pair
	 *
	 * @param mID_
	 * @param tIDs_
	 */
	public Pair(int mID_, int tID_) {
		this.mID = mID_;
		this.tID = tID_;
	}

	/**
	 * Erstellt Instanz Pair
	 *
	 * @param mID_
	 * @param List
	 *            mit tIDs_
	 */
	public Pair(int mID_, List<Integer> tIDs_) {
		this.mID = mID_;
		this.tIDs = tIDs_;
	}

	/**
	 * Erstellt Instanz Pair
	 *
	 * @param nsubj_
	 * @param obj_
	 */
	public Pair(String nsubj_, String obj_) {
		this.nsubj = nsubj_;
		this.obj = obj_;
	}

	/**
	 * holt mID
	 *
	 * @return gibt mID zurück
	 */
	public int getmID() {
		return mID;
	}

	/**
	 * holt tID.
	 *
	 * @return gibt tID zurück
	 */
	public int gettID() {
		return tID;
	}

	/**
	 * holt tIds.
	 *
	 * @return gibt die Listemit tIDs zurück
	 */
	public List<Integer> gettIDs() {
		return tIDs;
	}

	/**
	 * holt nsubj.
	 *
	 * @return gibt nsubj zurück
	 */
	public String getNsubj() {
		return nsubj;
	}

	/**
	 * holt obj.
	 *
	 * @return gibt obj zurück
	 */
	public String getObj() {
		return obj;
	}

	/**
	 * Die Ueberschriebene equals-Methode
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (o.getClass() != this.getClass()))
			return false;
		Pair test = (Pair) o;
		return (nsubj == test.nsubj || (nsubj != null && nsubj.equals(test.nsubj)))
				&& (obj == test.obj || (obj != null && obj.equals(test.obj)));
	}

	/**
	 * Die Ueberschriebene hashCode-Methode
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == nsubj ? 0 : nsubj.hashCode());
		hash = 31 * hash + (null == obj ? 0 : obj.hashCode());
		return hash;
	}

}
