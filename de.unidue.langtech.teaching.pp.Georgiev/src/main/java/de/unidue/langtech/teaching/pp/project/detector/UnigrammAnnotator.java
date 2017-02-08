package de.unidue.langtech.teaching.pp.project.detector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;
import de.unidue.langtech.teaching.pp.project.type.GoldLanguage;

public class UnigrammAnnotator extends JCasAnnotator_ImplBase {

	private final static char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final static char[] uml = { '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5', '\u00e6',
			'\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef', '\u00f0',
			'\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f8', '\u00f9', '\u00fa', '\u00fb',
			'\u00fc' };
	private int[] intVector;
	private double[] doubleVector;
	private final String myPlace = "src/test/resources/evoluation/unigramm/";
	private List<String> arrayString;
	private List<String> stringList;
	private double[] aVector, bVector;
	private List<Language> suggestion;
	private int numberOfFiles;

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		stringList = new ArrayList<String>();

		StanfordParser sp = new StanfordParser();
		try {
			sp.initialize(getContext());
			sp.process(jcas);
		} catch (ResourceInitializationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FSIterator<FeatureStructure> dependencies = jcas.getFSIndexRepository().getAllIndexedFS(jcas.getCasType(Dependency.type));
		while(dependencies.hasNext()){
			Dependency dep = (Dependency) dependencies.next();
			FSIterator<FeatureStructure> fs = dep.getCAS().getIndexRepository().getAllIndexedFS(jcas.getCasType(Sentence.type));
			while(fs.hasNext()){
				System.out.println(fs);
			}
//			System.out.println(dep.getDependencyType().toString());
//			System.out.println(dep.getDependent().getCoveredText());
//			System.out.println("Dependent: " + dep.getDependent().getCoveredText());
//			System.out.println("Governor: " + dep.getGovernor().getCoveredText());
			if(dep.getDependencyType().equals("nsubj")){
				Token t =dep.getDependent();
				Token s =dep.getGovernor();
				System.out.println("Dependent: " + t.getCoveredText());
				System.out.println("Governor: " + s.getCoveredText());
			} 
		}
		try {

			stringList = Tokenizer.tokenize(jcas.getDocumentText());
			// documentText = stringList.stream().map(Object::toString)
			// .collect(Collectors.joining(" "));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[] combine = (char[]) ArrayUtils.addAll(alpha, uml);

		int counterLetterAll = 0;

		intVector = new int[combine.length];
		doubleVector = new double[combine.length];

		for (int a = 0; a < combine.length; a++) {
			int counterLetter = 0;
			for (String str : stringList) {
				for (int i = 0; i < str.length(); i++) {
					if (str.charAt(i) == combine[a]) {
						counterLetter++;
						counterLetterAll++;
					}

				}

			}
			intVector[a] = counterLetter;

		}

		relativeValue(intVector, counterLetterAll);
		similarity();
		System.out.println("Unigrammen:");
		for (Language d : suggestion) {
			System.out.println(d.getLanguage() + " " + d.getValue());
		}
		extract(jcas);

		// Write into File
		// writeInFile(jcas);

	}

	public void similarity() {

		suggestion = new ArrayList<Language>();
		numberOfFiles = 0;

		try (Stream<Path> paths = Files.walk(Paths.get(myPlace))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					try {

						numberOfFiles++;
						arrayString = Files.readAllLines(filePath);

						aVector = new double[doubleVector.length];
						bVector = new double[arrayString.size()];

						for (int i = 0; i < arrayString.size(); i++) {
							aVector[i] = doubleVector[i];
							bVector[i] = Double.parseDouble(arrayString.get(i));
						}

						suggestion.add(new Language((filePath.getFileName().toString()),
								new Similarity().cosineSimilarity(aVector, bVector)));
						// suggestion.add(new
						// Language((filePath.getFileName().toString()),
						// new Similarity().euclideanDistance(aVector,
						// bVector)));

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void extract(JCas jcas) {

		double t = 0.0;
		String s = "";
		for (int i = 0; i < suggestion.size(); i++) {
			double x = suggestion.get(i).getValue();
			if (x > t) {
				s = suggestion.get(i).getLanguage();
				t = suggestion.get(i).getValue();
			}

		}

		write(jcas, s);
	}

	public void relativeValue(int[] myArray, int value) {

		int temp = 0;

		for (int i = 0; i < myArray.length; i++) {
			double trouble = 0.0;
			temp = myArray[i];
			if (value != 0) {
				trouble = temp / (double) value;
			}
			doubleVector[i] = trouble;
		}

	}

	public void write(JCas jCas, String language) {

		DetectedLanguage dl = JCasUtil.selectSingle(jCas, DetectedLanguage.class);

		String[] sl = dl.getSuggestion().toArray();
		int slLenght = sl.length + 1;
		StringArray sa = new StringArray(jCas, slLenght);
		if (sl.length != 0) {

			int index = 0;
			for (int i = 0; i < sl.length; i++) {
				sa.set(i, sl[i]);
				index++;
			}
			sa.set(index, language);
		} else {
			sa.set(0, language);
		}

		dl.setSuggestion(sa);
		dl.addToIndexes();

	}

	public void writeInFile(JCas jcas) {
		GoldLanguage gold = JCasUtil.selectSingle(jcas, GoldLanguage.class);
		try {
			String[] temp = (String[]) Arrays.asList(doubleVector).toArray();
			new Writer(temp, gold.getLanguage());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
