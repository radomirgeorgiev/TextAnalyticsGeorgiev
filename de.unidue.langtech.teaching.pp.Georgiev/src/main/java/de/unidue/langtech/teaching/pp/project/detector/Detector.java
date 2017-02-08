package de.unidue.langtech.teaching.pp.project.detector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;

public class Detector extends JCasAnnotator_ImplBase {

	private List<String> myS;

	public void process(JCas jcas) throws AnalysisEngineProcessException {

		myS = new ArrayList<String>();

		DetectedLanguage dl = JCasUtil.selectSingle(jcas, DetectedLanguage.class);
		myS = Arrays.asList(dl.getSuggestion().toArray());

		Map<String, Long> counts = myS.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

		String key = Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();

		dl.setLanguage(key);
		dl.addToIndexes();
		jcas.setDocumentLanguage(key);
	}

}
