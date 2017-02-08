package de.unidue.langtech.teaching.pp.project.languageDetector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.jcas.JCas;

public class BigramAnnotator implements NGramAnotator {

	private final static char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final static char[] uml = { '\u0020', '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5',
			'\u00e6', '\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef',
			'\u00f0', '\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f8', '\u00f9', '\u00fa',
			'\u00fb', '\u00fc' };
	private int[][] intMatrix;
	private double[][] doubleMatrix;
	private double[] aVector, bVector;;
	private final String myPlace = "src/test/resources/evoluation/bigramm/";
	private List<String> arrayString;
	private List<Language> suggestion;

	@Override
	public List<Language> process(JCas jcas, List<String> stringList) {

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
		similarity();
		System.out.println("Bigrammen:");
		for (Language d : suggestion) {
			System.out.println(d.getLanguage() + " " + d.getValue());
		}
		return suggestion;
	}

	private void relativeValue(int[][] myArray, int value) {

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

	private void similarity() {

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

}
