package de.unidue.langtech.teaching.pp.project.languageDetector;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;

public class LanguageDetector extends JCasAnnotator_ImplBase {

	private final double LAMBDA = 0.01;
	private final int MIN_TOKENS_FROM_TEXT = 10;
	private List<String> listOfTokenizedWordsFromText;
	private String language;
	private HashMap<String, List<Double>> counts;
	private double compareToLambda;

	public void process(JCas jcas) throws AnalysisEngineProcessException {

		// initialize the List of Words
		listOfTokenizedWordsFromText = new ArrayList<String>();

		// The whole Text as List of Words
		try {
			listOfTokenizedWordsFromText = Tokenizer.tokenize(jcas.getDocumentText());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// initialize the HashMap with language suggestions & compareToLambda
		counts = new HashMap<String, List<Double>>();
		compareToLambda = 0.0;
		
		UnigramAnnotator uniA = new UnigramAnnotator();
		List<Language> listOfDetectedCandidateLanguages = uniA.process(jcas, listOfTokenizedWordsFromText);
		extract(listOfDetectedCandidateLanguages);
		if (compareToLambda < LAMBDA  | tokenRange(listOfTokenizedWordsFromText.size())) {
			BigramAnnotator biA = new BigramAnnotator();
			listOfDetectedCandidateLanguages = biA.process(jcas, listOfTokenizedWordsFromText);
			extract(listOfDetectedCandidateLanguages);
			if (compareToLambda < LAMBDA | tokenRange(listOfTokenizedWordsFromText.size())) {
				TrigramAnnotator trA = new TrigramAnnotator();
				listOfDetectedCandidateLanguages = trA.process(jcas, listOfTokenizedWordsFromText);
				extract(listOfDetectedCandidateLanguages);
			}
		} 
		writeCandidateLangugage(jcas, language);
	}

	private void extract(List<Language> ll) {

		for (Language tempLanguage : ll) {
			List<Double> doubleList = new ArrayList<Double>();
			if (counts.get(tempLanguage.getLanguage()) != null) {
				doubleList = counts.get(tempLanguage.getLanguage());
			}
			doubleList.add(tempLanguage.getValue());
			counts.put(tempLanguage.getLanguage(), doubleList);
		}

		HashMap<String, Double> tempMap = new HashMap<String, Double>();
		for (Entry<String, List<Double>> entry : counts.entrySet()) {
			double tempD = 0.0;
			for (double d : entry.getValue()) {
				tempD += d;
			}
			tempMap.put(entry.getKey(), tempD / (entry.getValue().size()));
		}
		language = Collections.max(tempMap.entrySet(), Map.Entry.comparingByValue()).getKey();
		double tempA = tempMap.get(language);
		tempMap.remove(language);
		double tempB = Collections.max(tempMap.entrySet(), Map.Entry.comparingByValue()).getValue();
		compareToLambda = Math.abs(tempA - tempB);
	}

	private void writeCandidateLangugage(JCas jcas, String language) {
		DetectedLanguage detectedLanguage = new DetectedLanguage(jcas);
		detectedLanguage.setLanguage(language);
		detectedLanguage.addToIndexes();
		jcas.setDocumentLanguage(language);
	}
	
	private boolean tokenRange(int lenghtOfList){
		if(lenghtOfList<=MIN_TOKENS_FROM_TEXT){
			return true;
		}
		return false;
	}

}
