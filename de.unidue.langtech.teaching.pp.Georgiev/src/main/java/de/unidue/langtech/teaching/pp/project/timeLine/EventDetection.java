package de.unidue.langtech.teaching.pp.project.timeLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.resource.ResourceInitializationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class EventDetection {

	private static final String inputFile = "C:/Users/Nivelin Stoyanov/Desktop/SemEval_Task4_data/corpus_1/list_target_entities_corpus_1.txt";
	private static List<String> textToCreate;
	private static List<String> listOfEntyties;
	private static List<String> listOfCandidates;
	private static HashMap<String, Integer> mapOfCandidates;
	
	public static void eventRecognition(Document doc, List<String> textToCreate_) throws ClassNotFoundException{
		String stt = "";
		for(String s : textToCreate){
			stt=stt+s+" ";
			
		}
		MaxentTagger tagger = null;
		tagger = new MaxentTagger("src/test/resources/tagger/left3words-wsj-0-18.tagger");
		String tagged = tagger.tagString(stt);
		System.out.println(tagged);
	}

	public static HashMap<String, Integer> extractCandidate(Document doc, int index) throws ResourceInitializationException, ClassNotFoundException {
		listOfCandidates = new ArrayList<String>();
		mapOfCandidates = new HashMap<String, Integer>();
		getEntyties(inputFile);
		extractRawTextFromXML(doc);
		compareEntity(listOfEntyties, textToCreate);
		eventFileCandidates(listOfCandidates, index);
		eventRecognition(doc, textToCreate);
		return mapOfCandidates;
	}
	
	public static void eventFileCandidates(List<String> listOfCandidates_, int index_){
		for(String str : listOfCandidates_){
			mapOfCandidates.put(str, index_);
		}
	}

	public static void compareEntity(List<String> listOfEntyties_, List<String> textToCreate_) {

		for (String entities : listOfEntyties_) {
			String[] str = entities.split(" ");
			int candidateCount = 0;
			for (String s : str) {
				boolean seen = false;
				for (String s1 : textToCreate_) {
					if (s.toLowerCase().equals(s1.toLowerCase())) {
						if (!seen) {
							candidateCount++;
							seen = true;
						}
					}
				}
			}
			if (candidateCount == str.length) {
				listOfCandidates.add(entities);
			}
		}
	}

	public static void extractRawTextFromXML(Document doc) {
		try {
			textToCreate = new ArrayList<String>();
			if (doc.hasChildNodes()) {
				extractData(doc.getChildNodes());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static List<String> getEntyties(String path) throws ResourceInitializationException {
		File f = new File(path);
		try {
			listOfEntyties = FileUtils.readLines(f);
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
		return listOfEntyties;
	}

	static void extractData(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				// System.out.println("\nNode Name =" + tempNode.getNodeName() +
				// " [OPEN]");
				// System.out.println("Node Value =" +
				// tempNode.getTextContent());
				if (tempNode.getNodeName().equals("token") && tempNode.getTextContent() != null) {
					textToCreate.add(tempNode.getTextContent());
				}
				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						// System.out.println("attr name : " +
						// node.getNodeName());
						// System.out.println("attr value : " +
						// node.getNodeValue());
						// if (tempNode.getNodeName().equals("TIMEX3")) {
						// if (node.getNodeName().equals("value")) {
						// docCreationDate += node.getNodeValue();
						// }
						// }
					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					extractData(tempNode.getChildNodes());

				}

				// System.out.println("Node Name =" + tempNode.getNodeName() + "
				// [CLOSE]");

			}

		}

	}
}
