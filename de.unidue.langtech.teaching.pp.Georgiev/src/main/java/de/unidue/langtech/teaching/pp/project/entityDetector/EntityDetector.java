package de.unidue.langtech.teaching.pp.project.entityDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.langtech.teaching.pp.project.storage.EventDetails;
import de.unidue.langtech.teaching.pp.project.storage.Pair;
import de.unidue.langtech.teaching.pp.project.storage.SimpleToken;
import de.unidue.langtech.teaching.pp.project.storage.StorageDocument;
import de.unidue.langtech.teaching.pp.project.timeDetector.TimeDetection;
import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;
import de.unidue.langtech.teaching.pp.project.type.RawTextData;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

public class EntityDetector extends JCasAnnotator_ImplBase {

	private String inputFolder = "src/test/resources/storage/";
	private LinkedList<LinkedList<SimpleToken>> listOfDocumentEvents;
	private List<String> listOfAllEntities;
	private String storageFolder = "src/test/resources/storage/";
	private List<String> listOfEntityCandidatesForGivenDocument;
	private LinkedList<Integer> listOfAlreadyEvaluatedSentences;
	private LinkedList<Chunk> listOfNPChunksForGivenDokument;
	private LinkedList<Chunk> listOfVPChunksForGivenDokument;
	private HashMap<String, List<String>> mapOfEntities;
	private HashMap<String, List<EventDetails>> timeLineMap;
	private HashMap<String, HashMap<String, List<EventDetails>>> mapOfResult;
	private String recognizedEntity;
	private String documentCreationDate;
	private String documentLanguage;
	private String recognizedTime;
	private String documentID;
	private String corpusID;
	private int sentenceNumber;
	private Sentence sentenceToCheck;
	private EventDetails ed;
	private JCas jcas;

	@Override
	public void process(JCas jcas_) throws AnalysisEngineProcessException {

		System.out.print("Beginne mit Dokument-Analyse...\t");
		jcas = jcas_;
		DocumentMetaData docMeta = DocumentMetaData.get(jcas);
		DetectedLanguage detLang = JCasUtil.selectSingle(jcas, DetectedLanguage.class);
		RawTextData rtd = JCasUtil.selectSingle(jcas, RawTextData.class);
		documentLanguage = detLang.getLanguage();
		documentID = docMeta.getDocumentId();
		documentCreationDate = rtd.getDocumentCreationDate();
		listOfEntityCandidatesForGivenDocument = new ArrayList<String>();
		listOfAlreadyEvaluatedSentences = new LinkedList<Integer>();

		loadCorpusID();
		loadMapOfResult();
		loadMapOfEntities();
		loadAllEntities();
		getLinkOfChunkForGivenDokument();
		getEntitiesForGivenDocument();
		getEventsForGivenDocument();
		analysis();
		checkForDoubles();
		writeMapOfEntities();
		writeMapOfResult();
		System.out.println("ok!!!");
	}

	/**
	 * Adds the entity to hash map.
	 *
	 * @param entity
	 * @param listOfWords_
	 */
	private void addEntityToHashMap(String entity, List<String> listOfWords_) {
		List<String> tempList = new ArrayList<String>();
		tempList = mapOfEntities.get(entity);
		tempList.addAll(listOfWords_);
		mapOfEntities.put(entity, tempList);
	}

	/**
	 * AddOn
	 *
	 * @param llst_
	 * @param tokenStem_
	 */
	private void addOn(LinkedList<SimpleToken> llst_, String tokenStem_) {
		ed = new EventDetails(documentID, sentenceNumber, llst_, tokenStem_);
		fill(recognizedEntity, recognizedTime, ed);
		timeEquasion(recognizedEntity, recognizedTime, ed);
		listOfAlreadyEvaluatedSentences.add(sentenceNumber);
		// System.out.println(recognizedEntity + " : " + recognizedTime + "-" +
		// documentID + "-" + sentenceNumber + "-"
		// + llst_.getFirst().getTokenText());
	}

	/**
	 * Analysis.
	 *
	 */
	private void analysis() {
		recognizedEntity = null;

		Collection<Sentence> sentenceCollection = JCasUtil.select(jcas, Sentence.class);
		Object[] sent = sentenceCollection.toArray();

		for (LinkedList<SimpleToken> llst : listOfDocumentEvents) {

			sentenceNumber = llst.getFirst().getSentenceNumber();
			sentenceToCheck = (Sentence) sent[sentenceNumber];

			// if (llst.size() > 1) {
			// System.out.println("Sentence number: " + sentenceNumber + "
			// event: " + llst.getFirst().getTokenText()
			// + "_" + llst.getLast().getTokenText());
			// } else {
			// System.out.println("Sentence number: " + sentenceNumber + "
			// event: " + llst.getFirst().getTokenText());
			// }

			recognizedTime = new TimeDetection(jcas, sentenceToCheck, documentCreationDate, documentLanguage, llst)
					.getTime();

			filterTypA(llst);
			filterTypB(llst);
		}

	}

	/**
	 * Untersucht die NP-Chunks in einem Satz auf Ähnlichkeit zu einem von den
	 * gegebenen Entities.
	 *
	 * @param jcas
	 * @param se
	 * @param de
	 * @return gibt Entity mit grösste Ähnlichkeit zurück
	 * @throws Exception
	 */
	private String checkChunksForEntity(Dependency de) throws Exception {
		int sentenceBegin = sentenceToCheck.getBegin();
		int sentenceEnd = sentenceToCheck.getEnd();

		int dependencyBegin = de.getBegin();
		int dependencyEnd = de.getEnd();

		Chunk chunkToCheck = null;

		for (Chunk chunk : JCasUtil.select(jcas, Chunk.class)) {
			int chunkBegin = chunk.getBegin();
			int chunkEnd = chunk.getEnd();
			if (chunkBegin >= sentenceBegin && chunkEnd <= sentenceEnd && dependencyBegin >= chunkBegin
					&& dependencyEnd <= chunkEnd) {
				chunkToCheck = chunk;
			}
		}

		String myChunk1 = "";
		String myChunk2 = "";

		String tempChunk = "";

		if (listOfNPChunksForGivenDokument.contains(chunkToCheck)) {
			int indexOfChunkBefore = listOfNPChunksForGivenDokument.indexOf(chunkToCheck) - 1;
			int indexOfChunkAfter = listOfNPChunksForGivenDokument.indexOf(chunkToCheck) + 1;

			if (indexOfChunkBefore <= 0) {
				indexOfChunkBefore = 0;
			} else if (indexOfChunkAfter >= listOfNPChunksForGivenDokument.size()) {
				indexOfChunkAfter = listOfNPChunksForGivenDokument.size() - 1;
			}

			Chunk ch1 = listOfNPChunksForGivenDokument.get(indexOfChunkBefore);
			Chunk ch2 = listOfNPChunksForGivenDokument.get(indexOfChunkAfter);
			if (ch1.getBegin() >= sentenceBegin && ch1.getEnd() <= sentenceEnd) {
				String[] temp = ch1.getCoveredText().split(" ");
				for (int i = 0; i < temp.length; i++) {
					if (!(new StopwordRemover().isStopWord(temp[i]))) {
						myChunk1 += temp[i] + " ";
					}
				}

			} else if (ch2.getBegin() >= sentenceBegin && ch2.getEnd() <= sentenceEnd) {
				String[] temp = ch2.getCoveredText().split(" ");
				for (int i = 0; i < temp.length; i++) {
					if (!(new StopwordRemover().isStopWord(temp[i]))) {
						myChunk2 += temp[i] + " ";
					}
				}

			}

			String[] temp = chunkToCheck.getCoveredText().split(" ");
			for (int i = 0; i < temp.length; i++) {
				if (!(new StopwordRemover().isStopWord(temp[i]))) {
					tempChunk += temp[i] + " ";
				}
			}

			tempChunk = tempChunk + myChunk1 + myChunk2;

		}

		return similarity(tempChunk);

	}

	/**
	 * Prüfe ob bestimmte EventDetails doppelt in der Linkedlist vorkommt, wenn
	 * ja lösche den aus der Liste
	 */
	private void checkForDoubles() {
		if (!mapOfResult.isEmpty()) {
			List<String> keys = new ArrayList<String>(mapOfResult.keySet());
			for (String entity : listOfAllEntities) {
				if (keys.contains(entity)) {
					HashMap<String, List<EventDetails>> tempMap = mapOfResult.get(entity);
					for (String time : tempMap.keySet()) {
						LinkedList<EventDetails> lled = new LinkedList<EventDetails>(tempMap.get(time));
						LinkedList<EventDetails> copy = new LinkedList<EventDetails>();
						Iterator<EventDetails> it = lled.iterator();
						while (it.hasNext()) {
							EventDetails tempED = it.next();
							if (!copy.contains(tempED)) {
								copy.add(tempED);
							} else {
								mapOfResult.get(entity).get(time).remove(tempED);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Fülle Daten in einer bestimmten HashMap mit bestimmte Schlüssel Entity
	 *
	 * @param entity
	 * @param time
	 * @param ed
	 */
	private void fill(String entity, String time, EventDetails ed) {
		timeLineMap = new HashMap<String, List<EventDetails>>();
		List<EventDetails> listOfEventDetails = new ArrayList<EventDetails>();
		timeLineMap = mapOfResult.getOrDefault(entity, timeLineMap);
		listOfEventDetails = timeLineMap.getOrDefault(time, listOfEventDetails);
		boolean isSeen = false;
		for (EventDetails evD : listOfEventDetails) {
			if (ed.equals(evD)) {
				isSeen = true;
			}
		}
		if (!isSeen) {
			listOfEventDetails.add(ed);
		}
		timeLineMap.put(time, listOfEventDetails);
		mapOfResult.put(entity, timeLineMap);
	}

	/**
	 * FilterTypA
	 *
	 * @param llst
	 */
	private void filterTypA(LinkedList<SimpleToken> llst) {

		List<Dependency> tempListOfTokensForGivenSentence = getDependencyForSentence();

		for (Dependency dep : tempListOfTokensForGivenSentence) {
			if ((dep.getGovernor().getCoveredText().toLowerCase().equals(llst.getFirst().getTokenText().toLowerCase()))
					|| (dep.getGovernor().getCoveredText().toLowerCase()
							.equals(llst.getLast().getTokenText().toLowerCase()))) {
				if (dep.getGovernor().getCoveredText().toLowerCase() != llst.getFirst().getTokenText().toLowerCase()
						&& dep.getGovernor().getCoveredText().toLowerCase() != llst.getLast().getTokenText()
								.toLowerCase()) {
					firstCheck(dep, llst);
				}
			}
			secondCheck(dep, llst);
		}
	}

	/**
	 * FilterTypB
	 *
	 * @param llst
	 */
	private void filterTypB(LinkedList<SimpleToken> llst) {
		if (recognizedEntity == null && checkForAlreadyEvaluatedSentence()) {
			for (Chunk ch : getChunksForGivenSentence()) {
				recognizedEntity = getEntitiesForGivenSentenseThroughChunks(ch, jcas);
				if (recognizedEntity != null && recognizedEntity != "") {
					addOn(llst, foundTokensStemForGivenDependency(llst).getStem().getValue());
				}
			}
		}
	}

	/**
	 * 
	 */
	private boolean checkForAlreadyEvaluatedSentence() {
		if (listOfAlreadyEvaluatedSentences.contains(sentenceNumber)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 *
	 * @param llst_
	 */
	private void firstCheck(Dependency dep, LinkedList<SimpleToken> llst_) {
		for (String entity : listOfAllEntities) {
			if (dep.getDependent().getCoveredText().toLowerCase().equals(entity.toLowerCase())) {
				recognizedEntity = entity;
				List<String> tempListOfEntityF = new ArrayList<String>();
				tempListOfEntityF = mapOfEntities.getOrDefault(entity, tempListOfEntityF);
				tempListOfEntityF.add(entity);
				mapOfEntities.put(entity, tempListOfEntityF);
				addOn(llst_, foundTokensStemForGivenDependency(llst_).getStem().getValue());
				break;
			} else {
				recognizedEntity = null;
			}
		}
	}

	private Token foundTokensStemForGivenDependency(LinkedList<SimpleToken> llst) {

		for (Token to : JCasUtil.select(jcas, Token.class)) {
			if (to.getBegin() >= sentenceToCheck.getBegin() && to.getEnd() <= sentenceToCheck.getEnd()) {
				if (to.getCoveredText().toLowerCase().contains(llst.getFirst().getTokenText().toLowerCase())) {
					return to;
				}
			}
		}
		return null;
	}

	/**
	 * Liest die Chunks für einen gegebenen Satz in eine LinkenListe
	 * 
	 * @return gibt die Liste zurück
	 */
	private LinkedList<Chunk> getChunksForGivenSentence() {
		int sentenceBegin = sentenceToCheck.getBegin();
		int sentenceEnd = sentenceToCheck.getEnd();
		LinkedList<Chunk> tempLinkedList = new LinkedList<Chunk>();
		for (Chunk ch : JCasUtil.select(jcas, Chunk.class)) {
			if (ch.getBegin() >= sentenceBegin && ch.getEnd() <= sentenceEnd) {
				tempLinkedList.add(ch);
			}
		}
		return tempLinkedList;
	}

	/**
	 * Bestimmt alle Entities für das gegebenen Dokument
	 *
	 */
	private void getEntitiesForGivenDocument() {

		String[] dividetText = jcas.getDocumentText().split("\n");
		for (String sentence : dividetText) {
			String[] words = sentence.split(" ");
			for (String entity : listOfAllEntities) {
				String[] entitySplit = entity.split(" ");
				int candidateCount = 0;
				for (String s : entitySplit) {
					boolean seen = false;
					for (String word : words) {
						if (s.toLowerCase().equals(word.toLowerCase())) {
							if (!seen) {
								candidateCount++;
								seen = true;
							}
						}
					}
				}
				if (candidateCount == entitySplit.length) {
					if (listOfEntityCandidatesForGivenDocument != null
							&& !listOfEntityCandidatesForGivenDocument.contains(entity)) {
						listOfEntityCandidatesForGivenDocument.add(entity);
					}
				}
			}
		}
	}

	/**
	 * Sucht nach Entitaeten unter den Chunks eines Satzes
	 * 
	 * @param ch
	 * @param jcas
	 * @return
	 */
	private String getEntitiesForGivenSentenseThroughChunks(Chunk ch, JCas jcas) {
		String[] chunkSeparate = ch.getCoveredText().split(" ");
		LinkedList<String> tempLL = new LinkedList<String>();
		for (String s : chunkSeparate) {
			if (!isStopword(s)) {
				tempLL.add(s);
			}
		}

		for (String entity : listOfAllEntities) {
			int hit = 0;
			String[] entitySeparate = entity.split(" ");
			for (String entityS : entitySeparate) {
				for (String chun : tempLL) {
					if (chun.toLowerCase().equals(entityS.toLowerCase())) {
						hit++;
					}

				}

			}
			if (hit >= entitySeparate.length - 1 && hit > 0) {
				return entity;
			}

		}

		return null;
	}

	/**
	 * Holt alle Events für das bestimmte Dokument und speichert die in einer
	 * LinkedListe
	 *
	 * @return gibt die Liste mit den Events zurück
	 */
	@SuppressWarnings("unchecked")
	private LinkedList<LinkedList<SimpleToken>> getEventsForGivenDocument() {
		String[] files = { "storageDocuments.ser", "storageEvents.ser" };
		HashMap<String, StorageDocument> mapOfStorageDocuments = new HashMap<String, StorageDocument>();
		HashMap<String, List<Pair>> mapOfEvents = new HashMap<String, List<Pair>>();
		LinkedList<LinkedList<SimpleToken>> listOfDocumentEventsTemp = new LinkedList<LinkedList<SimpleToken>>();
		listOfDocumentEvents = new LinkedList<LinkedList<SimpleToken>>();

		for (int i = 0; i < files.length; i++) {
			try {
				FileInputStream fileIn = new FileInputStream(storageFolder + files[i]);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				switch (i) {
				case 0:
					mapOfStorageDocuments = (HashMap<String, StorageDocument>) in.readObject();
					in.close();
					fileIn.close();
					break;
				case 1:
					mapOfEvents = (HashMap<String, List<Pair>>) in.readObject();
					in.close();
					fileIn.close();
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
		}
		List<Pair> tempListOfPairs = mapOfEvents.get(documentID);
		LinkedList<SimpleToken> tempListOfToken;
		for (Pair p : tempListOfPairs) {
			tempListOfToken = new LinkedList<SimpleToken>();
			List<Integer> tempListOfIntegers = p.gettIDs();
			for (int a : tempListOfIntegers) {
				SimpleToken tempToken = mapOfStorageDocuments.get(documentID).getListOfSimpleTokens().get(a - 1);
				tempListOfToken.add(tempToken);
			}
			listOfDocumentEventsTemp.add(tempListOfToken);
		}

		while (!listOfDocumentEventsTemp.isEmpty()) {
			LinkedList<SimpleToken> tempListOfST = new LinkedList<SimpleToken>();
			tempListOfST = listOfDocumentEventsTemp.get(0);
			for (int j = 0; j < listOfDocumentEventsTemp.size(); j++) {
				LinkedList<SimpleToken> tempListOfSTS = new LinkedList<SimpleToken>();
				tempListOfSTS = listOfDocumentEventsTemp.get(j);
				if (tempListOfST.getFirst().getSentenceNumber() >= tempListOfSTS.getFirst().getSentenceNumber()) {
					if (tempListOfST.getFirst().getTokenID() >= tempListOfSTS.getFirst().getTokenID()) {
						tempListOfST = tempListOfSTS;
					}
				}
			}
			listOfDocumentEventsTemp.remove(tempListOfST);
			listOfDocumentEvents.add(tempListOfST);
		}

		return listOfDocumentEvents;
	}

	/**
	 * Holt die Chunks mit einem Wert "NP" für jeden Dokument und speichert
	 * diese in eine LinkedList
	 *
	 * @param jcas
	 */
	private void getLinkOfChunkForGivenDokument() {
		listOfNPChunksForGivenDokument = new LinkedList<Chunk>();
		listOfVPChunksForGivenDokument = new LinkedList<Chunk>();
		for (Chunk chunk : JCasUtil.select(jcas, Chunk.class)) {
			if (chunk.getChunkValue().equals("NP")) {
				listOfNPChunksForGivenDokument.add(chunk);
			} else if (chunk.getChunkValue().equals("VP")) {
				listOfVPChunksForGivenDokument.add(chunk);
			}
		}
	}

	/**
	 * Holt die Dependency für bestimmten Satz und speichert die in einer
	 * LinkedListe
	 *
	 * @param se
	 * @param jcas
	 * @return gibt die Liste mit Dependency zurück
	 */
	private List<Dependency> getDependencyForSentence() {
		List<Dependency> listToReturn = new LinkedList<Dependency>();
		int begin = sentenceToCheck.getBegin();
		int end = sentenceToCheck.getEnd();
		for (Dependency tok : JCasUtil.select(jcas, Dependency.class)) {
			if (tok.getBegin() >= begin && tok.getEnd() <= end) {
				listToReturn.add(tok);
			}
		}
		return listToReturn;

	}

	/**
	 * Prüfe ob ein File leer ist
	 *
	 * @param fileName
	 * @return gibt true zurück, wenn der File leer ist, sonst false
	 */
	private boolean isFileEmpty(String fileName) {
		File file = new File(storageFolder + fileName);
		return file.length() == 0;
	}

	private boolean isStopword(String word) {
		try {
			if (new StopwordRemover().isStopWord(word)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Laden alle Entities vom jcas Container in einer Liste
	 *
	 * @param jcas
	 */
	private void loadAllEntities() {
		listOfAllEntities = new ArrayList<String>();
		RawXMLData raw = JCasUtil.selectSingle(jcas, RawXMLData.class);
		StringArray stA = raw.getListOfEntities();
		for (int i = 0; i < stA.size(); i++) {
			listOfAllEntities.add(stA.get(i));
		}
	}

	/**
	 * Laden corpusID vom File
	 */
	private void loadCorpusID() {
		String fileName = "corpusID";
		File file = new File(storageFolder + fileName);
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNext()) {
			corpusID = s.next();
		}
	}

	/**
	 * Laden HashMap mapOfEntities vom File
	 */
	@SuppressWarnings("unchecked")
	private void loadMapOfEntities() {
		String fileName = "storageEntity.ser";
		if (!isFileEmpty(fileName)) {
			try {
				FileInputStream fileIn = new FileInputStream(inputFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mapOfEntities = (HashMap<String, List<String>>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
		} else {
			mapOfEntities = new HashMap<String, List<String>>();
		}
	}

	/**
	 * Laden HashMAap mapOfResult vom File
	 */
	@SuppressWarnings("unchecked")
	private void loadMapOfResult() {

		String fileName = "storageResult" + corpusID + ".ser";
		if (!isFileEmpty(fileName)) {
			try {
				FileInputStream fileIn = new FileInputStream(storageFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mapOfResult = (HashMap<String, HashMap<String, List<EventDetails>>>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
		} else {
			mapOfResult = new HashMap<String, HashMap<String, List<EventDetails>>>();
		}
	}

	/**
	 * Vergleich von zwei Daten und gibt das zurück was früher zutrifft es sei
	 * denn ein von beiden undefiniert ist, dann gibt das was definiert ist
	 *
	 * @param dateA
	 * @param dateB
	 * @return gibt das Datum nach dem Vergleich zurück
	 */
	private String matchDate(String dateA, String dateB) {

		String[] tempA = dateA.split("-");
		String[] tempB = dateB.split("-");

		String yearA = tempA[0];
		String monthA = tempA[1];
		String dayA = tempA[2];

		String yearB = tempB[0];
		String monthB = tempB[1];
		String dayB = tempB[2];

		if (!yearA.equals("XXXX") && !yearB.equals("XXXX")) {
			int iYearA = Integer.parseInt(yearA);
			int iYearB = Integer.parseInt(yearB);
			if (iYearA == iYearB) {
				if (!monthA.equals("XX") && !monthB.equals("XX")) {
					int iMonthA = Integer.parseInt(monthA);
					int iMonthB = Integer.parseInt(monthB);
					if (iMonthA == iMonthB) {
						if (!dayA.equals("XX") && !dayB.equals("XX")) {
							int iDayA = Integer.parseInt(dayA);
							int iDayB = Integer.parseInt(dayB);
							if (iDayA <= iDayB) {
								return dateA;
							} else {
								return dateB;
							}
						} else {
							if (dayA.equals(dayB)) {
								return dateA;
							} else {
								if (StringUtils.isNumeric(dayA)) {
									return dateA;
								} else {
									return dateB;
								}
							}
						}
					} else {
						if (iMonthA <= iMonthB) {
							return dateA;
						} else {
							return dateB;
						}
					}
				} else {
					if (monthA.equals(monthB)) {
						return dateA;
					} else {
						if (StringUtils.isNumeric(monthA)) {
							return dateA;
						} else {
							return dateB;
						}
					}
				}
			} else {
				if (iYearA <= iYearB) {
					return dateA;
				} else {
					return dateB;
				}
			}
		} else {
			if (yearA.equals(yearB)) {
				return dateA;
			} else {
				if (StringUtils.isNumeric(yearA)) {
					return dateA;
				} else {
					return dateB;
				}
			}
		}
	}

	/**
	 * Untersucht die Chunks für ein Entitaetkandidat
	 *
	 * @param llit
	 */
	private void secondCheck(Dependency dep, LinkedList<SimpleToken> llst) {
		if (recognizedEntity == null) {
			try {
				recognizedEntity = checkChunksForEntity(dep);
				if (recognizedEntity != null && recognizedEntity != "") {
					addOn(llst, foundTokensStemForGivenDependency(llst).getStem().getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Similarity
	 *
	 * @param chunksText
	 * @return result
	 */
	private String similarity(String chunksText) {
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		List<String> tempList = new ArrayList<String>();

		if (chunksText != null && chunksText != "") {
			String[] chunks = chunksText.split(" ");
			for (int i = 0; i < chunks.length; i++) {
				if (!StringUtils.isNumeric(chunks[i])) {
					tempList.add(chunks[i]);

				} else {
					double d = Double.parseDouble(chunks[i].replace(",", ""));
					if (d < 1000) {
						tempList.add(chunks[i]);
					}
				}
			}
			for (String entity : listOfEntityCandidatesForGivenDocument) {

				double lambda = 0.0;
				String[] partOfEntity = entity.split(" ");
				for (String s : partOfEntity) {
					for (String chunk : chunks) {
						if (s.toLowerCase().equals(chunk.toLowerCase())) {
							lambda += 0.3;
						}
					}
				}

				List<String> listOfWords = new ArrayList<String>();
				listOfWords = mapOfEntities.get(entity);
				double[][] matrix = new MatrixMaker().getMatrix(listOfWords);
				double[][][] cubeMatrix = new MatrixMaker().getCubeMatrix(listOfWords);

				double[][] chunkMatrix = new MatrixMaker().getMatrix(tempList);
				double[][][] chunkCubeMatrix = new MatrixMaker().getCubeMatrix(tempList);
				double score = 0.0;
				score = new MatrixMaker().similarity(matrix, chunkMatrix, cubeMatrix, chunkCubeMatrix) + lambda;
				resultMap.put(entity, score);
			}
		}

		double aa = 0.0;
		String result = null;
		for (Entry<String, Double> entry : resultMap.entrySet()) {
			double temp = entry.getValue();
			if (temp > aa) {
				aa = temp;
				result = entry.getKey();
			}
		}
		if (result != null) {
			addEntityToHashMap(result, tempList);
		}

		return result;

	}

	/**
	 * Vergleicht zwei Strings(Time) bei gleichen EventDetails
	 *
	 * @param entity_
	 * @param time_
	 * @param evd_
	 */
	private void timeEquasion(String entity_, String time_, EventDetails evd_) {
		HashMap<String, HashMap<String, List<EventDetails>>> tempHashMap = DeepClone.deepClone(mapOfResult);
		if (!tempHashMap.isEmpty()) {
			HashMap<String, List<EventDetails>> tempMap = tempHashMap.get(entity_);
			for (String time : tempMap.keySet()) {
				List<EventDetails> listOfDetails = tempMap.get(time);
				for (EventDetails ed : listOfDetails) {
					if (!ed.equals(evd_)) {
						if (ed.getDocID().equals(evd_.getDocID()) && ed.getEventLemma().equals(evd_.getEventLemma())) {
							String newDate = matchDate(time, time_);
							if (newDate.equals(time)) {
								mapOfResult.get(entity_).get(newDate).add(evd_);
								mapOfResult.get(entity_).get(time_).remove(evd_);
								if (mapOfResult.get(entity_).get(time_).isEmpty()) {
									mapOfResult.get(entity_).remove(time_);
								}
								break;
							} else {
								mapOfResult.get(entity_).get(time_).add(ed);
								mapOfResult.get(entity_).get(time).remove(ed);
								if (mapOfResult.get(entity_).get(time).isEmpty()) {
									mapOfResult.get(entity_).remove(time);
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Schreibt HashMap mapOfEntities in einem File
	 */
	private void writeMapOfEntities() {

		String outputFileName = "storageEntity.ser";
		try {
			FileOutputStream fileOut = new FileOutputStream(inputFolder + outputFileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mapOfEntities);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Schreibt HashMap mapOfResult in einem File
	 */
	private void writeMapOfResult() {

		String outputFileName = "storageResult" + corpusID + ".ser";
		try {
			FileOutputStream fileOut = new FileOutputStream(inputFolder + outputFileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mapOfResult);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Liest die Entitaetskandidaten für bestimmten Dokument
	 */
	public List<String> getListOfEntityCandidatesForGivenDocument() {
		return listOfEntityCandidatesForGivenDocument;
	}

	/**
	 * Liest die Ereignisse für bestimmten Dokument
	 */
	public LinkedList<LinkedList<SimpleToken>> getListOfDocumentEvents() {
		return listOfDocumentEvents;
	}

	/**
	 * Liest Dokumenterstellungsdatum für bestimmten Dokument
	 */
	public String getDocumentCreationDate() {
		return documentCreationDate;
	}

}
