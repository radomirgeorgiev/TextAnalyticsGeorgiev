package de.unidue.langtech.teaching.pp.project.languageDetector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.jcas.JCas;

/**
 * Klasse UnigramAnnotator. Die Klasse übernimmt den bereits annotierten Text
 * und bildet Unigrammen.
 * 
 * @author Radomir Georgiev
 */

public class UnigramAnnotator implements NGramAnotator {

	private final static char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	private final static char[] uml = { '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5', '\u00e6',
			'\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef', '\u00f0',
			'\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f8', '\u00f9', '\u00fa', '\u00fb',
			'\u00fc' };

	private int[] intVector;

	private double[] doubleVector;

	private double[] aVector, bVector;

	private final String myPlace = "src/test/resources/languageEvaluation/unigramm/";

	private List<String> arrayString;

	private List<Language> suggestion;

	private int numberOfFiles;

	/**
	 * Prozess Nimmt den bereit annotierten Text aus dem jcas-Container und
	 * leitet es weiter zur Untersuchung. Als Ergebnis wird eine Liste mit
	 * vorgeschlagenen Sprachen geliefert.
	 */
	public List<Language> process(JCas aJcas, List<String> stringList) {

		int counterLetterAll = 0;
		char[] combine = (char[]) ArrayUtils.addAll(alpha, uml);

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
		return suggestion;
	}

	/**
	 * Bildet die Cosinus-Ähnlichkeit zwischen zwei Vektoren. Als Resultat
	 * liefert die Methode eine Liste von Paaren, bestehend aus vorgeschlagenen
	 * Sprache und deren Cosinus-Werten. Die Sprache mit dem größten
	 * Cosinus-Wert wird als gefundene Sprache annotiert.
	 */
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

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Relatives Vorkommen des jeweiligen Unigramms im Text
	 *
	 * @param myArray
	 *            Matrix, die die absolute Werte des Vorkommens eines Unigramms
	 *            im Text
	 * @param value
	 *            Anzahl der vorgekommenen Unigrammen im Text
	 */
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

}
