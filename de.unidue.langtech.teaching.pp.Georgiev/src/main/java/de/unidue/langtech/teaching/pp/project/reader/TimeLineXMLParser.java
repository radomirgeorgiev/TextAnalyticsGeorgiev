package de.unidue.langtech.teaching.pp.project.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.unidue.langtech.teaching.pp.project.storage.Pair;
import de.unidue.langtech.teaching.pp.project.storage.SimpleToken;
import de.unidue.langtech.teaching.pp.project.storage.StorageDocument;
import de.unidue.langtech.teaching.pp.project.type.RawTextData;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;

/**
 * The Class TimeLineXMLParser.
 */
public class TimeLineXMLParser extends JCasAnnotator_ImplBase {

	private String inputFolder = "src/test/resources/storage/";
	private Document doc;
	private String docID;
	private List<SimpleToken> listOfSimpleTokens;
	private List<Pair> listEvents;
	private HashMap<String, StorageDocument> mapOfStorageDocuments;
	private HashMap<String, List<Pair>> mapOfEvents;
	private HashMap<String, List<String>> mapOfEntities;

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		RawXMLData rXMLdata = JCasUtil.selectSingle(jcas, RawXMLData.class);
		String str = rXMLdata.getRawXMLData();
		StringArray entities = rXMLdata.getListOfEntities();

		docID = "";
		listOfSimpleTokens = new LinkedList<SimpleToken>();
		listEvents = new ArrayList<Pair>();

		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(str)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.normalize();
		iteration(jcas, doc.getDocumentElement());
		String docText = createDocumentText(listOfSimpleTokens);
		try {
			jcas.setDocumentText(docText);
		} catch (CASRuntimeException cre) {

		}

		collectEvents(docID);
		collectDocuments(docID, docText, listOfSimpleTokens);
		collectEntities(entities);
	}

	/**
	 * Iteration.
	 *
	 * @param jcas
	 *            the jcas
	 * @param node
	 *            the node
	 */
	private void iteration(JCas jcas, Node node) {
		filter(jcas, node);
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				iteration(jcas, currentNode);
			}
		}

	}

	/**
	 * Filter. Filtert die Daten von dem jcas Container,die wir für den TimeLine
	 * brauchen werden
	 * 
	 * @param jcas
	 * @param node
	 * 
	 */
	private void filter(JCas jcas, Node node) {
		if (node.getNodeName().equals("token")) {
			NamedNodeMap nMap = node.getAttributes();
			Node tNumber = nMap.getNamedItem("number");
			Node tSentenceN = nMap.getNamedItem("sentence");
			Node tID = nMap.getNamedItem("t_id");
			int tokenNumber = Integer.parseInt(tNumber.getNodeValue());
			int sentenceNumber = Integer.parseInt(tSentenceN.getNodeValue());
			int tokenID = Integer.parseInt(tID.getNodeValue());
			String token = node.getTextContent();
			SimpleToken st = new SimpleToken(tokenNumber, sentenceNumber, tokenID, token);
			listOfSimpleTokens.add(st);

		} else if (node.getNodeName().equals("EVENT_MENTION")) {
			NamedNodeMap nMap = node.getAttributes();
			Node mID = nMap.getNamedItem("m_id");
			NodeList childList = node.getChildNodes();
			int temp = childList.getLength();
			int eventID = Integer.parseInt(mID.getNodeValue());
			List<Integer> pairInt = new LinkedList<Integer>();
			for (int i = 1; i < temp; i++) {
				Node child = childList.item(i);
				NamedNodeMap cMap = child.getAttributes();
				try {
					Node tID = cMap.getNamedItem("t_id");
					int tokenID = Integer.parseInt(tID.getNodeValue());
					pairInt.add(tokenID);

				} catch (NullPointerException np) {

				}
			}
			listEvents.add(new Pair(eventID, pairInt));
		} else if (node.getNodeName().equals("Document")) {
			NamedNodeMap nMap = node.getAttributes();
			Node dID = nMap.getNamedItem("doc_id");
			Node dName = nMap.getNamedItem("doc_name");
			Node dURL = nMap.getNamedItem("src_url");
			docID = dID.getNodeValue();
			String docTitle = dName.getNodeValue();
			String docURL = dURL.getNodeValue();
			DocumentMetaData docMeta = null;
			try {
				docMeta = DocumentMetaData.get(jcas);
			} catch (IllegalArgumentException iaE) {
				docMeta = DocumentMetaData.create(jcas);
			}
			docMeta.setDocumentId(docID);
			docMeta.setDocumentTitle(docTitle);
			docMeta.setDocumentUri(docURL);
			docMeta.addToIndexes();

		} else if (node.getNodeName().equals("TIMEX3")) {
			NamedNodeMap nMap = node.getAttributes();
			Node dDate = nMap.getNamedItem("value");
			String docDate = dDate.getNodeValue();
			RawTextData rtd = new RawTextData(jcas);
			rtd.setDocumentCreationDate(docDate);
			rtd.addToIndexes();
		}
	}

	/**
	 * Collect entities Prüft ob es in ein File leer ist und ruft die Methode
	 * getMapEntities anschliessend schreibt HashMap mapOfEntities in diesem
	 * File
	 *
	 * @param entities
	 */
	private void collectEntities(StringArray entities) {
		String fileName = "storageEntity.ser";
		if (isFileEmpty(fileName)) {
			getMapEntities(entities);
			try {
				FileOutputStream fileOut = new FileOutputStream(inputFolder + fileName);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(mapOfEntities);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
	}

	/**
	 * Erstellt ein HashMap für jede Entity
	 * 
	 * @param entities
	 * 
	 */
	private void getMapEntities(StringArray entities) {
		mapOfEntities = new HashMap<String, List<String>>();
		for (int i = 0; i < entities.size(); i++) {
			String[] temp = entities.get(i).split(" ");
			List<String> tempList = new ArrayList<String>();
			for (int a = 0; a < temp.length; a++) {
				tempList.add(temp[a]);
			}
			mapOfEntities.put(entities.get(i), tempList);
		}
	}

	/**
	 * Collect events. Sammelt die Events nach Dokument ID und schreibt die in
	 * einer Liste anschliessend wird die Liste zu einen HashMap mapOfEvents
	 * hinzugefügt und in einem Dokument geschriben
	 * 
	 * @param docID
	 */
	@SuppressWarnings("unchecked")
	private void collectEvents(String docID) {
		String fileName = "storageEvents.ser";
		List<Pair> tempListOfPair = new ArrayList<Pair>();
		List<Integer> tempListOfIntegers;
		Pair tempPair;
		for (int i = 0; i < listEvents.size(); i++) {
			tempListOfIntegers = new LinkedList<Integer>();
			for (int a = 0; a < listEvents.get(i).gettIDs().size(); a++) {
				tempListOfIntegers.add(listEvents.get(i).gettIDs().get(a));
			}
			tempPair = new Pair(listEvents.get(i).getmID(), tempListOfIntegers);
			tempListOfPair.add(tempPair);
		}

		if (isFileEmpty(fileName)) {
			mapOfEvents = new HashMap<String, List<Pair>>();
			mapOfEvents.put(docID, tempListOfPair);
			writeEvents(mapOfEvents, fileName);
		} else {
			try {
				FileInputStream fileIn = new FileInputStream(inputFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mapOfEvents = (HashMap<String, List<Pair>>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
			mapOfEvents.put(docID, tempListOfPair);
			writeEvents(mapOfEvents, fileName);
		}

	}

	/**
	 * Collect documents. Liest die Dokumente von dem HashMap
	 * (mapOfStorageDocuments) von gegebenen File (wenn nicht Empty) hinzugefügt
	 * docId und sd im Map und in einem Dokument geschriben in einem Dokument
	 * geschrieben
	 * 
	 * @param docID
	 * @param docText
	 * @param listOfSimpleTokens
	 */
	@SuppressWarnings("unchecked")
	private void collectDocuments(String docID, String docText, List<SimpleToken> listOfSimpleTokens) {
		String fileName = "storageDocuments.ser";
		StorageDocument sd = new StorageDocument(docText, listOfSimpleTokens);
		if (isFileEmpty(fileName)) {
			mapOfStorageDocuments = new HashMap<String, StorageDocument>();
			mapOfStorageDocuments.put(docID, sd);
			writeStorageDocument(mapOfStorageDocuments, fileName);
		} else {
			try {
				FileInputStream fileIn = new FileInputStream(inputFolder + fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mapOfStorageDocuments = (HashMap<String, StorageDocument>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();
			}
			mapOfStorageDocuments.put(docID, sd);
			writeStorageDocument(mapOfStorageDocuments, fileName);
		}
	}

	/**
	 * Schreibt HashMap mapOfStorageDocuments in einem Dokument
	 * 
	 * @param mapOfStorageDocuments
	 * @param fileName
	 */
	private void writeStorageDocument(HashMap<String, StorageDocument> mapOfStorageDocuments, String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(inputFolder + fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mapOfStorageDocuments);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Write events. Schreibt HashMap listEvents in einem Dokument
	 * 
	 * @param listEvents
	 * @param fileName
	 */
	private void writeEvents(HashMap<String, List<Pair>> listEvents, String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(inputFolder + fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(listEvents);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Prüft ob ein Dokument leer ist
	 *
	 * @param fileName
	 * @return true, wenn das Dokument leer ist, sonst false
	 */
	private boolean isFileEmpty(String fileName) {
		File file = new File(inputFolder + fileName);
		return file.length() == 0;
	}

	/**
	 * Nimmt die Tokens für ein Dokument und schreibt die in ein String wobei
	 * jeden Satz an neue Zeile ist
	 * 
	 * @param listOfSimpleTokens
	 * @return gibt den String zurück
	 */
	private String createDocumentText(List<SimpleToken> listOfSimpleTokens) {
		int tempInt = listOfSimpleTokens.get(listOfSimpleTokens.size() - 1).getSentenceNumber() + 1;
		String tempString = "";
		for (int i = 0; i < tempInt; i++) {
			for (SimpleToken st : listOfSimpleTokens) {
				if (st.getSentenceNumber() == i) {
					tempString += st.getTokenText() + " ";
				} else if (st.getSentenceNumber() > i) {
					if (st.getSentenceNumber() < 3) {
						tempString += ".";
					}
					tempString += "\n";
					break;

				}
			}
		}
		return tempString;
	}

}