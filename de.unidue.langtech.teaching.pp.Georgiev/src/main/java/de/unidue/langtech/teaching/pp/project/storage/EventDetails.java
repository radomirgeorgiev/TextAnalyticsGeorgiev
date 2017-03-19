package de.unidue.langtech.teaching.pp.project.storage;

import java.util.LinkedList;

/**
 * Klasse EventDetails wird benoetigt um saemtliche Info ueber ein Event zu
 * speichern
 */
public class EventDetails extends Component {

	private static final long serialVersionUID = 1L;

	private String docID;

	private String eventLemma;

	private int sentenceNumber;

	private LinkedList<SimpleToken> events;

	/**
	 * Erstellt Instanz von EventDetails
	 *
	 * @param docID_
	 * @param sentenceNumber_
	 * @param events_
	 * @param eventLemma_
	 */
	public EventDetails(String docID_, int sentenceNumber_, LinkedList<SimpleToken> events_, String eventLemma_) {
		this.docID = docID_;
		this.sentenceNumber = sentenceNumber_;
		this.eventLemma = eventLemma_;
		this.events = events_;
	}

	/**
	 * holt docID.
	 *
	 * @return gibt docID zurück
	 */
	public String getDocID() {
		return docID;
	}

	/**
	 * holt eventLemma.
	 *
	 * @return gibt eventLemma zurück
	 */
	public String getEventLemma() {
		return eventLemma;
	}

	/**
	 * holt sentenceNumber.
	 *
	 * @return gibt sentenceNumber zurück
	 */
	public int getSentenceNumber() {
		return sentenceNumber;
	}

	/**
	 * holt event.
	 *
	 * @return gibt Event zurück
	 */
	public LinkedList<SimpleToken> getEvent() {
		return events;
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
		EventDetails test = (EventDetails) o;
		return (docID == test.docID || (docID != null && docID.equals(test.docID)))
				&& (sentenceNumber == test.sentenceNumber)
				&& (eventLemma == test.eventLemma || (eventLemma != null && eventLemma.equals(test.eventLemma)))
				&& (events == test.events || (events != null && events.equals(test.events)));
	}

	/**
	 * Die Ueberschriebene hashCode-Methode
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == docID ? 0 : docID.hashCode());
		hash = 31 * hash + sentenceNumber;
		hash = 31 * hash + (null == eventLemma ? 0 : eventLemma.hashCode());
		hash = 31 * hash + (null == events ? 0 : events.hashCode());
		return hash;
	}

}
