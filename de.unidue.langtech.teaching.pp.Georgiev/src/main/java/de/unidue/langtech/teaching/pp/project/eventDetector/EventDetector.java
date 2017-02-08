package de.unidue.langtech.teaching.pp.project.eventDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.langtech.teaching.pp.project.entityDetector.EntityDetector;
import de.unidue.langtech.teaching.pp.project.entityDetector.ExtractTextFromWikipedia;
import de.unidue.langtech.teaching.pp.project.type.RawTextData;
import de.unidue.langtech.teaching.pp.project.type.RawXMLData;
import edu.stanford.nlp.sequences.ExactBestSequenceFinder;

@SuppressWarnings("unused")
public class EventDetector extends JCasAnnotator_ImplBase {

	private static String[] uncertainty = { "perhaps", "maybe", "probably", "possybly", "apparently", "imagine",
			"suppose", "guess", "believes", "has" };
	private static String[] modalVerb = { "can", "may", "shall", "could", "might", "should", "would" };
	private static String finalEntity;
	private static String finalRoot;
	private static String mapFolder = "src/test/resources/map/";
	private static String resultFolder = "src/test/resources/result/";
	private static List<String> listOfEntityCandidates;
	private static List<String> entities;
	private static String tempEntity;
	private static Map<String, List<String>> entityDescriptionMap;
	private static List<String> entityDescriptionList;
	private static HashMap<String, HashMap<String, Integer>> mapOfWikipediaTokens;
	private static JCas globalJCas;
	private static int sentenceNumber; 
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		globalJCas = jcas;
		HashMap<String, HashMap<String, Integer>> mapOfWikipediaTokens = new HashMap<String, HashMap<String, Integer>>();
		entityDescriptionMap = new HashMap<String, List<String>>();
		listOfEntityCandidates = new ArrayList<String>();
		entities = new ArrayList<String>();
		RawXMLData raw = JCasUtil.selectSingle(jcas, RawXMLData.class);
		StringArray stA = raw.getListOfEntities();
		for (int i = 0; i < stA.size(); i++) {
			entities.add(stA.get(i));
		}

		try {
			analysis(jcas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<String> compareEntityC(JCas jcas, String word) {
		List<String> tempList = new ArrayList<String>();
		String ent = "";
		String temp = "";
		temp = new ExtractTextFromWikipedia().getTextFromWikpedia(jcas.getDocumentLanguage(), word);
		for (String str : entities) {
			if (temp.toLowerCase().contains(str.toLowerCase())) {
				tempList.add(str);
			}
		}
		return tempList;
	}

	public static void compareEntity(List<String> listOfEntyties_, String[] textToCreate_) {

		for (String entities : listOfEntyties_) {
			String[] str = entities.split(" ");
			int candidateCount = 0;
			for (String s : str) {
				boolean seen = false;
				for (int i = 0; i < textToCreate_.length; i++) {
					if (s.toLowerCase().equals(textToCreate_[i].toLowerCase())) {
						if (!seen) {
							candidateCount++;
							seen = true;
						}
					}
				}
			}
			if (candidateCount == str.length) {
				listOfEntityCandidates.add(entities);
			}
		}
	}

	public static void testOfEntity(JCas jcas, String sentenceText) {
		String[] wordsOfText = sentenceText.split(" ");

		for (String str : entities) {
			if (!isSeen(str)) {
				new EntityDetector().process(jcas, str);
			}
		}

	}

	public static void analysis(JCas jCas) throws Exception {

		// show entities
		for (NamedEntity ne : JCasUtil.select(jCas, NamedEntity.class)) {
			System.out.println("Found NEs: " + ne.getValue() + ", " + ne.getCoveredText());
		}

		for (POS pos : JCasUtil.select(jCas, POS.class)) {
			System.out.println("Found POSs: " + pos.getPosValue() + ", " + pos.getCoveredText());
		}
		// similarity(listOfEntityCandidates, sentence);

		sentenceNumber = 0;
		for (Sentence se : JCasUtil.select(jCas, Sentence.class)) {
			int sentenceBegin = se.getBegin();
			int sentenceEnd = se.getEnd();

			System.out.println(sentenceNumber + " : " + se.getCoveredText());

			for (Dependency de : JCasUtil.select(jCas, Dependency.class)) {
				finalEntity = "";
				finalRoot = "";

				// String entityC = compareEntityC(jCas, de.getCoveredText());
				// System.out.println(entityC);
				// testOfEntity(jCas, de.getCoveredText());
				for (String str : listOfEntityCandidates) {
					System.out.println(str);

				}
				// System.out.println("Dependency type :
				// "+de.getDependencyType() + " Text: "+de.getCoveredText());
				if (de.getEnd() > sentenceEnd) {
					break;
				} else if (de.getBegin() >= sentenceBegin) {

					// boolean flag = false;
					// if
					// (de.getDependent().getPos().getPosValue().toLowerCase().equals("md")){
					// tempRoot = de.getDependent().getCoveredText();
					// flag=true;
					// }
					if (de.getDependencyType().equals("nsubj") || de.getDependencyType().equals("nsubjpass")
							|| de.getDependencyType().equals("dobj") || de.getDependencyType().equals("agent")
							|| de.getDependencyType().equals("iobj") || de.getDependencyType().equals("poss")) {

						Token nsubj = de.getDependent();
						Token root = de.getGovernor();
						
						if (checkSubj(nsubj)) {
							if (!finalEntity.isEmpty()&&checkRoot(se, root.getCoveredText())) {
								System.out.println("------Entity---------->>>>" + finalEntity
										+ "<<<<-----------------------------");
								System.out.println("-------Event---------->>>>" + finalRoot
										+ "<<<<-----------------------------");
								
								filter(jCas, finalRoot, finalEntity, sentenceNumber);
								
							}

						}


					}

				}

			}
			listOfEntityCandidates.clear();
			sentenceNumber++;
		}

	}

	public static boolean checkRoot(Sentence se, String root) {
		boolean ok = true;
		String[] tempSe = se.getCoveredText().split(" ");
		for(String str : modalVerb){
			for(int i = 0; i<tempSe.length;i++){
				if(str.equals(tempSe[i])){
					ok = false;
				}
			}
		}
		if(ok){
			for(String str : uncertainty){
				if(!root.equals(str)){
					finalRoot = root;
				}
			}
		}
		if(!finalRoot.isEmpty()){
			return true;
		}
		return false;
	}

	public static boolean checkSubj(Token nsubj) {

		if (isPartOfEntityList(nsubj.getCoveredText())) {
			finalEntity = nsubj.getCoveredText();
			return true;
		} else if (checkForEntityInWilipedia(nsubj.getCoveredText())) {

		}

		return true;
	}

	public static boolean isPartOfEntityList(String nsubjToCompare) {
		for (String str : entities) {
			if (nsubjToCompare.toLowerCase().equals(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkForEntityInWilipedia(String nsubjToCompare) {
		testOfEntity(globalJCas, nsubjToCompare);
		List<String> tempListOfEntityCandidates = compareEntityC(globalJCas, nsubjToCompare);
		String entityC = "";

		entityC = getFrequencyFromWikiMap(tempListOfEntityCandidates, nsubjToCompare);
		if (!entityC.isEmpty()) {
			finalEntity = entityC;
			return true;
		}
		return false;
	}

	public static void filter(JCas jcas, String event, String entity,  int sentenceNumber) throws UIMAException {

		DocumentMetaData docMetaData = DocumentMetaData.get(jcas);
		RawTextData rawTextData = JCasUtil.selectSingle(jcas, RawTextData.class);

		String docID = docMetaData.getDocumentId();
		String date = rawTextData.getDocumentCreationDate();

		String timeLine = "";
		timeLine = docID + "-" + sentenceNumber + "-" + event;
	
		writeResult(entity, date, timeLine);
		System.out.println("TIMELINE: " + date + " " + timeLine);
	}

	public static void filterByVerb(JCas jcas, Token nsubj, Token root, int sentenceNumber, String docID, String date,
			Dependency de) {

		System.out.println(date + " " + docID + "-" + sentenceNumber + "-" + root.getCoveredText());
	}

	private static void runSegmenterPipeline(JCas jcas) throws AnalysisEngineProcessException {
		try {
			SimplePipeline.runPipeline(jcas, AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class));
		} catch (ResourceInitializationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static boolean isSeen(String fileName) {
		return new File(mapFolder, fileName + ".ser").exists();
	}

	@SuppressWarnings("unchecked")
	public static String getFrequencyFromWikiMap(List<String> listOfCandidates, String word) {
		HashMap<String, Integer> myMap = new HashMap<String, Integer>();
		int compare = 0;
		String retEntity = "";
		for (String entity : listOfCandidates) {
			String inputFileName = entity + ".ser";

			try {
				FileInputStream fileIn = new FileInputStream(mapFolder + inputFileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				myMap = (HashMap<String, Integer>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();

			} catch (ClassNotFoundException c) {
				System.out.println("File not Found\n");
				c.printStackTrace();

			}
			int temp = 0;
			if (myMap.get(word) != null) {
				temp = myMap.get(word).intValue();
				if (compare < temp) {
					compare = temp;
					retEntity = entity;
				}
			}

		}
		return retEntity;
	}

	private static void writeResult(String entity, String date, String timeLine) {
		
		HashMap<String, List<String>> tempHM;
		if (!isSeen(entity)) {
			List<String> tempList = new ArrayList<String>();
			tempList.add(timeLine);
			tempHM = new HashMap<String, List<String>>();
			tempHM.put(date, tempList);
			writeTimeLine(tempHM, entity);
		} else {
			String tempKey = "";
			tempHM = readTimeLine(entity);
			if (tempHM.get(date) != null) {
				List<String> tempList = new ArrayList<String>();
				for (Map.Entry<String, List<String>> entry : tempHM.entrySet()) {
					if (entry.getKey().equals(date)) {
						tempList = entry.getValue();
						tempKey = entry.getKey();
					}
				}
				tempList.add(timeLine);
				tempHM.put(date, tempList);
			} else {
				List<String> tempList = new ArrayList<String>();
				tempList.add(timeLine);
				tempHM.put(date, tempList);
			}
		}
		writeTimeLine(tempHM, entity);

	}

	public void writeInFile(HashMap<String, Integer> myMap, String inputFolder, String entity) {

		String outputFileName = entity + ".ser";
		try {
			FileOutputStream fileOut = new FileOutputStream(inputFolder + outputFileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(myMap);
			out.close();
			fileOut.close();
			System.out.printf("Created file for entity: " + entity + "\n");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void writeTimeLine(HashMap<String, List<String>> myMap, String entity) {

		String outputFileName = entity + ".ser";
		try {
			FileOutputStream fileOut = new FileOutputStream(resultFolder + outputFileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(myMap);
			out.close();
			fileOut.close();
			System.out.printf("Created file for entity: " + entity + "\n");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static HashMap<String, List<String>> readTimeLine(String entity) {
		HashMap<String, List<String>> tempHM = new HashMap<String, List<String>>();
		String inputFileName = entity + ".ser";
		try {
			FileInputStream fileIn = new FileInputStream(resultFolder + inputFileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			tempHM = (HashMap<String, List<String>>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("File not Found\n");
			c.printStackTrace();

		}
		return tempHM;
	}

}