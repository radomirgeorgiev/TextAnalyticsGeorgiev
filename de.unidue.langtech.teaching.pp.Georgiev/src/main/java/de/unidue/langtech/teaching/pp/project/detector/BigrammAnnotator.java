package de.unidue.langtech.teaching.pp.project.detector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;

import de.unidue.langtech.teaching.pp.project.type.DetectedLanguage;
import de.unidue.langtech.teaching.pp.project.type.GoldLanguage;

public class BigrammAnnotator extends JCasAnnotator_ImplBase {

	private final static char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final static char[] uml = { '\u0020', '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5',
			'\u00e6', '\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef',
			'\u00f0', '\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f8', '\u00f9', '\u00fa',
			'\u00fb', '\u00fc' };
	private int[][] intMatrix;
	private double[][] doubleMatrix;
	private double[] aVector, bVector;;
	private List<String> stringList;
	private final String myPlace = "src/test/resources/evoluation/bigramm/";
	private List<String> arrayString;
	private List<Language> suggestion;

	public void process(JCas jcas) throws AnalysisEngineProcessException {

		stringList = new ArrayList<String>();

		try {

			stringList = Tokenizer.tokenize(jcas.getDocumentText());
			// documentText = stringList.stream().map(Object::toString)
			// .collect(Collectors.joining(" "));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[] combine = (char[]) ArrayUtils.addAll(uml, alpha);
		intMatrix = new int[combine.length][combine.length];
		doubleMatrix = new double[combine.length][combine.length];
		int counterAll = 0;

		for (int i = 0; i < combine.length; i++) {
			for (int j = 0; j < combine.length; j++) {
				String s1 = "" + combine[i] + combine[j];
				int temp = 0;
				for (String str : stringList) {
					String s2 = " " + str + " ";
					for (int a = 0; a < str.length()+1; a++) {
						String s3 = "";

						s3 = s2.substring(a, a + 2);

						if (s1.equals(s3)) {

							temp++;
							counterAll++;
							
						}

					}
				}
				intMatrix[i][j] = temp;

			}
		}
		// DoubleMatrix

		relativeValue(intMatrix, counterAll);
//		similarity();
//		System.out.println("Bigrammen:");
//		for (Language d : suggestion) {
//			System.out.println(d.getLanguage() + " " + d.getValue());
//		}
//		extract(jcas);

		// Write in File
		 writeInFile(jcas);

//		for (int i = 0; i < intMatrix.length; i++) {
//			for (int j = 0; j < intMatrix.length; j++) {
//				String s1 = "" + combine[i] + combine[j];
//				if (intMatrix[i][j] > 0) {
//					System.out.print(s1 + ":" + intMatrix[i][j] + " ");
//				}
//				 System.out.print(s1 + ":" + intMatrix[i][j] + " ");
//			}
//			 System.out.println();
//		}
//
//		for (int i = 0; i < doubleMatrix.length; i++) {
//			for (int j = 0; j < doubleMatrix.length; j++) {
//				String s1 = "" + combine[i] + combine[j];
//				if (doubleMatrix[i][j] > 0) {
//					System.out.print(s1 + ":" + doubleMatrix[i][j] + " ");
//				}
//				 System.out.print(s1 + ":" + intMatrix[i][j] + " ");
//			}
//			 System.out.println();
//		}

	}

	public void relativeValue(int[][] myArray, int value) {

		int temp = 0;
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray.length; j++) {
				double trouble = 0.0;
				temp = myArray[i][j];
				if (value != 0) {
					trouble = temp / (double) value;
				}
				doubleMatrix[i][j] = trouble;
			}
		}

	}

	public void writeInFile(JCas jcas) {
		GoldLanguage gold = JCasUtil.selectSingle(jcas, GoldLanguage.class);
		try {
			String[] temp = new String[Math.multiplyExact(doubleMatrix.length, doubleMatrix.length)];
			int x = 0;
			for (int i = 0; i < doubleMatrix.length; i++) {
				for (int j = 0; j < doubleMatrix.length; j++) {
					temp[x] = String.valueOf(doubleMatrix[i][j]);
					x++;
				}
			}
			String tp = gold.getLanguage();
			new Writer(temp, tp);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void similarity() {

		suggestion = new ArrayList<Language>();

		try (Stream<Path> paths = Files.walk(Paths.get(myPlace))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					try {
						arrayString = Files.readAllLines(filePath);
						aVector = new double[arrayString.size()];
						bVector = new double[arrayString.size()];
						int x = 0;
						for (int i = 0; i < doubleMatrix.length; i++) {
							for (int j = 0; j < doubleMatrix.length; j++) {
								aVector[x] = doubleMatrix[i][j];
								bVector[x] = Double.parseDouble(arrayString.get(x));
								x++;
							}
						}

						suggestion.add(new Language((filePath.getFileName().toString()),
								new Similarity().cosineSimilarity(aVector, bVector)));
//						suggestion.add(new Language((filePath.getFileName().toString()),
//								new Similarity().euclideanDistance(aVector, bVector)));

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

	public void write(JCas jCas, String language) {

		DetectedLanguage dl = JCasUtil.selectSingle(jCas, DetectedLanguage.class);

		String[] sl = dl.getSuggestion().toArray();
		int slLenght = sl.length + 1;
		StringArray sa = new StringArray(jCas, slLenght);
		if (sl.length!=0) {

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

}
