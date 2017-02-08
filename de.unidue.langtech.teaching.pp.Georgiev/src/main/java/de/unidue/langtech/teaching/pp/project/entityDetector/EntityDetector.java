package de.unidue.langtech.teaching.pp.project.entityDetector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;

public class EntityDetector {

	private String mapFolder = "src/test/resources/map/";
	private String inputStopword = "src/test/resources/collection/stopwordsLong.txt";
	private String regex = "^[()|<>$\'#.,;:?!&`%-]+$";

	public void process(JCas jcas, String entity) {

		HashMap<String, Integer> tempWikiMap = new HashMap<String, Integer>();
		try {
			tempWikiMap = prepairData(jcas, entity);
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UIMAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writeInFile(tempWikiMap, mapFolder, entity);
		System.gc();

	}

	private HashMap<String, Integer> prepairData(JCas jcas, String entity) throws UIMAException, IOException {
		List<String> tokenList = new ArrayList<String>();
		JCas jC = JCasFactory.createJCas();

		jC.setDocumentLanguage(jcas.getDocumentLanguage());
		String textToPrepare = new ExtractTextFromWikipedia().getTextFromWikpedia(jC.getDocumentLanguage(), entity);

		jC.setDocumentText(textToPrepare);

		SimplePipeline.runPipeline(jC, AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),
				AnalysisEngineFactory.createEngineDescription(StopWordRemover.class,
						StopWordRemover.PARAM_MODEL_LOCATION, new String[] { inputStopword }));

		for (Token to : JCasUtil.select(jC, Token.class)) {
			if (!to.getCoveredText().matches(regex)) {
				tokenList.add(to.getCoveredText());
			}
		}
		Set<String> mySet = new HashSet<String>(tokenList);
		HashMap<String, Integer> myMap = new HashMap<String, Integer>();
		for (String s : mySet) {
			myMap.put(s, Collections.frequency(tokenList, s));
		}

		// Map<String, Integer> myMap1 = sortByValue(myMap);

		// myMap1.entrySet().stream()
		// .forEach(System.out::println);
		jC.release();

		return myMap;
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

}
